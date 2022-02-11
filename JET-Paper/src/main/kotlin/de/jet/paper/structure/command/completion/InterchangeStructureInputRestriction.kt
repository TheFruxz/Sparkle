package de.jet.paper.structure.command.completion

import de.jet.jvm.extension.math.isDouble
import de.jet.jvm.extension.math.isLong

interface InterchangeStructureInputRestriction {

	fun isValid(input: String): Boolean

	companion object {

		val NONE = object : InterchangeStructureInputRestriction {
			override fun isValid(input: String) = false
		}

		val ANY = object : InterchangeStructureInputRestriction {
			override fun isValid(input: String) = true
		}

		val STRING = object : InterchangeStructureInputRestriction {
			override fun isValid(input: String) = true
		}

		val LONG = object : InterchangeStructureInputRestriction {
			override fun isValid(input: String) = input.isLong()
		}

		val DOUBLE = object : InterchangeStructureInputRestriction {
			override fun isValid(input: String) = input.isDouble()
		}

		val BOOLEAN = object : InterchangeStructureInputRestriction {
			override fun isValid(input: String) = input.toBooleanStrictOrNull() != null
		}

	}

}