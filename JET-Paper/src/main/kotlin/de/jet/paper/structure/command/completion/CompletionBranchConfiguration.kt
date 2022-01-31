package de.jet.paper.structure.command.completion

data class CompletionBranchConfiguration(
	val supportedInputTypes: MutableList<CompletionInputType> = mutableListOf(), // TODO: 31.01.2022 Should be single? //
	var isRequired: Boolean = true, //
	var ignoreCase: Boolean = false, //
	var mustMatchOutput: Boolean = true, //
	var infiniteSubParameters: Boolean = false,
)

fun CompletionBranch.isNotRequired() = configure { isRequired = false }

fun CompletionBranch.isRequired() = configure { isRequired = true }

fun CompletionBranch.ignoreCase() = configure { ignoreCase = true }

fun CompletionBranch.restrictCase() = configure { ignoreCase = false }

fun CompletionBranch.mustMatchOutput() = configure { mustMatchOutput = true }

fun CompletionBranch.mustNotMatchOutput() = configure { mustMatchOutput = false }

fun CompletionBranch.infiniteSubParameters() = configure { infiniteSubParameters = true }

fun CompletionBranch.limitedSubParameters() = configure { infiniteSubParameters = false }
