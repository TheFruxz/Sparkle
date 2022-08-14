package de.moltenKt.core.tool.timing.cooldown

import de.moltenKt.core.tool.timing.calendar.Calendar
import kotlin.time.Duration

object StaticCooldown {

	var cooldownStats: Map<Any, Calendar> = emptyMap()

	fun getCooldown(key: Any): Calendar? {
		return cooldownStats[key]
	}

	fun setCooldown(key: Any, cooldown: Calendar) {
		cooldownStats += key to cooldown
	}

	fun removeCooldown(key: Any) {
		cooldownStats -= key
	}

	fun hasCooldown(key: Any): Boolean =
		cooldownStats[key]?.isExpired == false

	fun isExpired(key: Any): Boolean =
		cooldownStats[key]?.isExpired ?: true

	fun extend(key: Any, time: Duration) {
		cooldownStats += key to ((cooldownStats[key] ?: Calendar.now()) + time)
	}

	fun decrease(key: Any, time: Duration) {
		cooldownStats += key to ((cooldownStats[key] ?: Calendar.now()) - time)
	}

	fun clean() {
		cooldownStats = cooldownStats.filterNot { it.value.isExpired }
	}

}