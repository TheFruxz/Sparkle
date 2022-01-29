package de.jet.paper.structure.command.completion

data class CompletionBranchConfiguration(
	val supportedInputTypes: MutableList<CompletionInputType> = mutableListOf(),
	var isRequired: Boolean = true,
	var mustMatchOutput: Boolean = true,
	var infiniteSubParameters: Boolean = false,
	var executeIndividualAssetTest: Boolean = true,
)
