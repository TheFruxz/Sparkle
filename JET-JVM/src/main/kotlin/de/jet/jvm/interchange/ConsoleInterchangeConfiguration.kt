package de.jet.jvm.interchange

import de.jet.jvm.extension.switchResult

data class ConsoleInterchangeConfiguration(
	val interchanges: List<ConsoleInterchangeBranch>,
	val onFail: (input: String) -> Unit = {},
	val onSuccess: (input: String) -> Unit = {},
) {

	fun executeCommand(input: String): Boolean {

		fun executeBase(input: String): Boolean {
			interchanges.forEach {
				it.getBestMatchFromCommandInputWithParameters(input).let { result ->
					result.first.let { branch ->
						if (branch != null) {
							branch.content?.let { content ->
								content.invoke(result.second)
								return true
							}
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
