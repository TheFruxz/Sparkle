package de.jet.library.tool.devlang

import org.intellij.lang.annotations.Language

data class JSON(
	@Language("json")
	override val value: String,
) : DevLangObject