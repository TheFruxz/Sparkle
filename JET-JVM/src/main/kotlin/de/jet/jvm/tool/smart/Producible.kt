package de.jet.jvm.tool.smart

/**
 * This interface marks every class/object, that can produce an object type [T]
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