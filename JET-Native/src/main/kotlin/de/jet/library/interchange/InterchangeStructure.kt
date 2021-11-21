package de.jet.library.interchange

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.positioning.Address

open class InterchangeStructure<T : InterchangeStructureBranch>(
    val name: String,
    override val path: String = "/",
    override val branches: List<T> = emptyList(),
) : Identifiable<T>, InterchangeStructureBranch(name, path, branches) {

    override val identity = name

    fun getNearestBranchWithParameters(original: Address<T>): Pair<T, String>? {

        fun getContent(address: Address<T>): Pair<T, String>? {
            val currentAddressState = address.addressString.split("/")
            var output: Pair<T, String>?

            output = getStructureBranches<T>().firstOrNull { branch ->
                branch.path.removePrefix("/") == currentAddressState.joinToString("/")
            }?.let {
                return@let it to original.addressString.removePrefix(currentAddressState.joinToString("/")).removePrefix("/").split("/").joinToString(" ")
            }

            if (output == null && currentAddressState.size > 1) {
                output = getContent(Address(currentAddressState.dropLast(1).joinToString("/")))
            }

            return output
        }

        return getContent(original.copy())
    }

}
