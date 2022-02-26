package de.jet.paper.tool.timing.tasky

import de.jet.jvm.extension.time.inWholeMinecraftTicks
import de.jet.paper.extension.system
import org.bukkit.scheduler.BukkitRunnable
import kotlin.time.Duration

interface TemporalAdvice {

	fun run(runnable: BukkitRunnable)

	companion object {

		fun instant(async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskAsynchronously(system)
				} else
					runnable.runTask(system)
			}
		}

		fun delayed(delay: Long, async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskLaterAsynchronously(system, delay)
				} else
					runnable.runTaskLater(system, delay)
			}
		}

		fun delayed(delay: Duration, async: Boolean = false) =
			delayed(delay.inWholeMinecraftTicks, async)

		fun ticking(delay: Long, distance: Long, async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskTimerAsynchronously(system, delay, distance)
				} else
					runnable.runTaskTimer(system, delay, distance)
			}
		}

		fun ticking(delay: Duration, distance: Duration, async: Boolean = false) =
			ticking(delay.inWholeMinecraftTicks, distance.inWholeMinecraftTicks, async)

	}

}