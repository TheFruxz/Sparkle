package dev.fruxz.sparkle.framework.modularity.component

import com.destroystokyo.paper.utils.PaperPluginLogger
import dev.fruxz.ascend.extension.container.sweep
import dev.fruxz.ascend.extension.objects.takeIfInstance
import dev.fruxz.ascend.tool.smart.composition.Composable
import dev.fruxz.sparkle.framework.command.registerCommand
import dev.fruxz.sparkle.framework.command.unregisterCommand
import dev.fruxz.sparkle.framework.event.unregisterEvents
import dev.fruxz.sparkle.framework.modularity.Expandable
import dev.fruxz.sparkle.framework.modularity.Hoster
import net.kyori.adventure.key.Key
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import kotlin.reflect.KClass

abstract class Component(
    val startup: StartupBehavior = StartupBehavior.DEFAULT_AUTOSTART,
    val isExperimental: Boolean = false,
    var vendor: Composable<Plugin>? = null,
) : Expandable, Hoster {

    private val commands = mutableListOf<Pair<KClass<out CommandExecutor>, CommandExecutor>>()
    private val runningCommands = mutableSetOf<PluginCommand>()
    private val components = mutableListOf<Component>()
    private val listeners = mutableListOf<Listener>()

    abstract val identity: Key
    abstract fun setup()

    init {
        this.setup()
    }

    override val logger: Logger by lazy {
        (vendor?.compose()?.logger ?: PaperPluginLogger.getLogger(identity.asString()))
    }

    // component

    override fun add(component: Component) {
        components.add(component)
    }

    // listener

    override fun add(listener: Listener) {
        listeners.add(listener)
    }

    // command

    override fun add(command: CommandExecutor, clazz: KClass<out CommandExecutor>) {
        commands.add(clazz to command)
    }

    inline fun <reified T : CommandExecutor> add(command: T) = add(command, T::class)

    override suspend fun start() {
        if (!ComponentManager.isRunning(this.identity)) {
            logger.info("Starting component ${identity.asString()}")

            val plugin = vendor?.compose()?.takeIfInstance<JavaPlugin>()

            components.forEach { it.start() }
            listeners.forEach { plugin?.server?.pluginManager?.registerEvents(it, plugin) }
            commands.forEach { (clazz, command) ->
                plugin?.registerCommand(command, clazz)?.let { runningCommands.add(it) }
            }

            ComponentManager.registerStart(this.identity)

            logger.info("Started component ${identity.asString()}")
        } else
            logger.warning("Requested start, but component ${identity.asString()} is already running")
    }

    override suspend fun stop() {
        if (ComponentManager.isRunning(this.identity)) {
            logger.info("Stopping component ${identity.asString()}")

            val plugin = vendor?.compose()?.takeIfInstance<JavaPlugin>()

            components.forEach { it.stop() }
            listeners.forEach { plugin?.server?.pluginManager?.unregisterEvents(it) }
            runningCommands.sweep { plugin?.unregisterCommand(it) }

            ComponentManager.registerStop(this.identity)

            logger.info("Stopped component ${identity.asString()}")
        } else
            logger.warning("Requested stop, but component ${identity.asString()} is not running")
    }

    override suspend fun restart() {
        stop()
        start()
    }

}