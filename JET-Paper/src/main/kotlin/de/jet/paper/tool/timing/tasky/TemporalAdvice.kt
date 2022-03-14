package de.jet.paper.tool.timing.tasky

import de.jet.jvm.extension.time.inWholeMinecraftTicks
import de.jet.paper.extension.system
import org.bukkit.scheduler.BukkitRunnable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface TemporalAdvice {

	fun run(runnable: BukkitRunnable)

	companion object {

		/**
		 * The [BukkitRunnable] gets executed as fast as possible.
		 * No repeat and no start delay are provided, to get the
		 * quickest result as possible.
		 * @param async defines, if the runnable should be executed as an async
		 * or sync task. if true [BukkitRunnable.runTaskAsynchronously] or else
		 * [BukkitRunnable.runTask] is used to execute the runnable.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun instant(async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskAsynchronously(system)
				} else
					runnable.runTask(system)
			}
		}

		/**
		 * The [BukkitRunnable] gets executed, with the specified execution-delay.
		 * @param delay the delay, specified in minecraft ticks
		 * @param async defines, if the runnable should be executed as an async
		 * or sync task. if true [BukkitRunnable.runTaskLaterAsynchronously] or else
		 * [BukkitRunnable.runTaskLater] is used to execute the runnable.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun delayed(delay: Long, async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskLaterAsynchronously(system, delay)
				} else
					runnable.runTaskLater(system, delay)
			}
		}

		/**
		 * The [BukkitRunnable] gets executed, with the specified execution-delay.
		 * @param delay the delay, specified as a [Duration]
		 * @param async defines, if the runnable should be executed as an async
		 * or sync task. if true [BukkitRunnable.runTaskLaterAsynchronously] or else
		 * [BukkitRunnable.runTaskLater] is used to execute the runnable.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun delayed(delay: Duration, async: Boolean = false) =
			delayed(delay.inWholeMinecraftTicks, async)

		/**
		 * The [BukkitRunnable] gets executed, with the specified execution-delay
		 * and gets execution again, after the specified distance-delay.
		 * @param delay the delay, specified in minecraft-ticks
		 * @param distance the distance, to the next execution, specified in minecraft-ticks
		 * @param async defines, if the runnable should be executed as an async
		 * or sync task. if true [BukkitRunnable.runTaskTimerAsynchronously] or else
		 * [BukkitRunnable.runTaskTimer] is used to execute the runnable.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun ticking(delay: Long, distance: Long, async: Boolean = false) = object : TemporalAdvice {
			override fun run(runnable: BukkitRunnable) {
				if (async) {
					runnable.runTaskTimerAsynchronously(system, delay, distance)
				} else
					runnable.runTaskTimer(system, delay, distance)
			}
		}

		/**
		 * The [BukkitRunnable] gets executed, with the specified execution-delay
		 * and gets execution again, after the specified distance-delay.
		 * @param delay the delay, specified as [Duration]
		 * @param distance the distance, to the next execution, specified as [Duration]
		 * @param async defines, if the runnable should be executed as an async
		 * or sync task. if true [BukkitRunnable.runTaskTimerAsynchronously] or else
		 * [BukkitRunnable.runTaskTimer] is used to execute the runnable.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun ticking(delay: Duration, distance: Duration, async: Boolean = false) =
			ticking(delay.inWholeMinecraftTicks, distance.inWholeMinecraftTicks, async)

		/**
		 * The [BukkitRunnable] gets executed, with the specified execution-delay
		 * and gets execution again, after a distance-delay.
		 * @param tickDistance the delay, specified as [Duration]
		 * @param startWithDistance defines, if the first execution, also waits a [tickDistance]
		 * @param async defines, if the runnable should be executed as an async
		 * or sync task. if true [BukkitRunnable.runTaskTimerAsynchronously] or else
		 * [BukkitRunnable.runTaskTimer] is used to execute the runnable.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun ticking(tickDistance: Duration, startWithDistance: Boolean = false, async: Boolean = false) =
			ticking(if (startWithDistance) tickDistance else 0.seconds, tickDistance, async)

	}

}