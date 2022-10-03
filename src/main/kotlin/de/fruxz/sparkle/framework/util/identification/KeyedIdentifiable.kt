package de.fruxz.sparkle.framework.util.identification

import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.framework.infrastructure.app.App
import net.kyori.adventure.key.Key
import org.bukkit.Keyed
import org.bukkit.NamespacedKey

interface KeyedIdentifiable<T> : VendorsIdentifiable<T>, Key, Keyed {

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

    override fun getKey() = NamespacedKey.fromString(asString())!!

    companion object {

        fun <T> custom(key: Key) = object : KeyedIdentifiable<T> {
            override val identityKey = key
        }

    }

}