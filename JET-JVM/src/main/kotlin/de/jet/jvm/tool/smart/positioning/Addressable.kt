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
		get() = addressObject.addressString

	/**
	 * The address of the object as a [Address]
	 * @author Fruxz
	 * @since 1.0
	 */
	val addressObject: Address<T>

	val rootCheck: (String) -> Boolean
		get() = { !it.contains(addressObject.divider) || it == addressObject.divider }

	val isRoot: Boolean
		get() = rootCheck(addressString)

}