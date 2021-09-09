package de.jet.library.tool.devlang

import org.intellij.lang.annotations.Language

data class YAML(
	@Language("yaml")
	override val value: String
) : DevLangObject