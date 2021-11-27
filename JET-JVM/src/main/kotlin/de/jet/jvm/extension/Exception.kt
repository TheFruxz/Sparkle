package de.jet.jvm.extension

import kotlin.random.Random

/**
 * Gets the exception [exception] and prints a beatiful stack trace & message.
 * @param exception the exception to print
 * @author Fruxz
 * @since 1.0
 */
fun catchException(exception: Exception) {

	val exceptionIdentity = Random.nextInt(10000, 99999)
	val exceptionTag = "#$exceptionIdentity"
	val exceptionShort = exception.stackTrace.firstOrNull()?.className ?: "Can't get short!"

	println(" > $exceptionTag - $exceptionShort")
	exception.printStackTrace()
	println(" < $exceptionTag - $exceptionShort")

}

/**
 * Sourrunds the [tryBlock] with a try catch block and if the
 * try catch block catches an exception it will execute [catchException].
 * @param catchBlock the block to execute if the try catch block catches an exception, before the [catchException] is executed
 * @param tryBlock the block to execute
 * @author Fruxz
 * @since 1.0
 */
fun jetTry(catchBlock: () -> Unit = { }, tryBlock: () -> Unit) {
	try {
		tryBlock()
	} catch (e: Exception) {
		catchBlock()
		catchException(e)
	}
}