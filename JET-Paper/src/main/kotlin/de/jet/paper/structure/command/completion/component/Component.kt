package de.jet.paper.structure.command.completion.component

import de.jet.paper.tool.permission.Approval
import org.bukkit.command.CommandSender

sealed interface CompletionComponent {

	fun completion(): Set<String>

	val label: String

	val displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)?

	val inputExpressionCheck: (input: String, ignoreCase: Boolean) -> Boolean

	val accessApproval: Approval?

	companion object {

		fun static(
			completion: Set<String>,
			displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
			accessApproval: Approval? = null,
		) = Static(completion, displayRequirement, accessApproval)

		fun static(
			vararg completion: String,
			displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
			accessApproval: Approval? = null,
		) = static(completion.toSet(), displayRequirement, accessApproval)

		fun asset(
			asset: CompletionAsset,
			displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
			accessApproval: Approval? = null,
		) = Asset(asset, displayRequirement, accessApproval)

	}

	data class Static(
		val completion: Set<String>,
		override val displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
		override val accessApproval: Approval? = null,
	) : CompletionComponent {

		override fun completion() = completion

		override val label = "[${completion.joinToString("/")}]"

		override val inputExpressionCheck: (String, Boolean) -> Boolean = { input, ignoreCase ->
			completion.any { it.equals(input, ignoreCase) }
		}

	}

	data class Asset(
		val asset: CompletionAsset,
		override val displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
		override val accessApproval: Approval? = null,
	) : CompletionComponent {

		override fun completion() = asset.computedContent

		override val label = "<${asset.identity}>"

		override val inputExpressionCheck: (String, Boolean) -> Boolean = check@{ input, ignoreCase ->
			return@check asset.check?.invoke(input, ignoreCase) ?: true
		}

	}

}