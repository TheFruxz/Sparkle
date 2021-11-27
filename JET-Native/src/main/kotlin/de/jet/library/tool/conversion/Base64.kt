package de.jet.library.tool.conversion

import java.util.Base64

/**
 * This object provides methods to encode and decode strings to and from Base64.
 */
object Base64 {

	/**
	 * From String to Base64
	 */
	fun encodeToString(input: String): String = Base64.getEncoder().encodeToString(input.encodeToByteArray())

	/**
	 * From Base64 to String
	 */
	fun decodeToString(input: String): String = Base64.getDecoder().decode(input.encodeToByteArray()).decodeToString()

}