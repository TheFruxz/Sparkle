package de.jet.jvm.tool.smart.type

/**
 * Operation results, that depends on unsafe factors, like user input.
 * if failed or denied the [atBreak] function would be called, if
 * succeeded, the [atSuccess] function would be called
 * @param atBreak function, that would be called, if the operation is denied
 * @param atSuccess function, that would be called, if the operation is succeeded
 * @author Fruxz
 * @since 1.0
 */
class Breakable<STATE, RESULT> private constructor(
	var atBreak: STATE.(RESULT) -> Unit,
	var atSuccess: STATE.(RESULT) -> Unit,
) {

	/**
	 * Replaces the [atBreak] function with the [process]
	 * @param process function, that would be called, if the operation is denied
	 * @return the [Breakable] instance
	 * @author Fruxz
	 * @since 1.0
	 */
	infix fun atBreak(process: STATE.(RESULT) -> Unit) = apply {
		atBreak = process
	}

	/**
	 * Replaces the [atSuccess] function with the [process]
	 * @param process function, that would be called, if the operation is succeeded
	 * @return the [Breakable] instance
	 * @author Fruxz
	 * @since 1.0
	 */
	infix fun atSuccess(process: STATE.(RESULT) -> Unit) = apply {
		atSuccess = process
	}

}