package de.moltenKt.core.tool.smart.identification

/**
 * This interface marks every object that can be identified
 * using the [Identity] object and an identity [String].
 * @param T the type of the object that is identifiable
 * @author Fruxz
 * @since 1.0
 */
interface Identifiable<T> : AnonymousIdentifiable {

	override val identity: String

	/**
	 * Represents the identity of the object as an [Identity]
	 * type [T].
	 * @author Fruxz
	 * @since 1.0
	 */
	val identityObject: Identity<T>
		get() = Identity(identity)

	companion object {

		/**
		 * Generates a new [Identifiable]<[Any]> object, which
		 * has an empty [String] as the identity.
		 * @return the new [Identifiable]<[Any]> object
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun empty() = object : Identifiable<Any> {
			override val identity = ""
		}

		/**
		 * Generates a new [Identifiable]<[T]> object, which
		 * has the [String] [id] as the identity set.
		 * @param id the identity of the object
		 * @param T the type of the object that is identifiable
		 * @return the new [Identifiable]<[T]> object
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun <T> custom(id: String) = object : Identifiable<T> {
			override val identity = id
		}

	}

}