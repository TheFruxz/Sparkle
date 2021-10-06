package de.jet.library.tool.smart.positioning

import de.jet.library.tool.smart.identification.Identifiable
import java.net.URL

data class Address internal constructor(val address: String) : Identifiable<Address> {

    override val identity = address

    companion object {

        fun address(path: String) =
            Address(path)

        fun address(url: URL) =
            Address("$url")

        fun address(identifiable: Identifiable<*>) =
            Address(identifiable.identity)

    }

}