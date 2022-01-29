package de.jet.paper.structure.command.completion

import de.jet.jvm.extension.collection.mapToLowercase
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranch
import de.jet.jvm.tree.TreeBranchType
import de.jet.paper.structure.command.completion.CompletionBranch.Companion.GENERIC_IDENTITY_NAME
import de.jet.paper.structure.command.completion.component.CompletionComponent

class CompletionBranch(
	override var identity: String = GENERIC_IDENTITY_NAME,
	override var path: Address<TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>> = Address.address(
		"/"
	),
	override var subBranches: List<CompletionBranch> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	override var content: List<CompletionComponent> = emptyList(),
) : TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>(
	identity,
	path,
	TreeBranchType.OBJECT,
	subBranches,
	content
) {

	var configuration = configuration
		private set

	fun buildSyntax() = buildString {
		fun printSubBranches(level: Int = 0, subBranches: List<CompletionBranch>) {
			subBranches.forEach { subBranch ->
				val branchConfig = subBranch.configuration
				appendLine(buildString {
					repeat(level) { append("  ") }
					if (level > 0) append("|- ") else append("/")
					append(buildString {
						if (level > 0) append("(")

						append(subBranch.content.joinToString("|") { it.label }.let { display ->
							display.ifBlank { subBranch.identity }
						})

						if (level > 0) {
							append(")")
							if (!branchConfig.isRequired) append("?")
							if (branchConfig.mustMatchOutput) append("^")
							if (branchConfig.infiniteSubParameters) append("*")
						}
					})
				})
				if (subBranch.subBranches.isNotEmpty())
					printSubBranches(level + 1, subBranch.subBranches)
			}
		}

		printSubBranches(subBranches = listOf(this@CompletionBranch))

	}

	fun computePossibleLevelCompletion(): List<String> {
		return content.flatMap { it.completion() }
	}

	fun computePossibleFutureLevelCompletions(): List<String> {
		return subBranches.flatMap { it.computePossibleLevelCompletion() }
	}

	fun isInputAllowed(input: String) = content.flatMap { if (it is CompletionComponent.Asset) it.asset.supportedInputType else emptyList() }.none { it.check?.let { it1 -> it1(input) } ?: true}

	fun computeCompletion(parameters: List<String>): List<String> {

		var currentBranches = listOf(this)
		var currentInput = parameters
		var depth = 0

		while (currentInput.isNotEmpty()) {

			val query = currentInput.first()
			val matchingBranches = currentBranches.flatMap {
				it.subBranches.filter {
					it.computePossibleLevelCompletion().mapToLowercase().contains(query.lowercase()) || it.isInputAllowed(query)
				}
			}.toMutableList()

			if (matchingBranches.size > 1) {
				matchingBranches.removeAll {
					it.configuration.supportedInputTypes.none {
						it.check?.invoke(query) ?: true
					}
				}
			}

			if (matchingBranches.isEmpty()) {
				break
			} else {
				depth++
				println("depth: $depth | parameters: ${parameters.size} | currentInput: ${currentInput.size}")
				if (depth < parameters.size) {
					currentBranches = matchingBranches
					currentInput = currentInput.drop(1)
				} else
					break
			}

		}

		val out = currentBranches.flatMap { it.computePossibleFutureLevelCompletions() }

		return out
			.filter { it.lowercase().startsWith(currentInput.firstOrNull()?.lowercase() ?: "") }.sorted()
			.plus(out.filter {
				it.lowercase().contains(currentInput.firstOrNull()?.lowercase() ?: "") && !it.lowercase()
					.startsWith(currentInput.firstOrNull()?.lowercase() ?: "")
			}.sorted())

	}

	fun configure(process: CompletionBranchConfiguration.() -> Unit) {
		configuration = configuration.apply(process)
	}

	fun branch(
		identity: String = GENERIC_IDENTITY_NAME,
		path: Address<TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>> = this.path / identity,
		configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
		process: CompletionBranch.() -> Unit,
	) {
		subBranches += CompletionBranch(
			identity = identity,
			path = path,
			subBranches = emptyList(),
			configuration = configuration,
			content = emptyList(),
		).apply(process)
	}

	fun addContent(vararg components: CompletionComponent) {
		content += components
	}

	companion object {

		internal const val GENERIC_IDENTITY_NAME = "GENERIC_INPUT"

	}

}

fun buildCompletion(
	identity: String = GENERIC_IDENTITY_NAME,
	path: Address<TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>> = Address.address("/"),
	subBranches: List<CompletionBranch> = emptyList(),
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	content: List<CompletionComponent> = emptyList(),
	process: CompletionBranch.() -> Unit,
) = CompletionBranch(identity, path, subBranches, configuration, content).apply(process)