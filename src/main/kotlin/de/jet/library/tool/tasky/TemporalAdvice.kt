package de.jet.library.tool.tasky

import de.jet.library.JET
import org.bukkit.scheduler.BukkitRunnable

interface TemporalAdvice {

	fun run(runnable: BukkitRunnable)

	companion object {

		fun instant(async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskAsynchronously(JET.appInstance)
				} else
					runnable.runTask(JET.appInstance)
			}
		}

		fun delayed(delay: Long, async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskLaterAsynchronously(JET.appInstance, delay)
				} else
					runnable.runTaskLater(JET.appInstance, delay)
			}
		}

		fun ticking(delay: Long, distance: Long, async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskTimerAsynchronously(JET.appInstance, delay, distance)
				} else
					runnable.runTaskTimer(JET.appInstance, delay, distance)
			}
		}

	}

}