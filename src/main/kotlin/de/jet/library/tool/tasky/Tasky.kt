package de.jet.library.tool.tasky

import de.jet.library.extension.catchException
import de.jet.library.structure.app.App
import de.jet.library.tool.display.ide.API
import org.bukkit.scheduler.BukkitRunnable

interface Tasky {

	fun shutdown()

	val taskId: Int

	var attempt: Long

	val dieOnError: Boolean

	val vendor: App

	val temporalAdvice: TemporalAdvice

	companion object {

		fun task(
			vendor: App,
			temporalAdvice: TemporalAdvice,
			killAtError: Boolean = true,
			process: Tasky.() -> Unit,
		): Task {
			val currentTask = Task(temporalAdvice, true, process)

			object : BukkitRunnable() {

				val controller = object : Tasky {
					override fun shutdown() = cancel()
					override val taskId: Int
						get() = getTaskId()
					override var attempt = 0L
					override val dieOnError = killAtError
					override val vendor = vendor
					override val temporalAdvice = temporalAdvice
				}

				override fun run() {

					controller.attempt++

					if ((Long.MAX_VALUE * .95) < controller.attempt)
						System.err.println("WARNING! Task #$taskId is running out of attempts, please restart!")

					try {

						currentTask.process(controller)

					} catch (e: Exception) {
						catchException(e)

						if (controller.dieOnError)
							controller.shutdown()

					} catch (e: java.lang.Exception) {
						catchException(e)

						if (controller.dieOnError)
							controller.shutdown()

					}
				}

			}

			return currentTask
		}

	}

}

class Task internal constructor(
	@API val temporalAdvice: TemporalAdvice,
	@API val killAtError: Boolean = true,
	val process: Tasky.() -> Unit,
)