package de.jet.library.structure.service

import de.jet.app.JetCache
import de.jet.library.extension.app
import de.jet.library.structure.app.App
import de.jet.library.tool.smart.Identity
import de.jet.library.tool.smart.Logging
import de.jet.library.tool.smart.VendorsIdentifiable
import de.jet.library.tool.timing.tasky.Tasky
import de.jet.library.tool.timing.tasky.TemporalAdvice

interface Service : VendorsIdentifiable<Service>, Logging {

	val temporalAdvice: TemporalAdvice

	val process: Tasky.() -> Unit

	val onStart: Tasky.() -> Unit
		get() = {}

	val onStop: Tasky.() -> Unit
		get() = {}

	val onCrash: Tasky.() -> Unit
		get() = {}

	override val sectionLabel: String
		get() = thisIdentity

	override val vendorIdentity: Identity<out App>
		get() = vendor.identityObject

	var controller: Tasky?
		get() = JetCache.runningServiceTaskController[identityObject]
		set(value) {
			if (value != null)
				JetCache.runningServiceTaskController[identityObject] = value
			else
				JetCache.runningServiceTaskController.remove(identityObject)
		}

	val isRunning: Boolean
		get() = controller != null && JetCache.runningTasks.contains(controller!!.taskId)

	fun boot() {
		app(vendor).start(this)
	}

	fun shutdown() {
		val state = controller

		if (state != null) {

			state.shutdown()

		} else
			throw IllegalStateException("controller is null")

	}

}