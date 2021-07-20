package io.quad.library.extension

import kotlin.Exception
import kotlin.random.Random

fun catchException(exception: Exception) {

	val exceptionIdentity = Random.nextInt(10000, 99999)

	debugLog("caught exception: '${exception.localizedMessage}'")
	exception.printStackTrace()

	// TODO: 11.07.2021 Nice error message

}

fun quadTry(catchBlock: () -> Unit = { }, tryBlock: () -> Unit) {
	try {
		tryBlock()
	} catch (e: Exception) {
		catchBlock()
		catchException(e)
	}
}