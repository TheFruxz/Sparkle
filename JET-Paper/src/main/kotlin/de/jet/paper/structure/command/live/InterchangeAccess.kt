package de.jet.paper.structure.command.live

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.extension.debugLog
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeExecutorType
import de.jet.paper.tool.smart.Logging
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

	val checkParameter = ValidationData(this)

	val inputLength = parameters.size

	fun inputLength(checkIf: Int) = parameters.size == checkIf

	fun inputParameter(slot: Int) = parameters[slot]

	data class ValidationData(
		private val access: InterchangeAccess,
	) {

		/**
		 * returning, if the parameter/variable was used correctly, or false if there is no parameter!
		 */
		operator fun get(slot: Int) = with(access) {
			val completion = interchange.completion
			val currentSection = completion.sections.getOrNull(slot)

			return@with currentSection?.inputExpressionCheck?.let { it(parameters.getOrNull(slot) ?: "") }?.debugLog("checkit")
				?: (completion.infinite && completion.sections.lastIndex < parameters.lastIndex).debugLog("skipit")

		}

	}

}
