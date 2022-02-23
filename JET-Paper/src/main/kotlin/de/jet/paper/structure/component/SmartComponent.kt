package de.jet.paper.structure.component

import de.jet.jvm.extension.tryToCatch
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.app.JetCache
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.objectBound.buildSandBox
import de.jet.paper.extension.objectBound.destroySandBox
import de.jet.paper.extension.system
import de.jet.paper.runtime.sandbox.SandBox
import de.jet.paper.structure.app.App
import de.jet.paper.structure.app.event.EventListener
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.execution
import de.jet.paper.structure.component.Component.RunType.DISABLED
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.display.message.Transmission.Level.FAIL

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
				vendor.replace(it.thisIdentityObject, disabledComponentInterchange(identityObject))
				JetCache.registeredInterchanges.remove(it)
				debugLog("Interchange '${it.identity}' registered through '$identity' with disabled-interchange!")
			}
		}

	}

	final override suspend fun start() {

		interchanges.forEach {
			tryToCatch {
				vendor.replace(it.thisIdentityObject, it)
				JetCache.registeredInterchanges.add(it)
				debugLog("Interchange '${it.identity}' replaced through '$identity' with original interchange-value!")
			}
		}

		services.forEach {
			tryToCatch {
				vendor.register(it)
				JetCache.registeredServices.add(it)
				debugLog("Service '${it.identity}' registered through '$identity'!")
				vendor.start(it)
				debugLog("Service '${it.identity}' started through '$identity'!")
			}
		}

		components.forEach {
			tryToCatch {
				vendor.add(it)
				JetCache.registeredComponents.add(it)
				debugLog("Component '${it.identity}' added through '$identity'!")
			}
		}

		listeners.forEach {
			tryToCatch {
				vendor.add(it)
				JetCache.registeredListeners.add(it)
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
				vendor.replace(it.thisIdentityObject, disabledComponentInterchange(identityObject))
				JetCache.registeredInterchanges.remove(it)
				debugLog("Interchange '${it.identity}' registered through '$identity' with disabled-interchange!")
			}
		}

		services.forEach {
			tryToCatch {
				vendor.stop(it)
				debugLog("Service '${it.identity}' stopped through '$identity'!")
				vendor.unregister(it)
				JetCache.registeredServices.remove(it)
				debugLog("Service '${it.identity}' unregistered through '$identity'!")
			}
		}

		components.forEach {
			tryToCatch {
				vendor.stop(it.identityObject)
				JetCache.registeredComponents.remove(it)
				debugLog("Component '${it.identity}' stopped through '$identity'!")
			}
		}

		listeners.forEach {
			tryToCatch {
				vendor.remove(it)
				JetCache.registeredListeners.remove(it)
				debugLog("Service '${it.identity}' removed through '$identity'")
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

		internal fun disabledComponentInterchange(identity: Identity<Component>) = object : Interchange("", ignoreInputValidation = true) {

			override val execution = execution {

				"§c§lSORRY!§7 The providing component '§e$identity§7' is currently §cdisabled§7!"
					.notification(FAIL, executor).display()

				SUCCESS
			}
		}.apply {
			replaceVendor(system)
		}

	}

}