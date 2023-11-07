package de.fruxz.sparkle.framework.infrastructure.component

import dev.fruxz.ascend.extension.objects.takeIfInstance
import dev.fruxz.ascend.extension.tryWithResult
import dev.fruxz.ascend.tool.time.calendar.Calendar
import de.fruxz.sparkle.framework.annotation.RequiresComponent.Companion.missingComponents
import de.fruxz.sparkle.framework.annotation.RequiresComponent.Companion.requirementsMet
import de.fruxz.sparkle.framework.attachment.Logging
import de.fruxz.sparkle.framework.attachment.VendorOnDemand
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.infrastructure.Attachable
import de.fruxz.sparkle.framework.infrastructure.Hoster
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.component.Component.ComponentRequestAnswer
import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.*
import de.fruxz.sparkle.framework.infrastructure.component.file.ComponentManager
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.server.SparkleCache
import dev.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import dev.fruxz.stacked.extension.subKey
import kotlin.reflect.KClass

abstract class Component(
	open val behaviour: RunType = DISABLED,
	open val isExperimental: Boolean = false,
	final override val preferredVendor: App? = null,
) : Attachable, KeyedIdentifiable<Component>, VendorOnDemand, Logging, Hoster<ComponentRequestAnswer, ComponentRequestAnswer, Component> {

	init {

		preferredVendor?.let {
			vendor = it
		}

	}

	override lateinit var vendor: App
		internal set

	val isVendorCurrentlySet: Boolean
		get() = this::vendor.isInitialized

	override val sectionLabel: String
		get() = "component/$identity"

	val isRunning: Boolean
		get() = SparkleCache.runningComponents.contains(identityObject)

	override val identityKey by lazy { vendor.subKey(label, CONTINUE) }

	/**
	 * This function replaces the current [vendor] of this [Component]
	 * with the [newVendor].
	 * This only happens, if the current [vendor] is not set (not initialized),
	 * or if [override] is true *(default: false)*.
	 * @param newVendor the new vendor, which will be used
	 * @param override defines, if the old vendor (if set) will be replaced with [newVendor]
	 * @return If the vendor-change happens, true is returned, otherwise false is returned!
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun replaceVendor(newVendor: App, override: Boolean) = if (override || !this::vendor.isInitialized) {
		vendor = newVendor
		true
	} else
		false

	fun firstContactHandshake() {
		debugLog("starting firstContactHandshake function of component '$identity'!")
		val component = ComponentManager.getComponent(identityObject)
		if (component == null) {
			debugLog("result: never touched '$identity'!")

			ComponentManager.registerComponent(this)

		} else
			debugLog("Component '$identity' was already in touch with this system!")
	}

	var isAutoStarting: Boolean
		get() = when (behaviour) {
			ENABLED, AUTOSTART_IMMUTABLE -> {
				true
			}
			else -> {
				ComponentManager.getComponent(identityObject)?.isAutoStart == true
			}
		}
		internal set(value) {
			if (canBeAutoStartToggled) {

				ComponentManager.editComponent(identityObject, isAutoStart = value)

			} else
				throw IllegalArgumentException("Component '$identity' cannot be toggled!")
		}

	val canBeStopped: Boolean
		get() = behaviour != ENABLED

	val isForced: Boolean
		get() = behaviour == ENABLED

	val canBeAutoStartToggled: Boolean
		get() = !setOf(ENABLED, AUTOSTART_IMMUTABLE).contains(behaviour)

	val runningSince: Calendar?
		get() = SparkleCache.runningComponents[identityObject]

	val isBlocked: Boolean
		get() = ComponentManager.getComponent(identityObject)?.isBlocked == true

	/**
	 * Can be overwritten, no origin code!
	 */
	open suspend fun register() { }

	abstract suspend fun start()

	abstract suspend fun stop()

	override fun requestStart(): ComponentRequestAnswer {
		var hasChanged = false
		return tryWithResult({ SparkleApp.debugMode }) {
			if (!isRunning) {
				if (requirementsMet()) {

					vendor.start(identityObject)
					hasChanged = true

				} else
					sectionLog.warning("Component $label cannot be started, becaused components ${missingComponents().map { it.simpleName }} are missing!")
			}
		}.let { return@let ComponentRequestAnswer(hasChanged, it.exceptionOrNull()) }
	}

	override fun requestStop(): ComponentRequestAnswer {
		var hasChanged = false
		return tryWithResult({ SparkleApp.debugMode }) {
			if (isRunning && canBeStopped) {

				vendor.stop(identityObject)
				hasChanged = true

			}
		}.let { return@let ComponentRequestAnswer(hasChanged, it.exceptionOrNull()) }
	}

	fun requestRestart(): ComponentRequestAnswer {
		var hasChanged = false
		var exception: Throwable?

		if (isRunning) {
			requestStop().let {
				hasChanged = it.hasStateChanged
				exception = it.exception
			}
		}

		requestStart().let {
			hasChanged = it.hasStateChanged || hasChanged
			exception = it.exception
		}

		return ComponentRequestAnswer(hasChanged, exception)
	}

	fun requestChangeAutoStart(autoStart: Boolean): ComponentRequestAnswer {
		var hasChanged = false

		return tryWithResult({ SparkleApp.debugMode }) {
			if (canBeAutoStartToggled) {
				isAutoStarting = autoStart
				hasChanged = true
			}
		}.let { return@let ComponentRequestAnswer(hasChanged, it.exceptionOrNull()) }

	}

	companion object {

		@JvmStatic
		inline fun <reified T : Component> getInstance(componentClass: KClass<T>): T? {
			return SparkleCache.registeredComponents.firstOrNull { it::class == componentClass }?.takeIfInstance<T>()
		}

		@JvmStatic
		inline fun <reified T : Component> isEnabled(componentClass: KClass<T>): Boolean {
			return getInstance(componentClass)?.identityObject?.let {SparkleCache.runningComponents.containsKey(it)} == true
		}

	}

	data class ComponentRequestAnswer(
		val hasStateChanged: Boolean,
		val exception: Throwable? = null,
	)

	enum class RunType {

		/**
		 * # `RunType.DISABLED`
		 * ## Info
		 * Disabled after registration, but can be started, stopped and configured
		 * to be part of the auto-start system.
		 *
		 * ## Use
		 * You should use this, if your component is not useful or required to run
		 * every time from the beginning, but can be in special/useful
		 * in multiple situations.
		 *
		 */
		DISABLED,

		/**
		 * # `RunType.AUTOSTART_IMMUTABLE`
		 * ## Info
		 * Starting after registration, Auto-Start option cannot be changed,
		 * for components, which should start at the registering-process, and
		 * have to do so every time.
		 *
		 * ## Use
		 * You should only use this, if you use this
		 * component EVERY time at the server-startup, but it is on your side.
		 *
		 */
		AUTOSTART_IMMUTABLE,

		/**
		 * # `RunType.AUTOSTART_MUTABLE`
		 * ## Info
		 * Starting after registration, Auto-Start option can be changed.
		 *
		 * ## Use
		 * You should use this, if this is a 'normal' component, which did
		 * not have to run the whole time, like a required system-component
		 * for example!
		 *
		 */
		AUTOSTART_MUTABLE,

		/**
		 * # `RunType.ENABLED`
		 * ## Info
		 * Enabled after registration, but cannot be stopped or removed from
		 * the auto-start components.
		 *
		 * ## Use
		 * Useful for important, necessary, or/and required system-components!
		 *
		 */
		ENABLED;

		val defaultIsAutoStart: Boolean
			get() = this != DISABLED

	}

}