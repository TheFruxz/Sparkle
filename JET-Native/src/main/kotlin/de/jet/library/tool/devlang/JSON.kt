package de.jet.library.tool.devlang

import de.jet.library.extension.data.toJson
import org.intellij.lang.annotations.Language

data class JSON(
	@Language("json")
	override val value: String,
) : DevLangObject {

	fun <T> toJson() = value.toJson()

}