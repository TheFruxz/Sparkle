package de.jet.minecraft.tool.timing.tasky

import de.jet.minecraft.extension.system
import org.bukkit.scheduler.BukkitRunnable

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

		fun ticking(delay: Long, distance: Long, async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskTimerAsynchronously(system, delay, distance)
				} else
					runnable.runTaskTimer(system, delay, distance)
			}
		}

	}

}