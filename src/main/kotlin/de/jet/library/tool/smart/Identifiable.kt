package de.jet.library.tool.smart

interface Identifiable<T> {

	val id: String

	fun equals(identifiable: Identifiable<*>) = identifiable.id == id

	val identityObject: Identity<T>
		get() = Identity(id)

	companion object {

		fun dummy() = object : Identifiable<Any> {
			override val id = "dummy"
		}

		fun <T> custom(id: String) = object : Identifiable<T> {
			override val id = id
		}

	}

}