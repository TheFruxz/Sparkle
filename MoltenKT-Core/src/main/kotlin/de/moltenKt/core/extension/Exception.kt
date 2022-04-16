package de.moltenKt.core.extension

import de.moltenKt.core.tool.exception.ExceptionHandler
import kotlin.random.Random

/**
 * Gets the exception [exception] and prints a beautiful stack trace & message.
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
 * This function creates a [ExceptionHandler] and places its try block to the [tryBlock]
 * parameter from this function.
 * @param tryBlock the try block to place
 * @return the [ExceptionHandler]
 * @author Fruxz
 * @since 1.0
 */
fun <O> doTry(tryBlock: () -> O) =
	ExceptionHandler(tryBlock, null)

/**
 * Try to execute the code specified inside the [process] function.
 * If an exception is thrown, the [catchException] function will be executed.
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param process the code to execute
 * @author Fruxz
 * @since 1.0
 */
fun <A> A.tryToCatch(process: A.() -> Unit) {
	try {
		process(this)
	} catch (e: Exception) {
		catchException(e)
	}
}

/**
 * Try return the value and returns the result inside a [Result] object.
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param T is the return type of the process
 * @param process the process to execute, returning the normal value as [T]
 * @return the value returned by the [process] as a [Result]
 * @author Fruxz
 * @since 1.0
 */
fun <A, T> A.tryToResult(process: A.() -> T): Result<T> {
	return try {
		Result.success(process())
	} catch (e: Exception) {
		Result.failure(e)
	}
}

/**
 * Try return the value returning of the [process] or returns the [other].
 * [other]-return is triggered by a thrown [Exception]
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param R is the return type of the process
 * @param T is the [other] type
 * @param other the value to return if the process throws an [Exception]
 * @param process the process to execute, returning the normal value as [R]
 * @return the value returned by the [process] or the [other]
 * @author Fruxz
 * @since 1.0
 */
fun <A, R, T : R> A.tryOrElse(other: T, process: A.() -> R): R {
	return tryToResult(process).getOrElse { other }
}

/**
 * Try return the value returning of the [process] or returns null.
 * null-return is triggered by a thrown [Exception]
 * @param A (short for air) is the type of the surrounding block or the object, where it is called from
 * @param T is the return type of the process
 * @param process the process to execute, returning the normal value as [T]
 * @return the value returned by the [process] or null
 * @author Fruxz
 * @since 1.0
 */
fun <A, T> A.tryOrNull(process: A.() -> T): T? {
	return tryToResult(process).getOrNull()
}

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, nothing happens after the exception.
 * @author Fruxz
 * @since 1.0
 */
fun <A> A.tryToIgnore(process: A.() -> Unit) {
	tryToResult(process).dump()
}

/**
 * Try to execute the code specified inside the [process] function.
 * if an exception is thrown, the stack trace will be printed.
 */
fun <A> A.tryToPrint(process: A.() -> Unit) {
	tryToResult(process).exceptionOrNull()?.printStackTrace()
}