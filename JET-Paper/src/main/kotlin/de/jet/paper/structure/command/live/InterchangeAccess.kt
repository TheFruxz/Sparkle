package de.jet.paper.structure.command.live

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.extension.debugLog
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeUserRestriction
import de.jet.paper.tool.smart.Logging
import org.bukkit.command.CommandSender
import java.util.logging.Level

data class InterchangeAccess(
	override val vendor: Identifiable<App>,
	val executorType: InterchangeUserRestriction,
	val executor: CommandSender,
	val interchange: Interchange,
	val label: String,
	val parameters: List<String>,
) : Logging {

	override val sectionLabel = "InterchangeRun/$vendor:$label"

	fun interchangeLog(level: Level = Level.INFO, message: String) = sectionLog.log(level, message)

	val inputLength = parameters.size

	fun inputLength(checkIf: Int) = parameters.size == checkIf

	fun inputParameter(slot: Int) = parameters[slot]

}
