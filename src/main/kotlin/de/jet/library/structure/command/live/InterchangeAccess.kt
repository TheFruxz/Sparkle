package de.jet.library.structure.command.live

import de.jet.library.structure.app.App
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeExecutorType
import de.jet.library.structure.smart.Identifiable
import org.bukkit.command.CommandSender

data class InterchangeAccess(
	val vendor: Identifiable<App>,
	val executorType: InterchangeExecutorType,
	val executor: CommandSender,
	val interchange: Interchange,
	val label: String,
	val parameters: List<String>,
)
