package de.moltenKt.paper.structure.command.live

import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.command.InterchangeUserRestriction
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.tool.smart.Logging
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

	/**
	 * @throws IllegalArgumentException if the asset has no transformer configured
	 * @throws IllegalStateException if the asset produces a null value
	 */
	fun <T : Any> getInput(slot: Int, restrictiveAsset: CompletionAsset<T>): T {
		if (restrictiveAsset.transformer == null) throw IllegalArgumentException("Asset '${restrictiveAsset.identity}' provides no transformer!")

		return getInput(slot).let { input -> restrictiveAsset.transformer?.invoke(executor, input) ?: throw IllegalStateException("Asset '${restrictiveAsset.identity}' transformer produces null at input '$input'!") }

	}

}
