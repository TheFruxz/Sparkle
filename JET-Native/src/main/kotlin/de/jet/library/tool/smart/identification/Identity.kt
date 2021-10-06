package de.jet.library.tool.smart.identification

data class Identity<T> constructor(
	val identity: String,
) {

	override fun toString() = identity

}
