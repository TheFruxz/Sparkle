package de.fruxz.sparkle.framework.infrastructure.command.live

import de.fruxz.sparkle.framework.attachment.Logging
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.visual.message
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.visual.message.Transmission
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import dev.fruxz.stacked.extension.asStyledComponent
import dev.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import java.util.logging.Level

data class InterchangeAccess<EXECUTOR : InterchangeExecutor>(
	override val vendor: App,
	val executorType: InterchangeUserRestriction,
	val executor: EXECUTOR,
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
	 * This function returns the given user-input string, at the given [slot]
	 * and every other passed parameter, beyond this slot index. By default, the
	 * [slot] is set to the last index of the input-[parameters], so the [getInputAndBeyond]
	 * is easy to use, but you should specify the right slot, to get the output
	 * you want to get.
	 *
	 * Example:
	 * User-Input: "/test foo bar baz"; slot: 1 -> ["bar", "baz"]
	 *
	 * @param slot The index-position of the first input-parameter to return.
	 * @return The input-parameters at the given index-position [slot] and beyond.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getInputAndBeyond(slot: Int = inputLength - 1) = parameters.subList(slot, inputLength)

	/**
	 * This function returns the given user-input string, at the given [slot]
	 * and every other passed parameter, beyond this slot index, joined into a
	 * single String. By default, the [slot] is set to the last index of the
	 * input-[parameters], so the [getInputAndBeyond] is easy to use, but you
	 * should specify the right slot, to get the output you want to get.
	 *
	 * Example:
	 * User-Input: "/test foo bar baz"; slot: 1 -> "bar baz"
	 *
	 * @param slot The index-position of the first input-parameter to return.
	 * @return The input-parameters at the given index-position [slot] and beyond.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun joinInputAndBeyond(slot: Int = inputLength - 1) = getInputAndBeyond(slot).joinToString(" ")

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
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getInput(slot: Int = inputLength - 1) = parameters[slot]

	/**
	 * This function returns the given user-input strings, at the given index-positions [slots].
	 *
	 * Example:
	 * User-Input: "/test foo bar baz"; slots: 0, 2 -> ["foo", "baz"]
	 *
	 * @param slots The index-positions of the input-parameters to return. ([IntRange]s and [IntProgression]s are also supported)
	 * @return The input-parameters at the given index-positions [slots].
	 * @throws IndexOutOfBoundsException if any of the given [slots] is out of bounds.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getInput(slots: Iterable<Int>) = slots.map { getInput(it) }

	/**
	 * This function returns the given user-input strings, at the given index-positions [slots]
	 * joined into a single string.
	 *
	 * Example:
	 * User-Input: "/test foo bar baz"; slots: 0, 2 -> "foo baz"
	 *
	 * @param slots The index-positions of the input-parameters to return. ([IntRange]s and [IntProgression]s are also supported)
	 * @return The input-parameters at the given index-positions [slots].
	 * @throws IndexOutOfBoundsException if any of the given [slots] is out of bounds.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun joinInput(slots: Iterable<Int>) = getInput(slots).joinToString(" ")

	/**
	 *
	 * This function returns the given user-input string, at the given index-position [slot].
	 * By default, the [slot] is set to the last index of the input-[parameters], so the [getInput]
	 * function is very quick to use inside the StructuredInterchanges, because an execution block
	 * itself hosts the last input-parameter any time.
	 * This function also converts the output String to the given [T] using the [translationAsset] [CompletionAsset].
	 *
	 * Example:
	 * User-Input: "/test foo bar baz"; slot: 1 -> "bar"
	 *
	 * @param slot The index-position of the input-parameter to return.
	 * @param translationAsset The restriction to check if the input-parameter is valid and also converts the input to the [T] result.
	 * @return The input-parameter at the given index-position [slot].
	 * @throws IndexOutOfBoundsException if the given [slot] is out of bounds.
	 * @throws IllegalArgumentException if the given restrictions at the given [slot] are not met.
	 * @throws IllegalStateException if the asset produces a null value
	 * @author Fruxz
	 * @since 1.0
	 */
	fun <T : Any> getInput(slot: Int = inputLength - 1, translationAsset: CompletionAsset<T>): T {
		if (translationAsset.translation == null) throw IllegalArgumentException("Asset '${translationAsset.identity}' provides no transformer!")

		return getInput(slot).let { input -> translationAsset.translation.invoke(
			CompletionAsset.CompletionContext(
			executor = executor,
			fullLineInput = parameters,
			input = parameters.getOrNull(slot) ?: "",
			ignoreCase = true
		)) ?: throw IllegalStateException("Asset '${translationAsset.identity}' transformer produces null at input '$input'!") }

	}

	fun feedback(componentLike: Component, notificationLevel: TransmissionAppearance? = null): Transmission =
		componentLike.let {
			when (notificationLevel) {
				null -> it.message(executor)
				else -> it.notification(notificationLevel)
			}
		}.display()

	fun feedback(styledString: String, notificationLevel: TransmissionAppearance? = null): Transmission =
		feedback(styledString.asStyledComponent, notificationLevel)

	fun feedback(notificationLevel: TransmissionAppearance? = null, builder: TextComponent.Builder.() -> Unit): Transmission =
		feedback(text(builder), notificationLevel)

}
