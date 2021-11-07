package de.jet.minecraft.structure.component

import de.jet.library.extension.jetTry
import de.jet.library.tool.smart.identification.Identity
import de.jet.minecraft.app.JetApp
import de.jet.minecraft.extension.debugLog
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.event.EventListener
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.component.Component.RunType.DISABLED
import de.jet.minecraft.structure.service.Service
import de.jet.minecraft.tool.display.message.Transmission.Level.FAIL

abstract class SmartComponent(
	override val vendor: App,
	override val behaviour: RunType = DISABLED,
) : Component(vendor, behaviour) {

	val interchanges = mutableSetOf<Interchange>()

	fun interchange(vararg interchange: Interchange) = interchanges.addAll(interchange)

	val services = mutableSetOf<Service>()

	fun service(vararg service: Service) = services.addAll(service)

	val components = mutableSetOf<Component>()

	fun component(vararg component: Component) = components.addAll(component)

	val listeners = mutableSetOf<EventListener>()

	fun listener(vararg listener: EventListener) = listeners.addAll(listener)

	final override fun register() {

		component() // register all objects

		interchanges.forEach {
			jetTry {
				vendor.replace(it.identityObject, disabledComponentInterchange(identityObject))
				debugLog("Interchange '${it.identity}' registered through '$identity' with disabled-interchange!")
			}
		}

	}

	final override fun start() {

		interchanges.forEach {
			jetTry {
				vendor.replace(it.identityObject, it)
				debugLog("Interchange '${it.identity}' replaced through '$identity' with original interchange-value!")
			}
		}

		services.forEach {
			jetTry {
				vendor.register(it)
				debugLog("Service '${it.identity}' registered through '$identity'!")
				vendor.start(it)
				debugLog("Service '${it.identity}' started through '$identity'!")
			}
		}

		components.forEach {
			jetTry {
				vendor.add(it)
				debugLog("Component '${it.identity}' added through '$identity'!")
			}
		}

		listeners.forEach {
			jetTry {
				vendor.add(it)
				debugLog("Listener '${it.identity}' added through '$identity'!")
			}
		}

	}

	final override fun stop() {

		interchanges.forEach {
			jetTry {
				vendor.replace(it.identityObject, disabledComponentInterchange(identityObject))
				debugLog("Interchange '${it.identity}' registered through '$identity' with disabled-interchange!")
			}
		}

		services.forEach {
			jetTry {
				vendor.stop(it)
				debugLog("Service '${it.identity}' stopped through '$identity'!")
				vendor.unregister(it)
				debugLog("Service '${it.identity}' unregistered through '$identity'!")
			}
		}

		components.forEach {
			jetTry {
				vendor.stop(it.identityObject)
				debugLog("Component '${it.identity}' stopped through '$identity'!")
			}
		}

		listeners.forEach {
			jetTry {
				vendor.remove(it)
				debugLog("Service '${it.identity}' removed through '$identity'")
			}
		}

	}

	abstract fun component()

	companion object {

		internal fun disabledComponentInterchange(identity: Identity<Component>) = object : Interchange(JetApp.instance, "") {
			override val execution = execution {

				"§c§lSORRY!§7 The providing component '§e$identity§7' is currently §cdisabled§7!"
					.notification(FAIL, executor).display()

				SUCCESS
			}
		}

	}

}