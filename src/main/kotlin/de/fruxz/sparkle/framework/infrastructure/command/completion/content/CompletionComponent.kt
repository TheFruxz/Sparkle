package de.fruxz.sparkle.framework.infrastructure.command.completion.content

import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset.CompletionContext
import de.fruxz.sparkle.framework.permission.Approval

sealed interface CompletionComponent {

	fun completion(context: CompletionContext): Set<String>

	val label: String

	val displayRequirement: ((executor: InterchangeExecutor, parameters: Array<String>, completion: Set<String>) -> Boolean)?

	val inputExpressionCheck: (CompletionContext) -> Boolean // TODO validation is NEVER USED????

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

		override fun completion(context: CompletionContext) = completion

		override val label = "[${completion.joinToString("/")}]"

		override val inputExpressionCheck: CompletionContext.() -> Boolean = {
			completion.any { it.equals(input, ignoreCase) }
		}

	}

	data class Asset(
		val asset: CompletionAsset<*>,
		override val displayRequirement: ((executor: InterchangeExecutor, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
		override val accessApproval: Approval? = null,
	) : CompletionComponent {

		override fun completion(context: CompletionContext) = asset.computedContent(context)

		override val label = "<${asset.identity}>"

		override val inputExpressionCheck: CompletionContext.() -> Boolean = check@{
			return@check asset.validation?.invoke(this) ?: true
		}

	}

}