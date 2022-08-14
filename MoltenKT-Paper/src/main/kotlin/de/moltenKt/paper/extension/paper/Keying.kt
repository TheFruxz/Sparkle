package de.moltenKt.paper.extension.paper

import de.moltenKt.unfold.extension.KeyingStrategy
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Player

fun NamespacedKey.subKey(value: String, strategy: KeyingStrategy = KeyingStrategy.CONTINUE): NamespacedKey = NamespacedKey(
	when (strategy) {
		KeyingStrategy.SQUASH -> asString().replace(":", "_")
		KeyingStrategy.ORIGIN -> namespace()
		KeyingStrategy.CONTINUE -> value()
	},
	value
)

fun Key.subNamespacedKey(value: String, strategy: KeyingStrategy = KeyingStrategy.CONTINUE): NamespacedKey = NamespacedKey(
	when (strategy) {
		KeyingStrategy.SQUASH -> asString().replace(":", "_")
		KeyingStrategy.ORIGIN -> namespace()
		KeyingStrategy.CONTINUE -> value()
	},
	value
)

val Player.key: Key
	get() = Key.key("$uniqueId")

val Location.key: Key
	get() = Key.key("location_${world.uid}.$x.$y.$z")

val Block.key: Key
	get() = Key.key("block_${world.uid}.$x.$y.$z")