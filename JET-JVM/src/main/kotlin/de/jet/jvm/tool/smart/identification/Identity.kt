package de.jet.jvm.tool.smart.identification

/**
 * This data class represents an identity, which
 * should be (in the most cases) unique inside
 * the [T] environment.
 *
 * This data class helps to easily identify and
 * compare objects.
 *
 * @param T The owner type of the identity.
 * @param identity The identity itself.
 * @author Fruxz
 * @since 1.0
 */
data class Identity<T> constructor(
	override val identity: String,
) : Identifiable<T> {

	/**
	 * Returns the [identity] (in the form of a [String])
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun toString() = identity

	/**
	 * This function returns this but changes the [T] type
	 * from the [Identity] to a [O] type, without changing
	 * the [identity] itself.
	 * @return The [Identity] with the [O] type instead of [T].
	 * @author Fruxz
	 * @since 1.0
	 */
	fun <O> change() = Identity<O>(identity)

}
