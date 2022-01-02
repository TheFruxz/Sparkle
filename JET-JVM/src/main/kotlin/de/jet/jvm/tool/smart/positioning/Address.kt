package de.jet.jvm.tool.smart.positioning

import de.jet.jvm.tool.smart.identification.Identifiable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This class represents an address (inside a path-structure).
 * @param addressString the address as string
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("smartAddress")
data class Address<T> internal constructor(
	@SerialName("path") override val addressString: String
) : Addressable<T>, Identifiable<T> {

	override val identity = addressString

	/**
	 * Returns the [addressString]
	 * @return the [addressString]
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun toString() = addressString

	override val address: Address<T>
		get() = address(addressString)

	operator fun div(addition: String) = copy(addressString = "$addressString/$addition")

	companion object {

		/**
		 * Generates an [Addressed]<[T]> from the [Address]'s class
		 * internal constructor.
		 * @param path the path as a string
		 * @return the [Addressed]<[T]>
		 * @author Fruxz
		 * @since 1.0
		 */
		fun <T> address(path: String) =
			Address<T>(path)

	}

}