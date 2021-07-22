package de.jet.library.structure.smart

interface Identifiable<T> {

	val id: String

	companion object {

		fun dummy() = object : Identifiable<Any> {
			override val id = "dummy"
		}

	}

}