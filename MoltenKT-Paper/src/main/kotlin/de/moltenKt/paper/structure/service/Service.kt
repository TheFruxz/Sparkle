package de.moltenKt.paper.structure.service

import de.moltenKt.jvm.tool.smart.identification.Identity
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.app
import de.moltenKt.paper.extension.paper.createKey
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.tool.smart.Logging
import de.moltenKt.paper.tool.smart.VendorsIdentifiable
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
import org.bukkit.NamespacedKey

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
		get() = MoltenCache.runningServiceTaskController[identityObject]
		set(value) {
			if (value != null)
				MoltenCache.runningServiceTaskController[identityObject] = value
			else
				MoltenCache.runningServiceTaskController.remove(identityObject)
		}

	val isRunning: Boolean
		get() = controller != null && MoltenCache.runningTasks.contains(controller!!.taskId)

	val key: NamespacedKey
		get() = app(vendor).createKey(thisIdentity)

	fun shutdown() {
		val state = controller

		if (state != null) {

			state.shutdown()

		} else
			throw IllegalStateException("controller is null")

	}

}