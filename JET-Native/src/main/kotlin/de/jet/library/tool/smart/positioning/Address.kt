package de.jet.library.tool.smart.positioning

import de.jet.library.tool.smart.identification.Identifiable

data class Address<T> internal constructor(val address: String) : Identifiable<Address<T>> {

    override val identity = address

    override fun toString() = address

    companion object {

        fun <T> address(path: String) =
            Address<T>(path)

    }

}