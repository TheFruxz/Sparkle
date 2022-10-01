package de.fruxz.sparkle.structure.service

import de.fruxz.sparkle.app.MoltenCache
import de.fruxz.sparkle.extension.debugLog
import de.fruxz.sparkle.extension.getApp
import de.fruxz.sparkle.structure.Hoster
import de.fruxz.sparkle.tool.smart.KeyedIdentifiable
import de.fruxz.sparkle.tool.smart.Logging
import de.fruxz.sparkle.tool.timing.tasky.Tasky
import de.fruxz.sparkle.tool.timing.tasky.TemporalAdvice
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.subKey
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
		get() = vendor.subKey(thisIdentity, CONTINUE)

	var controller: Tasky?
		get() = MoltenCache.runningServiceTaskController[key]
		set(value) {
			debugLog("Setting controller for $key to $value")
			if (value != null)
				MoltenCache.runningServiceTaskController += key to value
			else
				MoltenCache.runningServiceTaskController -= key
		}

	val isRunning: Boolean
		get() = controller != null && MoltenCache.runningTasks.contains(controller!!.taskId)

	fun shutdown() =
		controller?.shutdown() ?: throw IllegalStateException("controller of '$key' is null!")

	override fun requestStart() =
		vendor.getApp().start(this)

	override fun requestStop() =
		shutdown()

}