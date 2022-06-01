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

	/**
	 * This function returns the given user-input string, at the given index-position [slot].
	 * By default, the [slot] is set to the last index of the input-[parameters], so the [getInput]
	 * function is very quick to use inside the StructuredInterchanges, because an execution block
	 * itself hosts the last input-parameter any time.
	 *
	 * Example:
	 * User-Input: "/test foo bar baz"; slot: 1 -> "bar"
	 *
	 * @param slot The index-position of the input-parameter to return.
	 * @return The input-parameter at the given index-position [slot].
	 * @throws IndexOutOfBoundsException if the given [slot] is out of bounds.
	 * @throws IllegalArgumentException if the given restrictions at the given [slot] are not met.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getInput(slot: Int = inputLength - 1) = parameters[slot]

	/**
	 *
	 * This function returns the given user-input string, at the given index-position [slot].
	 * By default, the [slot] is set to the last index of the input-[parameters], so the [getInput]
	 * function is very quick to use inside the StructuredInterchanges, because an execution block
	 * itself hosts the last input-parameter any time.
	 * This function also converts the output String to the given [T] using the [restriction] [InterchangeStructureInputRestriction].
	 *
	 * Example:
	 * User-Input: "/test foo bar baz"; slot: 1 -> "bar"
	 *
	 * @param slot The index-position of the input-parameter to return.
	 * @param restriction The restriction to check if the input-parameter is valid and also converts the input to the [T] result.
	 * @return The input-parameter at the given index-position [slot].
	 * @throws IndexOutOfBoundsException if the given [slot] is out of bounds.
	 * @throws IllegalArgumentException if the given restrictions at the given [slot] are not met.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun <T> getInput(slot: Int = inputLength - 1, restriction: InterchangeStructureInputRestriction<T>) =
		if (restriction.isValid(parameters[slot])) {
			restriction.transformer(parameters[slot])
		} else {
			throw IllegalArgumentException("Input restriction not followed!")
		}

	/**
	 *
	 * This function returns the given user-input string, at the given index-position [slot].
	 * By default, the [slot] is set to the last index of the input-[parameters], so the [getInput]
	 * function is very quick to use inside the StructuredInterchanges, because an execution block
	 * itself hosts the last input-parameter any time.
	 * This function also converts the output String to the given [T] using the [restrictiveAsset] [CompletionAsset].
	 *
	 * Example:
	 * User-Input: "/test foo bar baz"; slot: 1 -> "bar"
	 *
	 * @param slot The index-position of the input-parameter to return.
	 * @param restrictiveAsset The restriction to check if the input-parameter is valid and also converts the input to the [T] result.
	 * @return The input-parameter at the given index-position [slot].
	 * @throws IndexOutOfBoundsException if the given [slot] is out of bounds.
	 * @throws IllegalArgumentException if the given restrictions at the given [slot] are not met.
	 * @throws IllegalStateException if the asset produces a null value
	 * @author Fruxz
	 * @since 1.0
	 */
	fun <T : Any> getInput(slot: Int = inputLength - 1, restrictiveAsset: CompletionAsset<T>): T {
		if (restrictiveAsset.transformer == null) throw IllegalArgumentException("Asset '${restrictiveAsset.identity}' provides no transformer!")

		return getInput(slot).let { input -> restrictiveAsset.transformer?.invoke(CompletionAsset.CompletionContext(
			executor = executor,
			fullLineInput = parameters,
			input = parameters.getOrNull(slot) ?: "",
			ignoreCase = true
		)) ?: throw IllegalStateException("Asset '${restrictiveAsset.identity}' transformer produces null at input '$input'!") }

	}

}
