package de.jet.library.tool.timing.tasky

import de.jet.app.JetCache
import de.jet.library.extension.catchException
import de.jet.library.extension.paper.scheduler
import de.jet.library.structure.app.App
import de.jet.library.structure.service.Service
import de.jet.library.tool.display.ide.API
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.smart.Identity
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

interface Tasky {

	fun shutdown()

	val taskId: Int

	var attempt: Long

	val dieOnError: Boolean

	val vendor: App

	val temporalAdvice: TemporalAdvice

	val startTime: Calendar

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
						override val startTime = Calendar.getInstance()
					}

					override fun run() {

						controller.attempt++

						if ((Long.MAX_VALUE * .95) < controller.attempt)
							System.err.println("WARNING! Task #$taskId is running out of attempts, please restart!")

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

						override val startTime = Calendar.getInstance()

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