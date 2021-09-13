package de.jet.library.tool.smart

data class Identity<T> constructor(
	val identity: String,
) {

	override fun toString() = identity

}
