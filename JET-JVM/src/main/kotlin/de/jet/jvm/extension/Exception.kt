package de.jet.jvm.extension

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
 * Surrounds the [tryBlock] with a try catch block and if the
 * try catch block catches an exception it will execute [catchException].
 * @param catchBlock the block to execute if the try catch block catches an exception, before the [catchException] is executed
 * @param tryBlock the block to execute
 * @author Fruxz
 * @since 1.0
 */
@Deprecated(message = "Do not use this, in future, a proper try-catch object would be available!")
fun jetTry(catchBlock: () -> Unit = { }, tryBlock: () -> Unit) {
	try {
		tryBlock()
	} catch (e: Exception) {
		catchBlock()
		catchException(e)
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
	return try {
		process()
	} catch (e: Exception) {
		other
	}
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
	return try {
		process()
	} catch (e: Exception) {
		null
	}
}

/**
 * Try return the value returning of the [process] or returns null.
 * null-return is triggered by a thrown [Exception]
 * @param T is the return type of the process
 * @param process the process to execute, returning the normal value as [T]
 * @return the value returned by the [process] or null
 * @author Fruxz
 * @since 1.0
 */
fun <T> tryOrNull(process: () -> T): T? {
	return try {
		process()
	} catch (e: Exception) {
		null
	}
}