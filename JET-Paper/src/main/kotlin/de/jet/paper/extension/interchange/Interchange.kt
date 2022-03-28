package de.jet.paper.extension.interchange

import de.jet.paper.app.JetCache
import org.bukkit.command.CommandSender

@Deprecated("Use InterchangeExecutor instead (JET-Use)", ReplaceWith("de.jet.paper.extension.interchange.InterchangeExecutor"))
typealias CommandSender = InterchangeExecutor

typealias InterchangeExecutor = CommandSender

typealias Parameters = Array<out String>

fun getInterchange(label: String, allowPluginIdentity: Boolean = true) =
    JetCache.registeredInterchanges.firstOrNull {
        it.label == label || (allowPluginIdentity && it.label == "${it.vendor.name.lowercase()}:$label")
    }