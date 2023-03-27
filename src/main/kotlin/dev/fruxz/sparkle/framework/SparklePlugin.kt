package dev.fruxz.sparkle.framework

import dev.fruxz.sparkle.framework.annotation.SparkleDSL
import dev.fruxz.sparkle.framework.annotation.command.CommandConstructor
import dev.fruxz.sparkle.framework.annotation.command.CommandName
import org.bukkit.command.CommandExecutor
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation

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
            logger.info("Queued command register of ${T::class.simpleName} via class!")
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

    // backend stuff

    override fun onLoad() {
        onLoads.forEach { it.invoke(this) }
    }
    override fun onEnable() {
        onEnables.forEach { it.invoke(this) }

        paperCommands.forEach { command ->
            val commandName = command.key.findAnnotation<CommandName>()?.name ?: command.key.simpleName

            val ann = command.key.findAnnotations<CommandName>()?.also { println("got: ${it.joinToString { it.annotationClass.simpleName ?: "" }}") }
            val sec = command.key.findAnnotation<CommandName>()?.also { println("other: ${it.annotationClass.simpleName}") }

            val name = sec!!.name.also { println("name: $it") }

            commandName
                ?.let { getCommand(it).also { println("command = ${it?.name}") }?.setExecutor(command.value) }
                ?: logger.warning("No name found for command ${command.key.simpleName} @${command.key.simpleName} at ${command.key.annotations.joinToString { it.annotationClass.simpleName ?: "/" }}!")
        }

    }
    override fun onDisable() {
        onDisables.forEach { it.invoke(this) }
    }

}