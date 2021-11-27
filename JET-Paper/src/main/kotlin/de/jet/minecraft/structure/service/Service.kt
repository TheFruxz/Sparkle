package de.jet.minecraft.structure.service

import de.jet.jvm.tool.smart.identification.Identity
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.app
import de.jet.minecraft.extension.paper.createKey
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.tool.smart.Logging
import de.jet.minecraft.tool.smart.VendorsIdentifiable
import de.jet.minecraft.tool.timing.tasky.Tasky
import de.jet.minecraft.tool.timing.tasky.TemporalAdvice
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
		get() = JetCache.runningServiceTaskController[identityObject]
		set(value) {
			if (value != null)
				JetCache.runningServiceTaskController[identityObject] = value
			else
				JetCache.runningServiceTaskController.remove(identityObject)
		}

	val isRunning: Boolean
		get() = controller != null && JetCache.runningTasks.contains(controller!!.taskId)

	val key: NamespacedKey
		get() = app(vendor).createKey(thisIdentity)

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