package de.moltenKt.core.tool.smart.positioning

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
		get() = addressObject.addressString

	/**
	 * The address of the object as a [Address]
	 * @author Fruxz
	 * @since 1.0
	 */
	val addressObject: Address<T>

	val isRoot: Boolean
		get() = !addressString.contains(addressObject.divider) || addressString == addressObject.divider

}