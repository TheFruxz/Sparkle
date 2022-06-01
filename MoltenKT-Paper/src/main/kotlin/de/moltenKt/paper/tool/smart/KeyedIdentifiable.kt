package de.moltenKt.paper.tool.smart

import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.paper.structure.app.App
import net.kyori.adventure.key.Key

interface KeyedIdentifiable<T> : VendorsIdentifiable<T>, Key {

    val identityKey: Key

    override val thisIdentity: String
        get() = value()

    override val vendorIdentity: Identity<out App>
        get() = Identity(namespace())

    override val identity: String
        get() = asString()

    override fun namespace(): String = identityKey.namespace()

    override fun value(): String = identityKey.value()

    override fun asString(): String = identityKey.asString()

}