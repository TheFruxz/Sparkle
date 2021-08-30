package de.jet.app.interchange

import de.jet.app.JetCache
import de.jet.app.JetData
import de.jet.library.extension.collection.replace
import de.jet.library.extension.display.message
import de.jet.library.extension.display.notification
import de.jet.library.extension.lang
import de.jet.library.extension.system
import de.jet.library.structure.app.App
import de.jet.library.structure.command.CompletionVariable
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeResult
import de.jet.library.structure.command.InterchangeResult.SUCCESS
import de.jet.library.structure.command.buildCompletion
import de.jet.library.structure.command.isRequired
import de.jet.library.structure.command.live.InterchangeAccess
import de.jet.library.structure.command.mustMatchOutput
import de.jet.library.structure.command.next
import de.jet.library.structure.command.plus
import de.jet.library.tool.display.message.Transmission.Level.FAIL
import de.jet.library.tool.display.message.Transmission.Level.INFO

class ComponentInterchange(vendor: App = system) : Interchange(vendor, "component", completion = buildCompletion {
	next("start") + "stop" + "list" + "autostart" isRequired true mustMatchOutput true
	next(CompletionVariable(vendor, "Component", true) {
		JetCache.registeredComponents.map { it.identity }
	}) isRequired false mustMatchOutput true
}) {
	override val execution: InterchangeAccess.() -> InterchangeResult = {

		if (parameters.size == 1 && parameters.first() == "list") {

			mutableListOf(lang("interchange.internal.component.list.header")).apply {
				JetCache.registeredComponents.forEach { component ->
					add(
						lang("interchange.internal.component.list.line")
							.replace(
								"[component]" to component.identity,
								"[autoStart]" to if (component.isAutoStarting || JetData.autoStartComponents.content.contains(component.identity)) "§a§lAUTO-START" else "§c§lAUTO-START",
								"[status]" to if (component.isRunning) "§a§lON" else "§c§lOFF"
							)
					)
				}
			}.forEach {
				it.message(executor).display()
			}

			SUCCESS

		} else if (parameters.size == 2) {

			try {

				val component = JetCache.registeredComponents.first { it.identity == parameters.last() }

				when (parameters.first().lowercase()) {

					"start" -> {

						if (!component.isRunning) {

							component.vendor.start(component.identityObject)

							lang("interchange.internal.component.nowRunning")
								.replace("[component]", component.identity)
								.notification(INFO, executor).display()

						} else
							lang("interchange.internal.component.alreadyRunning")
								.replace("[component]", component.identity)
								.notification(FAIL, executor).display()

						SUCCESS
					}

					"stop" -> {
						if (component.isRunning) {

							component.vendor.stop(component.identityObject)

							lang("interchange.internal.component.nowStopped")
								.replace("[component]", component.identity)
								.notification(INFO, executor).display()

						} else
							lang("interchange.internal.component.missingRunning")
								.replace("[component]", component.identity)
								.notification(FAIL, executor).display()

					}

					"autostart" -> {

						if (!component.canBeAutoStartToggled) {

							JetData.autoStartComponents.let { preference ->
								val currentState = preference.content.toMutableSet()

								lang(
									if (currentState.contains(component.identity)) {
										currentState.remove(component.identity)
										"interchange.internal.component.autoStartRemoved"
									} else {
										currentState.add(component.identity)
										"interchange.internal.component.autoStartAdded"
									}
								)
									.replace("[component]", component.identity)
									.notification(FAIL, executor).display()

								preference.content = currentState
							}

						} else
							lang("interchange.internal.component.autoStartStatic")
								.replace("[component]", component.identity)
								.notification(FAIL, executor).display()

					}

				}

				SUCCESS

			} catch (e: NoSuchElementException) {
				InterchangeResult.WRONG_USAGE
			}

		} else
			InterchangeResult.WRONG_USAGE

	}
}