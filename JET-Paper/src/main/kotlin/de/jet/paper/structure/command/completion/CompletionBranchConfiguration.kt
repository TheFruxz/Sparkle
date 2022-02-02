package de.jet.paper.structure.command.completion

data class CompletionBranchConfiguration(
	val supportedInputTypes: MutableList<CompletionInputType> = mutableListOf(CompletionInputType.ANY),
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

fun CompletionBranch.infiniteSubParameters() = configure {
	infiniteSubParameters = true
	mustMatchOutput = false
}

fun CompletionBranch.limitedSubParameters() = configure { infiniteSubParameters = false }

fun CompletionBranch.onlyAccept(vararg inputTypes: CompletionInputType) = configure {
	supportedInputTypes.clear()
	supportedInputTypes.addAll(inputTypes)
}
