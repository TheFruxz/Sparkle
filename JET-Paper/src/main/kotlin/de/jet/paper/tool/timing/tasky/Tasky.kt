package de.jet.paper.tool.timing.tasky

import de.jet.jvm.extension.catchException
import de.jet.jvm.extension.data.RandomTagType.MIXED_CASE
import de.jet.jvm.extension.data.buildRandomTag
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetCache
import de.jet.paper.extension.paper.scheduler
import de.jet.paper.structure.app.App
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.display.ide.API
import de.jet.paper.tool.smart.Logging
import org.bukkit.scheduler.BukkitRunnable

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

		fun task(
			vendor: App,
			temporalAdvice: TemporalAdvice,
			killAtError: Boolean = true,
			onStart: Tasky.() -> Unit = {},
			onStop: Tasky.() -> Unit = {},
			onCrash: Tasky.() -> Unit = {},
			serviceVendor: Identity<Service> = Identifiable.custom<Service>("dummy").identityObject,
			process: Tasky.() -> Unit,
			internalId: String = buildRandomTag(hashtag = false, tagType = MIXED_CASE)
		): Tasky {
			val currentTask = Task(temporalAdvice, true, process)
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

						try {

							if (controller.attempt == 1L) {
								JetCache.runningTasks.add(element = taskId)
								onStart(controller)
								output = controller
							}

							currentTask.process(controller)

						} catch (e: Exception) {
							catchException(e)

							if (controller.dieOnError) {
								onCrash(controller)
								JetCache.runningTasks.removeAll { check -> check == controller.taskId }
								JetCache.runningServiceTaskController = JetCache.runningServiceTaskController.filterNot { check -> check.value.taskId == controller.taskId }.toMutableMap()
								controller.shutdown()
							}

						} catch (e: java.lang.Exception) {
							catchException(e)

							if (controller.dieOnError) {
								onCrash(controller)
								JetCache.runningTasks.removeAll { check -> check == controller.taskId }
								JetCache.runningServiceTaskController = JetCache.runningServiceTaskController.filterNot { check -> check.value.taskId == controller.taskId }.toMutableMap()
								controller.shutdown()
							}

						}
					}

				}.let {
					output = object : Tasky {
						override fun shutdown() {
							JetCache.runningTasks.removeAll { check -> check == it.taskId }
							JetCache.runningServiceTaskController = JetCache.runningServiceTaskController.filterNot { check -> check.value.taskId == it.taskId }.toMutableMap()
							onStop(this)
							scheduler.cancelTask(it.taskId)
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

			if (serviceVendor.identity != "dummy")
				JetCache.runningServiceTaskController[serviceVendor] = output

			return output
		}

	}

}

class Task internal constructor(
	@API val temporalAdvice: TemporalAdvice,
	@API val killAtError: Boolean = true,
	val process: Tasky.() -> Unit,
)