package de.jet.library.tool.smart

data class Identity<T> internal constructor(
	val identity: String,
) {

	override fun toString() = identity

}
