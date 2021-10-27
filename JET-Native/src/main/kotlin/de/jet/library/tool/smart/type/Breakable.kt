package de.jet.library.tool.smart.type

/**
 * Operation results, that depends on unsafe factors, like user input.
 * if failed or denied the [atBreak] function would be called, if
 * succeeded, the [atSuccess] function would be called
 */
class Breakable<STATE, RESULT> private constructor(
	var atBreak: STATE.(RESULT) -> Unit,
	var atSuccess: STATE.(RESULT) -> Unit,
) {

	infix fun atBreak(process: STATE.(RESULT) -> Unit) = apply {
		atBreak = process
	}

	infix fun atSuccess(process: STATE.(RESULT) -> Unit) = apply {
		atSuccess = process
	}

}