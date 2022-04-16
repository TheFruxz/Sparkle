package de.moltenKt.paper.structure.component

import de.moltenKt.core.extension.tryOrNull
import de.moltenKt.core.extension.tryToCatch
import de.moltenKt.core.extension.tryToIgnore
import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.objectBound.buildSandBox
import de.moltenKt.paper.extension.objectBound.destroySandBox
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.runtime.sandbox.SandBox
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.app.event.EventListener
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.execution
import de.moltenKt.paper.structure.component.Component.RunType.DISABLED
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.display.message.Transmission.Level.FAIL
import de.moltenKt.paper.tool.permission.Approval

abstract class SmartComponent(
	override val behaviour: RunType = DISABLED,
	override val experimental: Boolean = false,
	preferredVendor: App? = null,
) : Component(behaviour, experimental, preferredVendor) {

	val interchanges = mutableSetOf<Interchange>()

	fun interchange(vararg interchange: Interchange) = interchanges.addAll(interchange)

	val services = mutableSetOf<Service>()

	fun service(vararg service: Service) = services.addAll(service)

	val components = mutableSetOf<Component>()

	fun component(vararg component: Component) = components.addAll(component)

	val listeners = mutableSetOf<EventListener>()

	fun listener(vararg listener: EventListener) = listeners.addAll(listener)

	val sandboxes = mutableSetOf<SandBox>()

	fun sandbox(vararg sandbox: SandBox) = sandboxes.addAll(sandbox)

	final override suspend fun register() {

		component() // register all objects

		interchanges.forEach {
			tryToCatch {
				it.replaceVendor(vendor)
				vendor.replace(it.thisIdentityObject, disabledComponentInterchange(identityObject, tryOrNull { it.requiredApproval }))
				MoltenCache.registeredInterchanges.add(it)
				debugLog("Interchange '${it.identity}' registered through '$identity' with disabled-interchange!")
			}
		}

	}

	final override suspend fun start() {

		interchanges.forEach {
			tryToCatch {
				vendor.replace(it.thisIdentityObject, it)
				MoltenCache.registeredInterchanges.add(it)
				debugLog("Interchange '${it.identity}' replaced through '$identity' with original interchange-value!")
			}
		}

		services.forEach {
			tryToCatch {
				vendor.register(it)
				MoltenCache.registeredServices.add(it)
				debugLog("Service '${it.identity}' registered through '$identity'!")
				vendor.start(it)
				debugLog("Service '${it.identity}' started through '$identity'!")
			}
		}

		components.forEach {
			tryToCatch {
				vendor.add(it)
				MoltenCache.registeredComponents.add(it)
				debugLog("Component '${it.identity}' added through '$identity'!")
			}
		}

		listeners.forEach {
			tryToCatch {
				vendor.add(it)
				MoltenCache.registeredListeners.add(it)
				debugLog("Listener '${it.identity}' added through '$identity'!")
			}
		}

		sandboxes.forEach {
			tryToCatch {
				buildSandBox(this@SmartComponent.vendor, it.identity, it.process)
			}
		}

	}

	final override suspend fun stop() {

		interchanges.forEach {
			tryToCatch {
				vendor.replace(it.thisIdentityObject, disabledComponentInterchange(identityObject, tryOrNull { it.requiredApproval }))
				MoltenCache.registeredInterchanges.remove(it)
				tryToIgnore { debugLog("Interchange '${it.identity}' registered through '$identity' with disabled-interchange!") }
			}
		}

		services.forEach {
			tryToCatch {

				if (it.isRunning) {
					vendor.stop(it)
					tryToIgnore { debugLog("Service '${it.identity}' stopped through '$identity'!") }
				}

				vendor.unregister(it)

				tryToIgnore { debugLog("Service '${it.identity}' unregistered through '$identity'!") }
			}
		}

		components.forEach {
			tryToCatch {
				vendor.stop(it.identityObject)
				MoltenCache.registeredComponents.remove(it)
				tryToIgnore { debugLog("Component '${it.identity}' stopped through '$identity'!") }
			}
		}

		listeners.forEach {
				tryToCatch {
					vendor.remove(it)
					tryToIgnore { debugLog("Service '${it.identity}' removed through '$identity'") }
				}
		}

		sandboxes.forEach {
			tryToCatch {
				destroySandBox(it.identity)
			}
		}

	}

	abstract suspend fun component()

	companion object {

		internal fun disabledComponentInterchange(identity: Identity<out Component>, requiredApproval: Approval? = null) = object : Interchange("", ignoreInputValidation = true, forcedApproval = requiredApproval) {

			override val execution = execution {

				"<red><bold>SORRY!</bold><gray> The providing component '<yellow>$identity<gray>' is currently <red>disabled<gray>!"
					.notification(FAIL, executor).display()

				SUCCESS
			}
		}.apply {
			replaceVendor(system)
		}

	}

}