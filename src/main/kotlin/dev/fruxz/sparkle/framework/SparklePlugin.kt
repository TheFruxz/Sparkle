package dev.fruxz.sparkle.framework

import dev.fruxz.sparkle.framework.annotation.SparkleDSL
import dev.fruxz.sparkle.framework.annotation.command.*
import dev.fruxz.sparkle.framework.annotation.command.properties.Aliases
import dev.fruxz.sparkle.framework.annotation.command.properties.Description
import dev.fruxz.sparkle.framework.annotation.command.properties.Label
import dev.fruxz.sparkle.framework.annotation.command.properties.Usage
import dev.fruxz.sparkle.framework.extension.coroutine.coroutineScope
import dev.fruxz.sparkle.framework.extension.internalCommandMap
import dev.fruxz.sparkle.framework.extension.internalSyncCommands
import kotlinx.coroutines.cancel
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

open class SparklePlugin(setup: SparklePlugin.() -> Unit) : JavaPlugin(), ModuleContext {

    private val onLoads = mutableListOf<JavaPlugin.() -> Unit>()
    private val onEnables = mutableListOf<JavaPlugin.() -> Unit>()
    private val onDisables = mutableListOf<JavaPlugin.() -> Unit>()

    private val paperCommands = mutableMapOf<KClass<out CommandExecutor>, CommandExecutor>()
    private val sparkleCommands = mutableListOf<Any>()

    init {
        setup()
    }

    // api stuff

    // // Server processes
    /**
     * Appends this [process] to the [onLoad] processing.
     */
    @SparkleDSL fun onLoad(process: JavaPlugin.() -> Unit) = onLoads.add(process)

    /**
     * Appends this [process] to the [onEnable] processing.
     */
    @SparkleDSL fun onEnable(process: JavaPlugin.() -> Unit) = onEnables.add(process)

    /**
     * Appends this [process] to the [onDisable] processing.
     */
    @SparkleDSL fun onDisable(process: JavaPlugin.() -> Unit) = onDisables.add(process)

    // // Commands

    @SparkleDSL fun command(command: CommandExecutor, clazz: KClass<out CommandExecutor> = command::class) = paperCommands.put(clazz, command)

    @Throws(IllegalArgumentException::class)
    @SparkleDSL inline fun <reified T : CommandExecutor> command(vararg constructorArgs: Any?) {
        val constructors = T::class.constructors

        constructors.firstOrNull { it.hasAnnotation<CommandConstructor>() }?.let {
            command(it.call(*constructorArgs), T::class)
            logger.info("Queued command register of ${T::class.simpleName} via class!") // TODO both (this and the below) cannot be debug but should be .fine() to not clutter console
            return
        }

        constructors.forEach {
            command(it.call(*constructorArgs), T::class)
            logger.warning("annotations: ${T::class.annotations.joinToString { it.annotationClass.simpleName ?: "/" }}")
            logger.info("Queued command register of ${T::class.simpleName} via class!")
            return
        }

        throw IllegalArgumentException("No constructor found for ${T::class.simpleName}!")

    }

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

            commandName?.let { securedName ->
                val securedCommand = (getCommand(securedName) ?: artificialCommand(securedName))

                securedCommand.setExecutor(command.value)
                commandDescription?.let { securedCommand.description = it }
                commandUsage?.let { securedCommand.usage = it }
                commandAliases?.let { securedCommand.aliases = it }

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

}