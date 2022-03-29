package de.jet.paper.extension.interchange

import de.jet.paper.app.JetCache
import de.jet.paper.extension.paper.getPluginCommand
import de.jet.paper.extension.paper.server
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.SimpleCommandMap

@Deprecated("Use InterchangeExecutor instead (JET-Use)", ReplaceWith("de.jet.paper.extension.interchange.InterchangeExecutor"))
typealias CommandSender = InterchangeExecutor

typealias InterchangeExecutor = CommandSender

typealias Parameters = Array<out String>

fun getInterchange(label: String, allowPluginIdentity: Boolean = true) =
    JetCache.registeredInterchanges.firstOrNull {
        it.label == label || (allowPluginIdentity && label == "${it.vendor.name.lowercase()}:${it.label}")
    }

fun getServerCommand(label: String) =
    getPluginCommand(label) ?: Bukkit.getCommandMap().getCommand(label) ?: SimpleCommandMap(server).getCommand(label)