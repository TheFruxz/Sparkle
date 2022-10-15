package de.fruxz.sparkle.framework.infrastructure.command.completion

import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructureInputRestriction.Companion.ANY

data class CompletionBranchConfiguration(
	var supportedInputTypes: Set<InterchangeStructureInputRestriction<*>> = setOf(ANY),
	var isRequired: Boolean = true,
	var ignoreCase: Boolean = false,
	var mustMatchOutput: Boolean = true,
	var infiniteSubParameters: Boolean = false,
)

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.isNotRequired() = configure { isRequired = false }

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.isRequired() = configure { isRequired = true }

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.ignoreCase() = configure { ignoreCase = true }

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.restrictCase() = configure { ignoreCase = false }

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.mustMatchOutput() = configure { mustMatchOutput = true }

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.mustNotMatchOutput() = configure {
	mustMatchOutput = false
	ignoreCase = true
}

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.infiniteSubParameters() = configure {
	infiniteSubParameters = true
	mustMatchOutput = false
}

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.limitedSubParameters() = configure { infiniteSubParameters = false }

fun <EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.onlyAccept(vararg inputTypes: InterchangeStructureInputRestriction<*>) = configure {
	supportedInputTypes = inputTypes.toSet()
}
