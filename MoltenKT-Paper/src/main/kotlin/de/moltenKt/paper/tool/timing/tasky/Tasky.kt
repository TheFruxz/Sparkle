package de.moltenKt.paper.tool.timing.tasky

import de.moltenKt.jvm.extension.catchException
import de.moltenKt.jvm.extension.data.RandomTagType.ONLY_UPPERCASE
import de.moltenKt.jvm.extension.data.buildRandomTag
import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.jvm.tool.smart.identification.Identity
import de.moltenKt.jvm.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.paper.scheduler
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.smart.Logging
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

		@JvmStatic
		fun task(
			vendor: App,
			temporalAdvice: TemporalAdvice,
			killAtError: Boolean = true,
			onStart: Tasky.() -> Unit = {},
			onStop: Tasky.() -> Unit = {},
			onCrash: Tasky.() -> Unit = {},
			serviceVendor: Identity<Service> = Identifiable.custom<Service>("dummy").identityObject,
			process: Tasky.() -> Unit,
			internalId: String = buildRandomTag(hash = false, tagType = ONLY_UPPERCASE)
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

						try {

							if (controller.attempt == 1L) {
								MoltenCache.runningTasks.add(element = taskId)
								onStart(controller)
								output = controller
							}

							currentTask.process(controller)

						} catch (e: Exception) {
							catchException(e)

							if (controller.dieOnError) {
								onCrash(controller)
								MoltenCache.runningTasks.removeAll { check -> check == controller.taskId }
								MoltenCache.runningServiceTaskController = MoltenCache.runningServiceTaskController.filterNot { check -> check.value.taskId == controller.taskId }.toMutableMap()
								controller.shutdown()
							}

						} catch (e: java.lang.Exception) {
							catchException(e)

							if (controller.dieOnError) {
								onCrash(controller)
								MoltenCache.runningTasks.removeAll { check -> check == controller.taskId }
								MoltenCache.runningServiceTaskController = MoltenCache.runningServiceTaskController.filterNot { check -> check.value.taskId == controller.taskId }.toMutableMap()
								controller.shutdown()
							}

						}
					}

				}.let {
					output = object : Tasky {
						override fun shutdown() {
							MoltenCache.runningTasks.removeAll { check -> check == it.taskId }
							MoltenCache.runningServiceTaskController = MoltenCache.runningServiceTaskController.filterNot { check -> check.value.taskId == it.taskId }.toMutableMap()
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
				MoltenCache.runningServiceTaskController[serviceVendor] = output

			return output
		}

	}

}

class Task internal constructor(
	val temporalAdvice: TemporalAdvice,
	val killAtError: Boolean = true,
	val process: Tasky.() -> Unit,
)