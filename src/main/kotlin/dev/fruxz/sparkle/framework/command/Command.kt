package dev.fruxz.sparkle.framework.command

import dev.fruxz.brigadikt.domain.FrontArgumentBuilder
import dev.fruxz.brigadikt.tree.buildUniversalCommand
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.command.annotations.Aliases
import dev.fruxz.sparkle.framework.command.annotations.Description
import dev.fruxz.sparkle.framework.command.annotations.Label
import dev.fruxz.sparkle.framework.command.annotations.Usage
import dev.fruxz.sparkle.framework.command.annotations.permission.Private
import dev.fruxz.sparkle.framework.command.annotations.permission.Public
import dev.fruxz.sparkle.framework.system.commandMap
import dev.fruxz.sparkle.framework.system.debugLog
import dev.fruxz.sparkle.framework.system.internalSyncCommands
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

fun buildCommand(
    name: String,
    builder: FrontArgumentBuilder<CommandSender>.() -> Unit,
) = buildUniversalCommand(
    name = name,
    builder = builder,
)

fun <T : CommandExecutor> JavaPlugin.registerCommand(command: T, clazz: KClass<out T>): PluginCommand? {

    val commandName = clazz.findAnnotation<Label>()?.name
    val commandDescription = clazz.findAnnotation<Description>()?.description
    val commandUsage = clazz.findAnnotation<Usage>()?.usage
    val commandAliases = clazz.findAnnotation<Aliases>()?.aliases?.distinct()
    val commandPermission = clazz.findAnnotation<Private>()?.permission
    val commandIsPublic = clazz.hasAnnotation<Public>()

    commandName?.let { securedName ->
        val securedCommand = (getCommand(securedName) ?: SparklePlugin.artificialCommand(this, securedName))

        securedCommand.setExecutor(command)
        commandDescription?.let { securedCommand.description = it }
        commandUsage?.let { securedCommand.usage = it }
        commandAliases?.let { securedCommand.aliases = it }
        if (!commandIsPublic) securedCommand.permission = commandPermission ?: "${this.name}.command.${securedName}"

        commandMap.register(securedName, pluginMeta.name, securedCommand) // TODO maybe not pluginMeta, due to the in-line creation of plugins
        server.internalSyncCommands()

        return securedCommand
    } ?: logger.warning("No name (@Label(name: String)) found for command ${clazz.simpleName} @${clazz.simpleName} at ${clazz.annotations.joinToString { it.annotationClass.simpleName ?: "/" }}!")

    return null
}

fun JavaPlugin.unregisterCommand(pluginCommand: PluginCommand) {
    debugLog { "unregistering ${pluginCommand.name}" }

    // normal unregistering
    pluginCommand.apply {
        setExecutor(null)
        tabCompleter = null
        unregister(server.commandMap)
    }

    // remove from known commands
    commandMap.knownCommands.apply {
        remove(pluginCommand.name.lowercase())
        remove((pluginCommand.plugin.name + ":" + pluginCommand.name).lowercase())
        pluginCommand.aliases.forEach { remove(it) }
    }

    // sync commands
    server.internalSyncCommands()
}