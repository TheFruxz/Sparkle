package de.fruxz.sparkle.structure.command.completion.component

import de.fruxz.sparkle.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.tool.permission.Approval

sealed interface CompletionComponent {

	fun completion(context: CompletionAsset.CompletionContext): Set<String>

	val label: String

	val displayRequirement: ((executor: InterchangeExecutor, parameters: Array<String>, completion: Set<String>) -> Boolean)?

	val inputExpressionCheck: (CompletionAsset.CompletionContext) -> Boolean

	val accessApproval: Approval?

	companion object {

		@JvmStatic
		fun static(
			completion: Set<String>,
			displayRequirement: ((executor: InterchangeExecutor, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
			accessApproval: Approval? = null,
		) = Static(completion, displayRequirement, accessApproval)

		@JvmStatic
		fun static(
			vararg completion: String,
			displayRequirement: ((executor: InterchangeExecutor, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
			accessApproval: Approval? = null,
		) = static(completion.toSet(), displayRequirement, accessApproval)

		@JvmStatic
		fun asset(
			asset: CompletionAsset<*>,
			displayRequirement: ((executor: InterchangeExecutor, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
			accessApproval: Approval? = null,
		) = Asset(asset, displayRequirement, accessApproval)

	}

	data class Static(
		val completion: Set<String>,
		override val displayRequirement: ((executor: InterchangeExecutor, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
		override val accessApproval: Approval? = null,
	) : CompletionComponent {

		override fun completion(context: CompletionAsset.CompletionContext) = completion

		override val label = "[${completion.joinToString("/")}]"

		override val inputExpressionCheck: CompletionAsset.CompletionContext.() -> Boolean = {
			completion.any { it.equals(input, ignoreCase) }
		}

	}

	data class Asset(
		val asset: CompletionAsset<*>,
		override val displayRequirement: ((executor: InterchangeExecutor, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
		override val accessApproval: Approval? = null,
	) : CompletionComponent {

		override fun completion(context: CompletionAsset.CompletionContext) = asset.computedContent(context)

		override val label = "<${asset.identity}>"

		override val inputExpressionCheck: CompletionAsset.CompletionContext.() -> Boolean = check@{
			return@check asset.check?.invoke(this) ?: true
		}

	}

}