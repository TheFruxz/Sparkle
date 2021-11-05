package de.jet.library.tool.smart.positioning

import de.jet.library.tool.smart.identification.Identifiable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("smartAddress")
data class Address<T> internal constructor(
	@SerialName("path") override val addressString: String
) : Addressable<T>, Identifiable<T> {

	override val identity = addressString

	override fun toString() = addressString

	override val address: Address<T>
		get() = address(addressString)

	companion object {

		fun <T> address(path: String) =
			Address<T>(path)

	}

}