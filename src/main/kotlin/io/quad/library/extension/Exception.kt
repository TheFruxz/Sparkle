package io.quad.library.extension

import java.lang.Exception

fun catchException(exception: Exception) {

	debugLog("caught exception: '${exception.localizedMessage}'")
	exception.printStackTrace()

	// TODO: 11.07.2021 Nice error message

}