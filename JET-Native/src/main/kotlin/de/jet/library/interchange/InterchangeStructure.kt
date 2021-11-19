package de.jet.library.interchange

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.positioning.Address
import de.jet.library.tool.smart.positioning.Addressed

open class InterchangeStructure<T : InterchangeStructureBranch>(
    val name: String,
    override val branches: List<T> = emptyList(),
) : Identifiable<T>, InterchangeStructureBranch(name) {

    override val identity = name

    fun getNearestBranchWithParameters(original: Address<T>): Pair<Addressed<T>, String>? {

        fun getContent(address: Address<T>): Pair<Addressed<T>, String>? {
            var currentAddressState = address.addressString.split("/").also { println("cc: $it") }

            getBranchList<T>().forEach { branch ->
                if (branch.address.addressString == currentAddressState.joinToString("/")) {
                    println(1)
                    return branch to original.addressString.removePrefix(currentAddressState.joinToString("/"))
                } else {

                    if (currentAddressState.size.also { println("size: $it") } > 1) {
                        currentAddressState = currentAddressState.dropLast(1)
                        getContent(Address(currentAddressState.joinToString("/")))
                    } else {
                        println(2)
                    }

                }
            }

            println(3)
            return null
        }

        println(4)
        return getContent(original.copy().also { println(it.addressString) })

    }

}
