package de.jet.library.tool.base

interface Constructable<T : Any> {

	/**
	 * Construct
	 * @throws IllegalArgumentException if [parameters] is invalid at the target [T]
	 */
	fun constructor(vararg parameters: Any?): T

}