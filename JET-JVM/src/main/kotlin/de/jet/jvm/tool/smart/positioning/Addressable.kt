package de.jet.jvm.tool.smart.positioning

/**
 * Interface for objects that can be addressed by a [Address].
 * @author Fruxz
 * @since 1.0
 */
interface Addressable<T> {

	/**
	 * The address of the object as a [String].
	 * @author Fruxz
	 * @since 1.0
	 */
	val addressString: String
		get() = address.addressString

	/**
	 * The address of the object as a [Address]
	 * @author Fruxz
	 * @since 1.0
	 */
	val address: Address<T>

	val isRoot: Boolean
		get() = !addressString.contains(address.divider) || addressString == address.divider

}