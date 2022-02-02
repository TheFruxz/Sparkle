package de.jet.paper.structure.command.completion

import de.jet.jvm.extension.math.isDouble
import de.jet.jvm.extension.math.isLong

interface CompletionInputType {

	fun isValid(input: String): Boolean

	companion object {

		val NONE = object : CompletionInputType {
			override fun isValid(input: String) = false
		}

		val ANY = object : CompletionInputType {
			override fun isValid(input: String) = true
		}

		val STRING = object : CompletionInputType {
			override fun isValid(input: String) = true
		}

		val LONG = object : CompletionInputType {
			override fun isValid(input: String) = input.isLong()
		}

		val DOUBLE = object : CompletionInputType {
			override fun isValid(input: String) = input.isDouble()
		}

		val BOOLEAN = object : CompletionInputType {
			override fun isValid(input: String) = input.toBooleanStrictOrNull() != null
		}

	}

}