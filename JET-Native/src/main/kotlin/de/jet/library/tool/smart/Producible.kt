package de.jet.library.tool.smart

/**
 * This interface marks every class/object, that can produce a object type [T]
 * @author Fruxz
 * @since 1.0
 */
interface Producible<T : Any> {

	/**
	 * Produces the product [T]
	 * @return the product [T]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun produce(): T

}