package de.fruxz.sparkle.server.component.service

import de.fruxz.ascend.extension.container.paged
import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.switchResult
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.WRONG_USAGE
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructureInputRestriction
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionComponent
import de.fruxz.sparkle.framework.infrastructure.command.completion.ignoreCase
import de.fruxz.sparkle.framework.infrastructure.command.completion.isNotRequired
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.visual.message.Transmission.Level.*
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.server.SparkleData
import de.fruxz.stacked.extension.dyeDarkGray
import de.fruxz.stacked.extension.dyeGold
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeGreen
import de.fruxz.stacked.extension.dyeRed
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.extension.newlines
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.hover
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration.BOLD

internal class ServiceInterchange : StructuredInterchange(
	label = "service",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		val iconDisabled = "⭘"
		val iconEnabled = "⏻"

		branch {
			addContent("list")

			ignoreCase()

			fun displayServices(executor: InterchangeExecutor, page: Int) {
				val pageValue = SparkleCache.serviceStates.values.paged(page, SparkleData.systemConfig.entriesPerListPage)

				if (pageValue.content.isNotEmpty()) {

					text {
						this + text("List of all registered services: ").dyeGray()
						this + text("(Page ${pageValue.pageNumber} of ${pageValue.availablePages.last})").dyeYellow()
						this + newline()

						pageValue.content.forEach { serviceState ->
							this + newline()
							this + serviceState.service.isRunning.switchResult(
								text(iconEnabled) {
									dyeGreen()
									hover {
										text {
											this + text("Enabled: ").style(NamedTextColor.BLUE, BOLD)
											this + newline()
											this + text("This service is currently iterating and executing its code!").dyeGray()
											newlines(2)
											this + text {
												this + text("CLICK ").style(NamedTextColor.GREEN, BOLD)
												this + text("to disable this service").dyeGray()
											}
										}
									}
									clickEvent(ClickEvent.runCommand("/service @ ${serviceState.service.key} stop"))
								},
								text(iconDisabled) {
									dyeGray()
									hover {
										text {
											this + text("Disabled: ").style(NamedTextColor.BLUE, BOLD)
											this + newline()
											this + text("This service is currently not executing its code!").dyeGray()
											newlines(2)
											this + text {
												this + text("CLICK ").style(NamedTextColor.GREEN, BOLD)
												this + text("to enable this service").dyeGray()
											}
										}
									}
									clickEvent(ClickEvent.runCommand("/service @ ${serviceState.service.key} start"))
								},
							)
							this + text(" | ").dyeDarkGray()
							this + text {
								this + text(serviceState.service.label).dyeGold().hover {
									text {
										this + text("Label & Identity: ").style(NamedTextColor.BLUE, BOLD)
										this + newline()
										this + text("The label is used to display the service in lists and information, the identity is used to identify the service in the system").dyeYellow()

										newlines(2)

										this + text("Label: ").dyeGray()
										this + text(serviceState.service.label).dyeGreen()
										this + newline()
										this + text("Identity: ").dyeGray()
										this + text(serviceState.service.key().asString()).dyeGreen()
									}
								}
							}

							serviceState.runningSince?.durationToNow()?.let { time ->
								this + text(" (running $time)").dyeGray()
							}

						}

						newlines(2)

					}.notification(GENERAL, executor).display()

				} else {

					text {
						this + text("There are currently ").dyeGray()
						this + text("no services").dyeRed()
						this + text(" registered!").dyeGray()
					}.notification(FAIL, executor).display()

				}
			}

			concludedExecution {

				displayServices(executor, 0)

				SUCCESS
			}

			branch {
				addContent(CompletionAsset.pageCompletion { ceilToInt(SparkleCache.serviceStates.values.size.toDouble() / SparkleData.systemConfig.entriesPerListPage) })

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
						val service = SparkleCache.serviceStates.values.first { it.service.identity == getInput(1) }

						if (!service.service.isRunning) {

							service.service.requestStart()

							text {
								this + text("The service '").dyeGray()
								this + text(service.service.label).dyeYellow()
									.hover { text("Identity: ").dyeGray() + text(service.service.identity).dyeYellow() }
								this + text("' has been started!").dyeGray()
							}.notification(APPLIED, executor).display()

						} else {

							text {
								this + text("The service '").dyeGray()
								this + text(service.service.label).dyeYellow()
									.hover { text("Identity: ").dyeGray() + text(service.service.identity).dyeYellow() }
								this + text("' is already online!").dyeGray()
							}

						}

					}

				}

				branch {

					addContent("stop")

					ignoreCase()

					concludedExecution {
						val service = SparkleCache.serviceStates.values.first { it.service.identity == getInput(1) }

						if (service.service.isRunning) {

							service.service.requestStop()

							text {
								this + text("The service '").dyeGray()
								this + text(service.service.label).dyeYellow()
									.hover { text("Identity: ").dyeGray() + text(service.service.identity).dyeYellow() }
								this + text("' has been stopped!").dyeGray()
							}.notification(APPLIED, executor).display()

						} else {

							text {
								this + text("The service '").dyeGray()
								this + text(service.service.label).dyeYellow()
									.hover { text("Identity: ").dyeGray() + text(service.service.identity).dyeYellow() }
								this + text("' is already offline!").dyeGray()
							}.notification(FAIL, executor).display()

						}

					}

				}

				branch {

					addContent("restart")

					ignoreCase()

					concludedExecution {
						val service = SparkleCache.serviceStates.values.first { it.service.identity == getInput(1) }

						service.service.requestStop()
						service.service.requestStart()

						text {
							this + text("The service '").dyeGray()
							this + text(service.service.label).dyeYellow()
								.hover { text("Identity: ").dyeGray() + text(service.service.identity).dyeYellow() }
							this + text("' has been restarted!").dyeGray()
						}.notification(APPLIED, executor).display()

					}

				}

				branch {

					addContent("unregister")

					ignoreCase()

					concludedExecution {
						val service = SparkleCache.serviceStates.values.first { it.service.identity == getInput(1) }

						try {

							service.service.requestStop()
							service.service.requestStart()

							text {
								this + text("The service '").dyeGray()
								this + text(service.service.label).dyeYellow()
									.hover { text("Identity: ").dyeGray() + text(service.service.identity).dyeYellow() }
								this + text("' has been unregistered!").dyeGray()
							}.notification(APPLIED, executor).display()

						} catch (exception: IllegalStateException) {

							text {
								this + text("The service '").dyeGray()
								this + text(getInput(1)).dyeYellow()
								this + text("' is not registered!").dyeGray()
							}.notification(FAIL, executor).display()

						}

					}

				}

			}

		}

	}
)