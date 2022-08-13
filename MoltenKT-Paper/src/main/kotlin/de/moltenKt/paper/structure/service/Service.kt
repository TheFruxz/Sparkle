package de.moltenKt.paper.structure.service

import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.getApp
import de.moltenKt.paper.structure.Hoster
import de.moltenKt.paper.tool.smart.KeyedIdentifiable
import de.moltenKt.paper.tool.smart.Logging
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
import net.kyori.adventure.key.Key

interface Service : Hoster<Unit, Unit, Service>, Logging {

	val temporalAdvice: TemporalAdvice

	val process: Tasky.() -> Unit

	val onStart: Tasky.() -> Unit
		get() = {}

	val onStop: Tasky.() -> Unit
		get() = {}

	val onCrash: Tasky.() -> Unit
		get() = {}

	override val thisIdentity: String
		get() = label.lowercase()

	override val sectionLabel: String
		get() = thisIdentity

	override val identityKey: Key
		get() = Key.key(vendor, thisIdentity)

	var controller: Tasky?
		get() = MoltenCache.runningServiceTaskController[identityKey]
		set(value) {
			if (value != null)
				MoltenCache.runningServiceTaskController += identityKey to value
			else
				MoltenCache.runningServiceTaskController -= identityKey
		}

	val isRunning: Boolean
		get() = controller != null && MoltenCache.runningTasks.contains(controller!!.taskId)

	fun shutdown() =
		controller?.shutdown() ?: throw IllegalStateException("controller is null")

	override fun requestStart() =
		vendor.getApp().start(this)

	override fun requestStop() =
		shutdown()

}