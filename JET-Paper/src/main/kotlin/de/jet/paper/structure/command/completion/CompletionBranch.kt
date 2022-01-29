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

	fun content(vararg completionComponents: CompletionComponent) =
		content(content = completionComponents.toList())

	fun computePossibleLevelCompletion(): List<String> {
		return content.flatMap { it.completion() }
	}

	fun computePossibleFutureLevelCompletions(): List<String> {
		return subBranches.flatMap { it.computePossibleLevelCompletion() }
	}

	fun isInputAllowedByTypes(input: String) =
		content.flatMap { if (it is CompletionComponent.Asset) it.asset.supportedInputType else emptyList() }
			.any { it.check?.let { it1 -> it1(input) } ?: true }

	fun computeLocalCompletion() = content.flatMap { it.completion() }

	fun validateInput(parameters: List<String>): Boolean {
		var currentBranches = listOf(this)
		var query = parameters.firstOrNull() ?: ""
		var depth = 0

		for (x in 1..parameters.size) {
			val nextBranches = currentBranches.flatMap { it.subBranches }
			query = parameters[x - 1]
			if (nextBranches.isEmpty()) break
			currentBranches = nextBranches.filter {
				query.isBlank()
						|| it.computeLocalCompletion().mapToLowercase().any { it.contains(query, true) }
						|| (!it.configuration.mustMatchOutput && it.isInputAllowedByTypes(query))
			}
			depth++
		}

		println(currentBranches.size)
		if (parameters.size > depth && currentBranches.none { it.configuration.infiniteSubParameters }) {
			return false
		} else
			return currentBranches.none { it.subBranches.any { it.configuration.isRequired } }
	}

	fun computeCompletion(parameters: List<String>): List<String> {

		var currentBranches = listOf(this)
		var query = parameters.firstOrNull() ?: ""
		var depth = 0

		for (x in 1..parameters.size) {
			val nextBranches = currentBranches.flatMap { it.subBranches }
			query = parameters[x - 1]
			if (nextBranches.isEmpty()) break
			currentBranches = nextBranches.filter {
				query.isBlank()
						|| it.computeLocalCompletion().mapToLowercase().any { it.contains(query, true) }
						|| (!it.configuration.mustMatchOutput && it.isInputAllowedByTypes(query))
			}
			depth++
		}

		return if (parameters.size > depth) {
			listOf(" ")
		} else
			currentBranches.flatMap { it.computeLocalCompletion() }.partition { it.startsWith(query, true) }.let {
				it.first + it.second.filter { it.contains(query, true) }
			}
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

fun emptyCompletion() =
	buildCompletion(process = { })