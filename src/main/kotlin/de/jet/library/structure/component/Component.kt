package de.jet.library.structure.component

import de.jet.app.JetCache
import de.jet.app.JetData
import de.jet.library.extension.debugLog
import de.jet.library.extension.paper.createKey
import de.jet.library.structure.app.App
import de.jet.library.structure.component.Component.RunType.*
import de.jet.library.tool.smart.Identity
import de.jet.library.tool.smart.Logging
import de.jet.library.tool.smart.VendorsIdentifiable
import org.bukkit.NamespacedKey

abstract class Component(
	override val vendor: App,
	val behaviour: RunType = DISABLED,
) : VendorsIdentifiable<Component>, Logging {

	override val vendorIdentity: Identity<out App>
		get() = vendor.identityObject

	override val sectionLabel: String
		get() = "component/$identity"

	val isRunning: Boolean
		get() = JetCache.runningComponents.contains(identityObject)

	val key: NamespacedKey
		get() = vendor.createKey(thisIdentity)

	fun firstContactHandshake() {
		if (!JetData.touchedComponents.content.contains(identity)) {
			if (setOf(AUTOSTART_IMMUTABLE, AUTOSTART_MUTABLE).contains(behaviour)) {
				JetData.touchedComponents.content += identity
				debugLog("Remembering: I've stayed in touch with the '$identity'-component!")
			} else
				debugLog("AutoStart setup was not required, '$identity' does not request auto-start!")
		} else
			debugLog("Component '$identity' was already in touch with this system!")
	}

	val isAutoStarting: Boolean
		get() = when (behaviour) {
			ENABLED, AUTOSTART_IMMUTABLE -> {
				true
			}
			else -> {
				JetData.autoStartComponents.content.contains(identity)
			}
		}

	val canBeStopped: Boolean
		get() = behaviour != ENABLED

	val canBeAutoStartToggled: Boolean
		get() = setOf(ENABLED, AUTOSTART_IMMUTABLE).contains(behaviour)

	abstract fun start()

	abstract fun stop()

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