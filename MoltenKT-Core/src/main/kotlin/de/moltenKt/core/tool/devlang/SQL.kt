package de.moltenKt.core.tool.devlang

import de.moltenKt.core.tool.base.Constructable
import org.intellij.lang.annotations.Language

/**
 * This data class represents a chunk of SQL code stored inside the [value]
 * @author Fruxz
 * @since 1.0
 */
data class SQL(
	@Language("sql")
	override val value: String,
) : DevLangObject {

	override fun toString() =
		value

	companion object : Constructable<SQL> {

		@JvmStatic
		override fun constructor(vararg parameters: Any?): SQL =
			SQL("" + parameters.first())

	}

}
