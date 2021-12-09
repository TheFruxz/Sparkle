package de.jet.jvm.application.console.interchange

import de.jet.jvm.interchange.InterchangeStructured
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.positioning.Address

open class ConsoleInterchangeStructure<T : ConsoleStructureBranch>(
	override val name: String,
	override val path: String = "/",
	override val branches: List<T> = emptyList(),
) : Identifiable<T>, ConsoleStructureBranch(name, path, branches), InterchangeStructured<T> {

	override val identity: String
		get() = name

	override fun getNearestBranchWithParameters(original: Address<T>): Pair<T, String> {

		fun getContent(address: Address<T>): Pair<T, String> {
			val currentAddressState = address.addressString.split("/")
			var output: Pair<T, String>?

			output = getStructureBranches<T>().firstOrNull { branch ->
				branch.path.removePrefix("/") == currentAddressState.joinToString("/")
			}?.let {
				return@let it to original.addressString.removePrefix(currentAddressState.joinToString("/")).removePrefix("/").split("/").joinToString(" ")
			}

			if (output == null && currentAddressState.size > 1) {
				output = getContent(Address(currentAddressState.dropLast(1).joinToString("/")))
			} else if (address == original) {
				return (getStructureBranches<T>().also { it.forEach {
					println("${it.path}")
				} }.first() to "")
			}

			return output ?: (getStructureBranches<T>().first() to original.addressString.removePrefix("/").split("/").joinToString(" "))
		}

		return getContent(original.copy())
	}

}