package de.moltenKt.core.interchange

import de.moltenKt.core.extension.input.buildConsoleInterchange
import de.moltenKt.core.extension.switchResult

/**
 * This data class represents a configuration of multiple
 * usable interchanges, a reaction to failure and a
 * reaction to success.
 * @param interchanges A list of all usable interchanges.
 * @param onFail A reaction to failure.
 * @param onSuccess A reaction to success.
 * @author Fruxz
 * @since 1.0
 */
data class ConsoleInterchangeConfiguration(
	val interchanges: List<ConsoleInterchangeBranch>,
	val onFail: (input: String) -> Unit = {},
	val onSuccess: (input: String) -> Unit = {},
) {

	/**
	 * This function executes an interchange call onto all given interchanges.
	 * @param input The input to be passed onto the interchanges.
	 * @return The result of the interchange call.
	 * @author Fruxz
	 */
	fun executeCommand(input: String): Boolean {

		fun executeBase(input: String): Boolean {
			buildConsoleInterchange("") {
				subBranches = interchanges
			}.getBestMatchFromCommandInputWithParameters(input).let { result ->
				result.first.let { branch ->
					if (branch != null) {
						branch.content?.let { content ->
							content.invoke(result.second)
							return true
						}
					}
				}
			}
			return false
		}

		return executeBase(input)
			.also { it.switchResult(onSuccess, onFail)(input) }

	}

}
