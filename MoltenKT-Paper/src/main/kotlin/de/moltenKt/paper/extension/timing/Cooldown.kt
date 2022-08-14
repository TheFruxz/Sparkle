package de.moltenKt.paper.extension.timing

import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.core.tool.timing.cooldown.StaticCooldown
import de.moltenKt.paper.extension.paper.key
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import kotlin.time.Duration

// Player cooldown

fun Player.setCooldown(key: Any, destination: Calendar) =
	StaticCooldown.setCooldown(this.key to key, destination)

fun Player.setCooldown(key: Any, duration: Duration) =
	StaticCooldown.setCooldown(this.key to key, Calendar.now() + duration)

fun Player.getCooldown(key: Any) =
	StaticCooldown.getCooldown(this.key to key)

fun Player.hasCooldown(key: Any) =
	StaticCooldown.hasCooldown(this.key to key)

// Block cooldown

fun Block.setCooldown(key: Any, destination: Calendar) =
	StaticCooldown.setCooldown(this.key to key, destination)

fun Block.setCooldown(key: Any, duration: Duration) =
	StaticCooldown.setCooldown(this.key to key, Calendar.now() + duration)

fun Block.getCooldown(key: Any) =
	StaticCooldown.getCooldown(this.key to key)

fun Block.hasCooldown(key: Any) =
	StaticCooldown.hasCooldown(this.key to key)

// Location cooldown

fun Location.setCooldown(key: Any, destination: Calendar) =
	StaticCooldown.setCooldown(this.key to key, destination)

fun Location.setCooldown(key: Any, duration: Duration) =
	StaticCooldown.setCooldown(this.key to key, Calendar.now() + duration)

fun Location.getCooldown(key: Any) =
	StaticCooldown.getCooldown(this.key to key)

fun Location.hasCooldown(key: Any) =
	StaticCooldown.hasCooldown(this.key to key)


