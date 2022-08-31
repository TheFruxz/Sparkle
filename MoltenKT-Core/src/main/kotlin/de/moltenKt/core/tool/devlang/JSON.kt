package de.moltenKt.core.tool.devlang

import de.moltenKt.core.extension.data.fromJson
import de.moltenKt.core.extension.data.fromJsonString
import de.moltenKt.core.tool.base.Constructable
import org.intellij.lang.annotations.Language

/**
 * This data class represents a chunk of JSON code stored inside the [value]
 * @author Fruxz
 * @since 1.0
 */
data class JSON(
	@Language("json")
	override val value: String,
) : DevLangObject {

	/**
	 * Converts the [value] to a [T] object using the [fromJson] function
	 * @author Fruxz
	 * @since 1.0
	 */
	inline fun <reified T : Any> fromJson() = value.fromJsonString<T>()

	override fun toString() =
		value

	companion object : Constructable<JSON> {

		@JvmStatic
		override fun constructor(vararg parameters: Any?): JSON =
			JSON(parameters.first() as String)
	}

}