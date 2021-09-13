package de.jet.library.tool.devlang

import de.jet.library.extension.data.toJson
import de.jet.library.tool.base.Constructable
import org.intellij.lang.annotations.Language

data class JSON(
	@Language("json")
	override val value: String,
) : DevLangObject {

	fun <T> toJson() = value.toJson()

	companion object : Constructable<JSON> {
		override fun constructor(vararg parameters: Any?): JSON =
			JSON("" + parameters.first())
	}

}