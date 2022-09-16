package de.moltenKt.paper.structure.component

import de.moltenKt.core.extension.tryToResult
import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.runIfAutoRegister
import de.moltenKt.paper.structure.Hoster
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.component.Component.RunType.*
import de.moltenKt.paper.structure.component.file.ComponentManager
import de.moltenKt.paper.tool.smart.ContextualInstance
import de.moltenKt.paper.tool.smart.Logging
import de.moltenKt.paper.tool.smart.VendorOnDemand
import de.moltenKt.unfold.extension.KeyingStrategy.CONTINUE
import de.moltenKt.unfold.extension.subKey
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlin.reflect.KClass

abstract class Component(
	open val behaviour: RunType = DISABLED,
	open val isExperimental: Boolean = false,
	final override val preferredVendor: App? = null,
) : ContextualInstance<Component>, VendorOnDemand, Logging, Hoster<Component.ComponentRequestAnswer, Component.ComponentRequestAnswer, Component> {

	init {

		preferredVendor?.let {
			vendor = it
		}

		runIfAutoRegister()

	}

	override lateinit var vendor: App
		internal set

	val isVendorCurrentlySet: Boolean
		get() = this::vendor.isInitialized

	override val thisIdentity: String
		get() = label.lowercase()

	override val vendorIdentity: Identity<out App>
		get() = vendor.identityObject

	override val sectionLabel: String
		get() = "component/$identity"

	val isRunning: Boolean
		get() = MoltenCache.runningComponents.contains(identityObject)

	override val identityKey by lazy { vendor.subKey(thisIdentity.lowercase(), CONTINUE) }

	override val threadContext by lazy { @OptIn(DelicateCoroutinesApi::class) newSingleThreadContext(identity) }

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
		get() = MoltenCache.runningComponents[identityObject]

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
		return tryToResult {
			if (!isRunning) {

				vendor.start(identityObject)
				hasChanged = true

			}
		}.let { return@let ComponentRequestAnswer(hasChanged, it.exceptionOrNull()) }
	}

	override fun requestStop(): ComponentRequestAnswer {
		var hasChanged = false
		return tryToResult {
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

		return tryToResult {
			if (canBeAutoStartToggled) {
				isAutoStarting = autoStart
				hasChanged = true
			}
		}.let { return@let ComponentRequestAnswer(hasChanged, it.exceptionOrNull()) }

	}

	fun requestReset() {

	}

	companion object {

		@JvmStatic
		fun getInstance(componentClass: KClass<out Component>): Component {
			return MoltenCache.registeredComponents.first { it::class == componentClass }
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