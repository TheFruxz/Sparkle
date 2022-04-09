package de.jet.paper.app.component.service

import de.jet.jvm.extension.container.page
import de.jet.jvm.extension.container.replace
import de.jet.jvm.extension.container.replaceVariables
import de.jet.jvm.extension.math.ceilToInt
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetCache.registeredServices
import de.jet.paper.extension.app
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.extension.lang
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.StructuredInterchange
import de.jet.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.ignoreCase
import de.jet.paper.structure.command.completion.isNotRequired
import de.jet.paper.tool.display.message.Transmission.Level.*
import kotlin.time.Duration.Companion.milliseconds

internal class ServiceInterchange : StructuredInterchange(
	label = "service",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		branch {
			addContent("list")

			ignoreCase()

			fun displayServices(executor: InterchangeExecutor, page: Int) {
				val pageValue = registeredServices.page(page, 6)

				if (pageValue.content.isNotEmpty()) {
					buildString {

						appendLine(
							lang("interchange.internal.service.list.header").replaceVariables(
								"p1" to pageValue.page + 1,
								"p2" to pageValue.pages,
							)
						)

						pageValue.content.withIndex().forEach {
							val service = it.value

							appendLine(lang("interchange.internal.service.list.line").replaceVariables(
								"service" to service.identity,
								"enabled" to if (service.isRunning) "<green><bold>ONLINE</bold>" else "<red><bold>OFFLINE</bold>",
								"activeSince" to (Calendar.now().timeInMilliseconds - (service.controller?.startTime
									?: Calendar.now()).timeInMilliseconds).milliseconds.toString(),
							))

						}

					}.notification(INFO, executor).display()
				} else
					lang("interchange.internal.service.noServices")
						.notification(FAIL, executor).display()
			}

			concludedExecution {

				displayServices(executor, 0)

				SUCCESS
			}

			branch {
				addContent(CompletionAsset.PAGES { ceilToInt(registeredServices.size.toDouble() / 6) })

				isNotRequired()

				execution {
					val page = getInput(1, InterchangeStructureInputRestriction.LONG).toInt() - 1

					if (page >= 0) {
						displayServices(executor, page)
					} else
						return@execution WRONG_USAGE

					return@execution SUCCESS
				}

			}

		}

		branch {

			addContent("at", "@")

			ignoreCase()

			branch {

				addContent(CompletionComponent.asset(CompletionAsset.SERVICE))

				branch {

					addContent("start")

					ignoreCase()

					concludedExecution {
						val service = registeredServices.first { it.identity == getInput(1) }

						try {

							app(service.vendor).start(service)

							lang("interchange.internal.service.serviceStarted")
								.replace("[id]" to service.identity)
								.notification(APPLIED, executor).display()

						} catch (exception: IllegalStateException) {
							lang("interchange.internal.service.serviceAlreadyOnline")
								.replace("[id]" to service.identity)
								.notification(FAIL, executor).display()
						}

					}

				}

				branch {

					addContent("stop")

					ignoreCase()

					concludedExecution {
						val service = registeredServices.first { it.identity == getInput(1) }

						try {

							app(service.vendor).stop(service)

							lang("interchange.internal.service.serviceStopped")
								.replace("[id]" to service.identity)
								.notification(APPLIED, executor).display()

						} catch (exception: IllegalStateException) {
							lang("interchange.internal.service.serviceAlreadyOffline")
								.replace("[id]" to service.identity)
								.notification(FAIL, executor).display()
						}

					}

				}

				branch {

					addContent("restart")

					ignoreCase()

					concludedExecution {
						val service = registeredServices.first { it.identity == getInput(1) }

						app(service.vendor).restart(service)

						lang("interchange.internal.service.serviceRestarted")
							.replace("[id]" to service.identity)
							.notification(APPLIED, executor).display()

					}

				}

				branch {

					addContent("unregister")

					ignoreCase()

					concludedExecution {
						val service = registeredServices.first { it.identity == getInput(1) }

						try {

							app(service.vendor).stop(service)
							app(service.vendor).unregister(service)

							lang("interchange.internal.service.serviceUnregistered")
								.replace("[id]" to service.identity)
								.notification(APPLIED, executor).display()

						} catch (exception: IllegalStateException) {
							lang("interchange.internal.service.serviceNotFound")
								.replace("[id]" to service.identity)
								.notification(FAIL, executor).display()
						}

					}

				}

				branch {

					addContent("reset")

					ignoreCase()

					concludedExecution {
						val service = registeredServices.first { it.identity == getInput(1) }

						try {

							app(service.vendor).reset(service)

							lang("interchange.internal.service.serviceReset")
								.replace("[id]" to service.identity)
								.notification(APPLIED, executor).display()

						} catch (exception: IllegalStateException) {
							lang("interchange.internal.service.serviceNotFound")
								.replace("[id]" to service.identity)
								.notification(FAIL, executor).display()
						}

					}

				}

			}

		}

	}
)