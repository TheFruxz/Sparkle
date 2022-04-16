package de.moltenKt.core.tool.smart.positioning

/**
 * This interface represents an object, which has a specific
 * path inside a pathed structure.
 * @author Fruxz
 * @since 1.0
 */
@Suppress("SpellCheckingInspection")
interface Pathed<T> : Addressable<T> {

	/**
	 * This is the path of this object, which
	 * represents the address of the object
	 * inside the pathed structure.
	 * @author Fruxz
	 * @since 1.0
	 */
	override val addressObject: Address<T>
		get() = address

	/**
	 * This is the path of this object, which
	 * represents the address of the object
	 * inside the pathed structure.
	 * @author Fruxz
	 * @since 1.0
	 */
	val address: Address<T>

	/**
	 * This value represents the different parts
	 * of the paths of this object.
	 * @author Fruxz
	 * @since 1.0
	 */
	val pathParts: List<String>
		get() {
			var isDirectory = false
			var pathString = address.addressString

			if (pathString.endsWith("/")) {
				isDirectory = true
				pathString = pathString.removeSuffix("/")
			}

			val list = pathString.removePrefix("/").split("/")

			return list.mapIndexed { index, s ->
				if (isDirectory && index == list.lastIndex) {
					"$s/"
				} else {
					s
				}
			}
		}

}