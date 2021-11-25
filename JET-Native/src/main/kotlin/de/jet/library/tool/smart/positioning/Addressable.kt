package de.jet.library.tool.smart.positioning

interface Addressable<T> {

	val addressString: String
		get() = address.addressString

	val address: Address<T>

}