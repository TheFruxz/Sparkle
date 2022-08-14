package de.moltenKt.paper.extension.paper

import de.moltenKt.unfold.extension.KeyingStrategy
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey

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