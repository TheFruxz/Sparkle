package dev.fruxz.sparkle.framework

import dev.fruxz.sparkle.framework.command.*
import dev.fruxz.sparkle.framework.command.annotations.*
import dev.fruxz.sparkle.framework.command.annotations.permission.Private
import dev.fruxz.sparkle.framework.command.annotations.permission.Public
import dev.fruxz.sparkle.framework.coroutine.scope.coroutineScope
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.component.ModuleContext
import dev.fruxz.sparkle.framework.system.internalCommandMap
import dev.fruxz.sparkle.framework.system.internalSyncCommands
import dev.fruxz.sparkle.server.LocalSparklePlugin
import kotlinx.coroutines.cancel
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

open class SparklePlugin(setup: SparklePlugin.() -> Unit) : JavaPlugin(), ModuleContext, Keyed {

    private val onLoads = mutableListOf<JavaPlugin.() -> Unit>()
    private val onEnables = mutableListOf<JavaPlugin.() -> Unit>()
    private val onDisables = mutableListOf<JavaPlugin.() -> Unit>()

    private val paperCommands = mutableMapOf<KClass<out CommandExecutor>, CommandExecutor>()

    private val key: Key by lazy {
       Key.key(LocalSparklePlugin.SYSTEM_IDENTITY.lowercase(), this.name.lowercase())
    }

    init {
        setup()
    }

    // api stuff

    // // Server processes
    /**
     * Appends this [process] to the [onLoad] processing.
     */
    @SparkleDSL
    fun onLoad(process: JavaPlugin.() -> Unit) = onLoads.add(process)

    /**
     * Appends this [process] to the [onEnable] processing.
     */
    @SparkleDSL
    fun onEnable(process: JavaPlugin.() -> Unit) = onEnables.add(process)

    /**
     * Appends this [process] to the [onDisable] processing.
     */
    @SparkleDSL
    fun onDisable(process: JavaPlugin.() -> Unit) = onDisables.add(process)

    // // Commands

    // // // Bukkit

    @SparkleDSL
    fun command(command: CommandExecutor, clazz: KClass<out CommandExecutor> = command::class) = paperCommands.put(clazz, command)

    @SparkleDSL
    fun command(clazz: KClass<out CommandExecutor>, vararg constructorArgs: Any?) {
        val constructors = clazz.constructors

        constructors.firstOrNull { it.hasAnnotation<CommandConstructor>() }?.let {
            command(it.call(*constructorArgs), clazz)
            logger.info("Queued command register of ${clazz.simpleName} via class!") // TODO both (this and the below) cannot be debug but should be .fine() to not clutter console
            return
        }

        constructors.forEach {
            command(it.call(*constructorArgs), clazz)
            logger.info("Queued command register of ${clazz.simpleName} via class!")
            return
        }

        throw IllegalArgumentException("No constructor found for ${clazz.simpleName}!")
    }

    @Throws(IllegalArgumentException::class)
    @SparkleDSL
    inline fun <reified T : CommandExecutor> command(vararg constructorArgs: Any?) = command(T::class, *constructorArgs)

    // // Coroutines



    // backend stuff

    override fun onLoad() {
        onLoads.forEach { it.invoke(this) }
    }
    override fun onEnable() {
        onEnables.forEach { it.invoke(this) }

        paperCommands.forEach { command ->
            val commandName = command.key.findAnnotation<Label>()?.name
            val commandDescription = command.key.findAnnotation<Description>()?.description
            val commandUsage = command.key.findAnnotation<Usage>()?.usage
            val commandAliases = command.key.findAnnotation<Aliases>()?.aliases?.distinct()
            val commandPermission = command.key.findAnnotation<Private>()?.permission
            val commandIsPublic = command.key.hasAnnotation<Public>()

            commandName?.let { securedName ->
                val securedCommand = (getCommand(securedName) ?: artificialCommand(securedName))

                securedCommand.setExecutor(command.value)
                commandDescription?.let { securedCommand.description = it }
                commandUsage?.let { securedCommand.usage = it }
                commandAliases?.let { securedCommand.aliases = it }
                if (!commandIsPublic) securedCommand.permission = commandPermission ?: "${this.name}.command.${securedName}"

                server.internalCommandMap.register(securedName, pluginMeta.name, securedCommand) // TODO maybe not pluginMeta, due to the in-line creation of plugins
                server.internalSyncCommands()

            } ?: logger.warning("No name found for command ${command.key.simpleName} @${command.key.simpleName} at ${command.key.annotations.joinToString { it.annotationClass.simpleName ?: "/" }}!")


        }

    }
    override fun onDisable() {
        onDisables.forEach { it.invoke(this) }

        coroutineScope.cancel("Plugin disabled!") // Disables all coroutines
    }

    private fun artificialCommand(label: String): PluginCommand {
        val constructor = PluginCommand::class.constructors.first()

        constructor.isAccessible = true

        return constructor.call(label, this)
    }

    /**
     * Returns the [Key] of this [SparklePlugin] using the [key] property.
     */
    override fun key(): Key = key

}