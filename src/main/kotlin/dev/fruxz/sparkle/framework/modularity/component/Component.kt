package dev.fruxz.sparkle.framework.modularity.component

import com.destroystokyo.paper.utils.PaperPluginLogger
import dev.fruxz.ascend.extension.container.sweep
import dev.fruxz.ascend.extension.objects.takeIfInstance
import dev.fruxz.ascend.tool.smart.composition.Composable
import dev.fruxz.sparkle.framework.command.registerCommand
import dev.fruxz.sparkle.framework.command.unregisterCommand
import dev.fruxz.sparkle.framework.coroutine.task.asAsync
import dev.fruxz.sparkle.framework.coroutine.task.doAsync
import dev.fruxz.sparkle.framework.event.unregisterEvents
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.modularity.Expandable
import dev.fruxz.sparkle.framework.modularity.Hoster
import dev.fruxz.stacked.extension.dyeGray
import dev.fruxz.stacked.extension.dyeYellow
import dev.fruxz.stacked.extension.newline
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import kotlinx.coroutines.Deferred
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.event.HoverEventSource
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.UnaryOperator
import java.util.logging.Logger
import kotlin.reflect.KClass
import net.kyori.adventure.text.Component as AdventureComponent

abstract class Component(
    val startup: StartupBehavior = StartupBehavior.DEFAULT_AUTOSTART,
    val isExperimental: Boolean = false,
    var vendor: Composable<Plugin>? = null,
) : Expandable, Hoster, HoverEventSource<AdventureComponent> {

    private val commands = mutableListOf<Pair<KClass<out CommandExecutor>, CommandExecutor>>()
    private val runningCommands = mutableSetOf<PluginCommand>()
    private val components = mutableListOf<Component>()
    private val listeners = mutableListOf<Listener>()

    private var onDisable: (suspend () -> Unit)? = null
    private var onEnable: (suspend () -> Unit)? = null

    abstract val identity: Key
    abstract fun setup()

    @SparkleDSL
    fun onDisable(action: suspend () -> Unit) {
        onDisable = action
    }

    @SparkleDSL
    fun onEnable(action: suspend () -> Unit) {
        onEnable = action
    }

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

    // state

    val isRunning: Boolean
        get() = ComponentManager.registered[this]?.isRunning ?: false

    var isAutoStarting: Boolean
        get() = ComponentManager.configuration[identity]?.isAutoStart ?: startup.defaultIsAutoStart
        set(value) {
            ComponentManager.configuration += identity to (ComponentManager.configuration[identity]?.copy(isAutoStart = value) ?: ComponentManager.ComponentConfiguration(value, false))
        }

    override fun start(): Deferred<Boolean> = asAsync {
        if (ComponentManager.registered.containsKey(this)) {
            if (!isRunning) {
                logger.info("Starting component ${identity.asString()}")

                val plugin = vendor?.compose()?.takeIfInstance<JavaPlugin>()

                listeners.forEach { plugin?.server?.pluginManager?.registerEvents(it, plugin) }
                commands.forEach { (clazz, command) ->
                    plugin?.registerCommand(command, clazz)?.let { runningCommands.add(it) }
                }
                components.map { it.start() }.forEach { it.await() }

                ComponentManager.updateState(identity) { it.copy(isRunning = true) }

                onEnable?.invoke() // onEnable is called after all components are started

                logger.info("Started component ${identity.asString()}")
                return@asAsync true
            } else {
                logger.warning("Requested start, but component ${identity.asString()} is already running")
                return@asAsync false
            }
        } else
            throw IllegalStateException("Component ${identity.asString()} is not registered")
    }

    override fun stop(): Deferred<Boolean> = asAsync {
        if (ComponentManager.registered.containsKey(this)) {
            if (isRunning) {
                logger.info("Stopping component ${identity.asString()}")

                val plugin = vendor?.compose()?.takeIfInstance<JavaPlugin>()

                listeners.forEach { plugin?.server?.pluginManager?.unregisterEvents(it) }
                runningCommands.sweep { plugin?.unregisterCommand(it) }
                components.map { it.stop() }.forEach { it.await() }

                ComponentManager.updateState(identity) { it.copy(isRunning = false) }

                onDisable?.invoke() // onDisable is called after all components are stopped

                logger.info("Stopped component ${identity.asString()}")
                return@asAsync true
            } else {
                logger.warning("Requested stop, but component ${identity.asString()} is not running")
                return@asAsync false
            }
        } else
            throw IllegalStateException("Component ${identity.asString()} is not registered")
    }

    override fun restart() = doAsync {
        stop().await()
        start().await()
    }

    override fun asHoverEvent(op: UnaryOperator<net.kyori.adventure.text.Component>): HoverEvent<net.kyori.adventure.text.Component> =
        HoverEvent.showText(op.apply(text {
            this + text("Identity: ").dyeGray()
            this + text(identity.asString()).dyeYellow()
            newline()
            this + text("Vendor: ").dyeGray()
            this + text(vendor?.compose()?.name ?: "Unknown").dyeYellow()
            newline()
            this + text("Experimental: ").dyeGray()
            this + text(isExperimental.toString()).dyeYellow()
        }))

}