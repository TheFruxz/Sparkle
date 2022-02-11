package de.jet.paper.structure.command.completion

import de.jet.jvm.extension.empty
import de.jet.jvm.extension.math.isDouble
import de.jet.jvm.extension.math.isLong

interface InterchangeStructureInputRestriction<DATATYPE> {

	fun isValid(input: String): Boolean

	fun transformer(input: String): DATATYPE

	companion object {

		val NONE = object : InterchangeStructureInputRestriction<Unit> {
			override fun isValid(input: String) = false
			override fun transformer(input: String) = empty()
		}

		val ANY = object : InterchangeStructureInputRestriction<String> {
			override fun isValid(input: String) = true
			override fun transformer(input: String) = input
		}

		val STRING = object : InterchangeStructureInputRestriction<String> {
			override fun isValid(input: String) = true
			override fun transformer(input: String) = input
		}

		val LONG = object : InterchangeStructureInputRestriction<Long> {
			override fun isValid(input: String) = input.isLong()
			override fun transformer(input: String) = input.toLong()
		}

		val DOUBLE = object : InterchangeStructureInputRestriction<Double> {
			override fun isValid(input: String) = input.isDouble()
			override fun transformer(input: String) = input.toDouble()
		}

		val BOOLEAN = object : InterchangeStructureInputRestriction<Boolean> {
			override fun isValid(input: String) = input.toBooleanStrictOrNull() != null
			override fun transformer(input: String) = input.toBooleanStrict()
		}

	}

}