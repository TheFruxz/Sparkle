package de.fruxz.sparkle.framework.infrastructure.service

import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.framework.infrastructure.Hoster
import de.fruxz.sparkle.framework.attachment.Logging
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.getApp
import de.fruxz.sparkle.framework.scheduler.Tasky
import de.fruxz.sparkle.framework.scheduler.TemporalAdvice
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.subKey
import net.kyori.adventure.key.Key

interface Service : Hoster<Unit, Unit, Service>, de.fruxz.sparkle.framework.attachment.Logging {

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
		get() = SparkleCache.runningServiceTaskController[key]
		set(value) {
			debugLog("Setting controller for $key to $value")
			if (value != null)
				SparkleCache.runningServiceTaskController += key to value
			else
				SparkleCache.runningServiceTaskController -= key
		}

	val isRunning: Boolean
		get() = controller != null && SparkleCache.runningTasks.contains(controller!!.taskId)

	fun shutdown() =
		controller?.shutdown() ?: throw IllegalStateException("controller of '$key' is null!")

	override fun requestStart() =
		vendor.getApp().start(this)

	override fun requestStop() =
		shutdown()

}