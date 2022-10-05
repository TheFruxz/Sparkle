package de.fruxz.sparkle.framework.extension.time

import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.ascend.tool.timing.cooldown.StaticCooldown
import de.fruxz.sparkle.framework.extension.key
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

// Player cooldown

fun Player.setCooldown(key: Any, destination: Calendar) =
	StaticCooldown.setCooldown(this.key to key, destination)

fun Player.setCooldown(key: Any, duration: Duration) =
	StaticCooldown.setCooldown(this.key to key, Calendar.now() + duration)

fun Player.getCooldown(key: Any) =
	StaticCooldown.getCooldown(this.key to key)?.let { destination ->
		RunningCooldown(this.key to key, destination)
	}

fun Player.hasCooldown(key: Any) =
	StaticCooldown.hasCooldown(this.key to key)

val Player.activeCooldowns: List<RunningCooldown>
	get() = StaticCooldown.cooldownStats
		.filter { it.key.let { key -> key is Pair<*, *> && key.first == this.key } && it.value.inFuture }
		.map { RunningCooldown(it.key, it.value) }

// Block cooldown

fun Block.setCooldown(key: Any, destination: Calendar) =
	StaticCooldown.setCooldown(this.key to key, destination)

fun Block.setCooldown(key: Any, duration: Duration) =
	StaticCooldown.setCooldown(this.key to key, Calendar.now() + duration)

fun Block.getCooldown(key: Any) =
	StaticCooldown.getCooldown(this.key to key)?.let { destination ->
		RunningCooldown(this.key to key, destination)
	}

fun Block.hasCooldown(key: Any) =
	StaticCooldown.hasCooldown(this.key to key)

val Block.activeCooldowns: List<RunningCooldown>
	get() = StaticCooldown.cooldownStats
		.filter { it.key.let { key -> key is Pair<*, *> && key.first == this.key } && it.value.inFuture }
		.map { RunningCooldown(it.key, it.value) }

// Location cooldown

fun Location.setCooldown(key: Any, destination: Calendar) =
	StaticCooldown.setCooldown(this.key to key, destination)

fun Location.setCooldown(key: Any, duration: Duration) =
	StaticCooldown.setCooldown(this.key to key, Calendar.now() + duration)

fun Location.getCooldown(key: Any) =
	StaticCooldown.getCooldown(this.key to key)?.let { destination ->
		RunningCooldown(this.key to key, destination)
	}

fun Location.hasCooldown(key: Any) =
	StaticCooldown.hasCooldown(this.key to key)

val Location.activeCooldowns: List<RunningCooldown>
	get() = StaticCooldown.cooldownStats
		.filter { it.key.let { key -> key is Pair<*, *> && key.first == this.key } && it.value.inFuture }
		.map { RunningCooldown(it.key, it.value) }

data class RunningCooldown constructor(val key: Any, val destination: Calendar) {
	val remaining = (destination.timeInMilliseconds - Calendar.now().timeInMilliseconds).milliseconds
}
