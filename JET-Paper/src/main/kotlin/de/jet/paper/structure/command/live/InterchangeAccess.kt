package de.jet.paper.structure.command.live

import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeUserRestriction
import de.jet.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.jet.paper.tool.smart.Logging
import java.util.logging.Level

data class InterchangeAccess(
	override val vendor: App,
	val executorType: InterchangeUserRestriction,
	val executor: InterchangeExecutor,
	val interchange: Interchange,
	val label: String,
	val parameters: List<String>,
	val additionalParameters: List<String>,
) : Logging {

	override val sectionLabel = "InterchangeRun/$vendor:$label"

	fun interchangeLog(level: Level = Level.INFO, message: String) = sectionLog.log(level, message)

	val inputLength = parameters.size

	fun inputLength(checkIf: Int) = parameters.size == checkIf

	fun getInput(slot: Int) = parameters[slot]

	fun <T> getInput(slot: Int, restriction: InterchangeStructureInputRestriction<T>) =
		if (restriction.isValid(parameters[slot])) {
			restriction.transformer(parameters[slot])
		} else {
			throw IllegalArgumentException("Input restriction not followed!")
		}

}
