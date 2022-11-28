package de.fruxz.sparkle.framework.identification

import de.fruxz.ascend.tool.smart.identification.Identifiable
import net.kyori.adventure.key.Key
import org.bukkit.Keyed
import org.bukkit.NamespacedKey

interface KeyedIdentifiable<T> : Identifiable<T>, Key, Keyed {

    val identityKey: Key

    override val identity: String
        get() = asString()

    override fun namespace(): String = identityKey.namespace()

    override fun value(): String = identityKey.value()

    override fun asString(): String = identityKey.asString()

    override fun key() = key

    override fun getKey() = NamespacedKey.fromString(asString())!!

    companion object {

        fun <T> custom(key: Key) = object : KeyedIdentifiable<T> {
            override val identityKey = key
        }

    }

}