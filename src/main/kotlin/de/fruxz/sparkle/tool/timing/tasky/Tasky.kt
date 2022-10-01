package de.fruxz.sparkle.tool.timing.tasky

import de.fruxz.ascend.extension.catchException
import de.fruxz.ascend.extension.data.RandomTagType.ONLY_UPPERCASE
import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.app.SparkleApp.Infrastructure
import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.extension.debugLog
import de.fruxz.sparkle.structure.app.App
import de.fruxz.sparkle.tool.smart.Logging
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.util.logging.Level

interface Tasky : Logging {

	fun shutdown()

	val taskId: Int

	val internalId: String

	var attempt: Long

	val dieOnError: Boolean

	override val vendor: App

	val temporalAdvice: TemporalAdvice

	val startTime: Calendar

	override val sectionLabel: String
		get() = "Task: $internalId"

	companion object {

		@JvmStatic
		fun task(
			vendor: App,
			temporalAdvice: TemporalAdvice,
			killAtError: Boolean = true,
			onStart: Tasky.() -> Unit = {},
			onStop: Tasky.() -> Unit = {},
			onCrash: Tasky.() -> Unit = {},
			serviceVendor: Key = Key.key(Infrastructure.SYSTEM_IDENTITY, "dummy"),
			process: Tasky.() -> Unit,
			internalId: String = buildRandomTag(hashtag = false, tagType = ONLY_UPPERCASE)
		): Tasky {
			val currentTask = Task(temporalAdvice, killAtError, process)
			lateinit var output: Tasky

			temporalAdvice.run(
				object : BukkitRunnable() {

					val controller = object : Tasky {
						override fun shutdown() = cancel()
						override val taskId: Int
							get() = getTaskId()
						override var attempt = 0L
						override val dieOnError = killAtError
						override val vendor = vendor
						override val temporalAdvice = temporalAdvice
						override val startTime = Calendar.now()
						override val internalId = internalId
					}

					override fun run() {

						controller.attempt++

						if ((Long.MAX_VALUE * .95) < controller.attempt)
							controller.sectionLog.warning("WARNING! Task #$taskId is running out of attempts, please restart!")

						fun handleCrash(exception: Exception) {
							catchException(exception)

							if (controller.dieOnError) {
								onCrash(controller)
								SparkleCache.runningTasks = SparkleCache.runningTasks.filter { check -> check != controller.taskId }
								SparkleCache.runningServiceTaskController = SparkleCache.runningServiceTaskController.filterNot { check -> check.value.taskId == controller.taskId }.toMutableMap()
								controller.shutdown()
							}
						}

						try {

							if (controller.attempt == 1L) {
								SparkleCache.runningTasks += taskId
								onStart(controller)
								output = controller
							}

							currentTask.process(controller)

						} catch (e: Exception) { handleCrash(e) } catch (e: java.lang.Exception) { handleCrash(e) } // Crash handling
					}

				}.let {
					output = object : Tasky {
						override fun shutdown() {
							SparkleCache.runningTasks = SparkleCache.runningTasks.filter { check -> check != it.taskId }
							SparkleCache.runningServiceTaskController = SparkleCache.runningServiceTaskController.filterNot { check -> check.value.taskId == it.taskId }.toMutableMap()
							onStop(this)
							Bukkit.getScheduler().cancelTask(it.taskId)
							it.cancel()
						}

						override val taskId: Int
							get() = it.taskId

						override var attempt: Long
							get() = it.controller.attempt
							set(value) {
								it.controller.attempt = value
							}
						override val dieOnError = killAtError

						override val vendor = vendor

						override val temporalAdvice = temporalAdvice

						override val startTime = Calendar.now()

						override val internalId = internalId

					}
					return@let it
				}

			)

			if (serviceVendor.value() != "dummy") {
				SparkleCache.runningServiceTaskController += serviceVendor to output
				debugLog("Tasky '$serviceVendor' successfully started task '$internalId'")
			} else
				debugLog("Tasky failed to start task '$internalId' due to dummy check fail!", Level.WARNING)

			return output
		}

	}

}

class Task internal constructor(
	val temporalAdvice: TemporalAdvice,
	val killAtError: Boolean = true,
	val process: Tasky.() -> Unit,
)