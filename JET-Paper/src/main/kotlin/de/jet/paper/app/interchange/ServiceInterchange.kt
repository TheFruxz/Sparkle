package de.jet.paper.app.interchange

import de.jet.jvm.extension.collection.replace
import de.jet.jvm.extension.time.millisecond
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetCache
import de.jet.paper.extension.app
import de.jet.paper.extension.display.BOLD
import de.jet.paper.extension.display.GREEN
import de.jet.paper.extension.display.RED
import de.jet.paper.extension.display.message
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.lang
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.CompletionVariable
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.buildCompletion
import de.jet.paper.structure.command.isRequired
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.structure.command.mustMatchOutput
import de.jet.paper.structure.command.next
import de.jet.paper.structure.command.plus
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.display.message.Transmission.Level.FAIL
import de.jet.paper.tool.display.message.Transmission.Level.INFO
import kotlin.time.Duration.Companion.milliseconds

class ServiceInterchange(vendor: App = system) : Interchange(vendor, "service", requiresAuthorization = true, completion = buildCompletion {
	next(setOf("start", "stop", "restart", "list", "unregister", "reset")) isRequired true mustMatchOutput true
	next(CompletionVariable(vendor, "Service", true) {
		JetCache.registeredServices.map { it.identity }
	}) + "*" + CompletionVariable(vendor, "Service-Group", true) {
		JetCache.registeredApplications.map { "${it.identity}:*" }
	} mustMatchOutput true isRequired false
}) {

	override val execution: InterchangeAccess.() -> InterchangeResult = interchange@{

		if (parameters.size == 1 && parameters.first().equals("list", true)) {

			lang("interchange.internal.service.list.header")
				.message(executor).display()

			JetCache.registeredServices.forEach { service: Service ->

				lang("interchange.internal.service.list.line")
					.replace("[service]", service.identity)
					.replace("[enabled]" to if (service.isRunning) "$GREEN${BOLD}ONLINE" else "$RED${BOLD}OFFLINE")
					.replace("[activeSince]" to (Calendar.now().timeInMilliseconds - (service.controller?.startTime ?: Calendar.now()).timeInMilliseconds).milliseconds.toString())
					.message(executor).display()

			}

		} else if (parameters.size == 2) {
			val services = if (parameters.last() == "*") {
				JetCache.registeredServices
			} else if (parameters.last().endsWith(":*")) {
				val searchApp = Identity<App>(parameters.last().removeSuffix(":*"))
				JetCache.registeredServices.filter { it.vendorIdentity == searchApp }
			} else
				JetCache.registeredServices.firstOrNull { it.identity == parameters.last() }?.let { listOf(it) } ?: emptyList()

			if (services.isNotEmpty()) {

				when (parameters.first().lowercase()) {

					"start" -> {
						services.forEach { currentService ->
							try {

								app(currentService.vendor).start(currentService)

								lang("interchange.internal.service.serviceStarted")
									.replace("[id]" to currentService.identity)
									.notification(INFO, executor).display()

							} catch (exception: IllegalStateException) {
								lang("interchange.internal.service.serviceAlreadyOnline")
									.replace("[id]" to currentService.identity)
									.notification(FAIL, executor).display()
							}
						}
					}

					"stop" -> {
						services.forEach { currentService ->
							try {

								app(currentService.vendor).stop(currentService)

								lang("interchange.internal.service.serviceStopped")
									.replace("[id]" to currentService.identity)
									.notification(INFO, executor).display()

							} catch (exception: IllegalStateException) {
								lang("interchange.internal.service.serviceAlreadyOffline")
									.replace("[id]" to currentService.identity)
									.notification(FAIL, executor).display()
							}
						}
					}

					"restart" -> {
						services.forEach { currentService ->
							app(currentService.vendor).restart(currentService)

							lang("interchange.internal.service.serviceRestarted")
								.replace("[id]" to currentService.identity)
								.notification(INFO, executor).display()
						}
					}

					"unregister" -> {
						services.forEach { currentService ->
							try {

								app(currentService.vendor).stop(currentService)

								lang("interchange.internal.service.serviceUnregistered")
									.replace("[id]" to currentService.identity)
									.notification(INFO, executor).display()

							} catch (exception: IllegalStateException) {
								lang("interchange.internal.service.serviceNotFound")
									.replace("[id]" to currentService.identity)
									.notification(FAIL, executor).display()
							}
						}
					}

					"reset" -> {
						services.forEach { currentService ->
							try {

								app(currentService.vendor).reset(currentService)

								lang("interchange.internal.service.serviceReset")
									.replace("[id]" to currentService.identity)
									.notification(INFO, executor).display()

							} catch (exception: IllegalStateException) {
								lang("interchange.internal.service.serviceNotFound")
									.replace("[id]" to currentService.identity)
									.notification(FAIL, executor).display()
							}
						}
					}

					else -> return@interchange WRONG_USAGE

				}

			} else
				lang("interchange.internal.service.serviceNotFound")
					.replace("[id]" to parameters.last())
					.notification(FAIL, executor).display()

		} else
			return@interchange WRONG_USAGE

		return@interchange SUCCESS
	}

}