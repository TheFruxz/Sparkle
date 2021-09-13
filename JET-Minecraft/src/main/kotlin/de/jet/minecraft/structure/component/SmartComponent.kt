package de.jet.minecraft.structure.component

import de.jet.library.extension.jetTry
import de.jet.minecraft.app.JetApp
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.event.EventListener
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.command.live.InterchangeAccess
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

	final override fun start() {

		component() // register all objects

		interchanges.forEach {
			jetTry {
				vendor.add(it)
			}
		}

		services.forEach {
			jetTry {
				vendor.register(it)
				vendor.start(it)
			}
		}

		components.forEach {
			jetTry {
				vendor.add(it)
			}
		}

		listeners.forEach {
			jetTry {
				vendor.add(it)
			}
		}

	}

	final override fun stop() {

		interchanges.forEach {
			jetTry {
				vendor.replace(it.identityObject, disabledModuleInterchange)
			}
		}

		services.forEach {
			jetTry {
				vendor.stop(it)
				vendor.unregister(it)
			}
		}

		components.forEach {
			jetTry {
				vendor.stop(it.identityObject)
			}
		}

		listeners.forEach {
			jetTry {
				vendor.remove(it)
			}
		}

	}

	abstract fun component()

	companion object {

		internal val disabledModuleInterchange = object : Interchange(JetApp.instance, "") {
			override val execution: InterchangeAccess.() -> InterchangeResult = {

				"§cThe providing module is disabled!"
					.notification(FAIL, executor).display()

				SUCCESS
			}
		}

	}

}