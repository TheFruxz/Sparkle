package de.jet.library.extension

import kotlin.random.Random

fun catchException(exception: Exception) {

	val exceptionIdentity = Random.nextInt(10000, 99999)
	val exceptionTag = "#$exceptionIdentity"
	val exceptionShort = exception.stackTrace.firstOrNull()?.className ?: "Can't get short!"

	println(" > $exceptionTag - $exceptionShort")
	exception.printStackTrace()
	println(" < $exceptionTag - $exceptionShort")

}

fun jetTry(catchBlock: () -> Unit = { }, tryBlock: () -> Unit) {
	try {
		tryBlock()
	} catch (e: Exception) {
		catchBlock()
		catchException(e)
	}
}