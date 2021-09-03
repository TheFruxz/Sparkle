package de.jet.library.tool.smart

interface Identifiable<T> {

	val identity: String

	val identityObject: Identity<T>
		get() = Identity(identity)

	companion object {

		fun empty() = object : Identifiable<Any> {
			override val identity = ""
		}

		fun <T> custom(id: String) = object : Identifiable<T> {
			override val identity = id
		}

	}

}