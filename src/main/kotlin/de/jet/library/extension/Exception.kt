package de.jet.library.extension

import kotlin.Exception
import kotlin.random.Random

fun catchException(exception: Exception) {

	val exceptionIdentity = Random.nextInt(10000, 99999)
	val exceptionTag = "#$exceptionIdentity"

	debugLog("[$exceptionTag] caught exception: '${exception.localizedMessage}'")
	println(" > $exceptionTag - ${exception.localizedMessage}")
	exception.printStackTrace()
	println(" < $exceptionTag - ${exception.localizedMessage}")

	// TODO: 11.07.2021 Nice error message

}

fun jetTry(catchBlock: () -> Unit = { }, tryBlock: () -> Unit) {
	try {
		tryBlock()
	} catch (e: Exception) {
		catchBlock()
		catchException(e)
	}
}