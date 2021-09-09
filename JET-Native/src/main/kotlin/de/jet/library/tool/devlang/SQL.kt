package de.jet.library.tool.devlang

import org.intellij.lang.annotations.Language

data class SQL(
	@Language("sql")
	override val value: String,
) : DevLangObject
