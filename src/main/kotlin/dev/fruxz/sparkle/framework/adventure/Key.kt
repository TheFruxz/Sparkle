package dev.fruxz.sparkle.framework.adventure

import dev.fruxz.stacked.extension.KeyingStrategy
import dev.fruxz.stacked.extension.subKey
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer
import org.bukkit.block.Block
import org.bukkit.entity.Entity

fun NamespacedKey.subKey(value: String, strategy: KeyingStrategy = KeyingStrategy.CONTINUE): NamespacedKey =
    subNamespacedKey(value, strategy)

infix operator fun NamespacedKey.div(value: String): NamespacedKey = subNamespacedKey(value)

fun Key.subNamespacedKey(value: String, strategy: KeyingStrategy = KeyingStrategy.CONTINUE): NamespacedKey =
    with(subKey(value, strategy)) { NamespacedKey(namespace(), value()) }

val Key.namespacedKey: NamespacedKey
    get() = when (this) {
        is NamespacedKey -> this
        else -> NamespacedKey(namespace(), value())
    }

val Entity.key: Key
    get() = Key.key("entity", "$uniqueId")

val OfflinePlayer.key: Key
    get() = Key.key("player","$uniqueId")

val Location.key: Key
    get() = Key.key("location", "${world.uid}.$x.$y.$z")

val Block.key: Key
    get() = Key.key("block", "${world.uid}.$x.$y.$z")