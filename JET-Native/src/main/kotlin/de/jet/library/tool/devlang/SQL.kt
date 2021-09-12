package de.jet.library.tool.devlang

import de.jet.library.tool.base.Constructable
import org.intellij.lang.annotations.Language

data class SQL(
	@Language("sql")
	override val value: String,
) : DevLangObject {

	companion object : Constructable<SQL> {
		override fun constructor(vararg parameters: Any?): SQL =
			SQL("" + parameters.first())
	}

}
