package de.jet.paper.structure.component

import de.jet.jvm.extension.container.toggle
import de.jet.jvm.extension.tryToResult
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetCache
import de.jet.paper.app.JetData
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.paper.createKey
import de.jet.paper.extension.runIfAutoRegister
import de.jet.paper.structure.app.App
import de.jet.paper.structure.component.Component.RunType.*
import de.jet.paper.tool.smart.ContextualIdentifiable
import de.jet.paper.tool.smart.Logging
import de.jet.paper.tool.smart.VendorOnDemand
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import org.bukkit.NamespacedKey
import kotlin.reflect.KClass

abstract class Component(
	open val behaviour: RunType = DISABLED,
	open val experimental: Boolean = false,
	final override val preferredVendor: App? = null,
) : ContextualIdentifiable<Component>, VendorOnDemand, Logging {

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

	override val vendorIdentity: Identity<out App>
		get() = vendor.identityObject

	override val sectionLabel: String
		get() = "component/$identity"

	val isRunning: Boolean
		get() = JetCache.runningComponents.contains(identityObject)

	val key: NamespacedKey
		get() = vendor.createKey(thisIdentity)

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
		if (!JetData.touchedComponents.content.contains(identity)) {
			debugLog("result: never touched '$identity'!")
			if (setOf(AUTOSTART_IMMUTABLE, AUTOSTART_MUTABLE).contains(behaviour)) {

				if (behaviour == AUTOSTART_MUTABLE) {
					debugLog("Component-Behaviour of '$identity' is AUTOSTART_MUTABLE; Checking if not auto-start...")
					if (!JetData.autoStartComponents.content.contains(identity)) {
						debugLog("Adding Component '$identity' to auto-start set; was not known and want auto-start!")
						JetData.autoStartComponents.content = JetData.autoStartComponents.content.toMutableSet().apply {
							add(identity)
						}
					}
				}

				JetData.touchedComponents.content += identity
				debugLog("Remembering: I've stayed in touch with the '$identity'-component!")

			} else
				debugLog("AutoStart setup was not required, '$identity' does not request auto-start!")
		} else
			debugLog("Component '$identity' was already in touch with this system!")
	}

	var isAutoStarting: Boolean
		get() = when (behaviour) {
			ENABLED, AUTOSTART_IMMUTABLE -> {
				true
			}
			else -> {
				JetData.autoStartComponents.content.contains(identity)
			}
		}
		internal set(value) {
			if (canBeAutoStartToggled) {

				JetData.autoStartComponents.content.toMutableSet().toggle(identity, value)

			} else
				throw IllegalArgumentException("Component '$identity' cannot be toggled!")
		}

	val canBeStopped: Boolean
		get() = behaviour != ENABLED

	val canBeAutoStartToggled: Boolean
		get() = !setOf(ENABLED, AUTOSTART_IMMUTABLE).contains(behaviour)

	val runningSince: Calendar?
		get() = JetCache.runningComponents[identityObject]

	/**
	 * Can be overwritten, no origin code!
	 */
	open suspend fun register() { }

	abstract suspend fun start()

	abstract suspend fun stop()

	fun requestStart(): ComponentRequestAnswer {
		var hasChanged = false
		return tryToResult {
			if (!isRunning) {

				vendor.start(identityObject)
				hasChanged = true

			}
		}.let { return@let ComponentRequestAnswer(hasChanged, it.exceptionOrNull()) }
	}

	fun requestStop(): ComponentRequestAnswer {
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

		fun getInstance(componentClass: KClass<out Component>): Component {
			return JetCache.registeredComponents.first { it::class == componentClass }
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

	}

}