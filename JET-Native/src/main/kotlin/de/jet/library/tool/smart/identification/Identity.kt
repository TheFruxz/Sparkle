package de.jet.library.tool.smart.identification

data class Identity<T> constructor(
	override val identity: String,
) : Identifiable<T> {

	override fun toString() = identity

	fun <O> change() = Identity<O>(identity)

}
