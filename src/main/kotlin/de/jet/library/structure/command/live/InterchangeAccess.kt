package de.jet.library.structure.command.live

import de.jet.library.structure.app.App
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeExecutorType
import de.jet.library.structure.smart.Identifiable
import de.jet.library.structure.smart.Logging
import org.bukkit.command.CommandSender
import java.util.logging.Level

data class InterchangeAccess(
	override val vendor: Identifiable<App>,
	val executorType: InterchangeExecutorType,
	val executor: CommandSender,
	val interchange: Interchange,
	val label: String,
	val parameters: List<String>,
) : Logging {

	override val sectionLabel = "InterchangeRun/$vendor:$label"

	fun interchangeLog(level: Level = Level.INFO, message: String) = sectionLog.log(level, message)

}
