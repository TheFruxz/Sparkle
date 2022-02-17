package de.jet.paper.extension.interchange

import org.bukkit.command.CommandSender

@Deprecated("Use InterchangeExecutor instead (JET-Use)", ReplaceWith("de.jet.paper.extension.interchange.InterchangeExecutor"))
typealias CommandSender = InterchangeExecutor

typealias InterchangeExecutor = CommandSender

typealias Parameters = Array<out String>