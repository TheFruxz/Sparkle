package de.jet.jvm.tool.devlang

import de.jet.jvm.tool.base.Constructable
import org.intellij.lang.annotations.Language

/**
 * This data class represents a chunk of YAML code stored inside the [value]
 * @author Fruxz
 * @since 1.0
 */
data class YAML(
	@Language("yaml")
	override val value: String
) : DevLangObject {

	override fun toString() =
		value

	companion object : Constructable<YAML> {

		@JvmStatic
		override fun constructor(vararg parameters: Any?): YAML =
			YAML("" + parameters.first())

	}

}