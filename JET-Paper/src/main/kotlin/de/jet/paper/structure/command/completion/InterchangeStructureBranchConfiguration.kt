package de.jet.paper.structure.command.completion

data class CompletionBranchConfiguration(
	val supportedInputTypes: MutableList<InterchangeStructureInputRestriction<*>> = mutableListOf(InterchangeStructureInputRestriction.ANY),
	var isRequired: Boolean = true, //
	var ignoreCase: Boolean = false, //
	var mustMatchOutput: Boolean = true, //
	var infiniteSubParameters: Boolean = false,
)

fun InterchangeStructure.isNotRequired() = configure { isRequired = false }

fun InterchangeStructure.isRequired() = configure { isRequired = true }

fun InterchangeStructure.ignoreCase() = configure { ignoreCase = true }

fun InterchangeStructure.restrictCase() = configure { ignoreCase = false }

fun InterchangeStructure.mustMatchOutput() = configure { mustMatchOutput = true }

fun InterchangeStructure.mustNotMatchOutput() = configure { mustMatchOutput = false }

fun InterchangeStructure.infiniteSubParameters() = configure {
	infiniteSubParameters = true
	mustMatchOutput = false
}

fun InterchangeStructure.limitedSubParameters() = configure { infiniteSubParameters = false }

fun InterchangeStructure.onlyAccept(vararg inputTypes: InterchangeStructureInputRestriction<*>) = configure {
	supportedInputTypes.clear()
	supportedInputTypes.addAll(inputTypes)
}
