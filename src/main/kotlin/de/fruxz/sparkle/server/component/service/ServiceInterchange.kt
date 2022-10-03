package de.fruxz.sparkle.server.component.service

import de.fruxz.ascend.extension.container.page
import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.switchResult
import de.fruxz.sparkle.server.SparkleCache.registeredServices
import de.fruxz.sparkle.framework.Constants
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.WRONG_USAGE
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructureInputRestriction
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionComponent
import de.fruxz.sparkle.framework.infrastructure.command.completion.ignoreCase
import de.fruxz.sparkle.framework.infrastructure.command.completion.isNotRequired
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.util.extension.app
import de.fruxz.sparkle.framework.util.extension.visual.notification
import de.fruxz.sparkle.framework.util.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.util.visual.message.Transmission.Level.*
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
				val pageValue = registeredServices.page(page, Constants.ENTRIES_PER_PAGE)

				if (pageValue.content.isNotEmpty()) {

					text {
						this + text("List of all registered services: ").dyeGray()
						this + text("(Page ${pageValue.pageNumber} of ${pageValue.availablePages.last})").dyeYellow()
						this + newline()

						pageValue.content.forEach { service ->
							this + newline()
							this + service.isRunning.switchResult(
								text(iconEnabled).dyeGreen(),
								text(iconDisabled).dyeGray(),
							)
							this + text(" | ").dyeDarkGray()
							this + text {
								this + text(service.label).dyeGold().hover {
									text {
										this + text("Label & Identity: ").style(NamedTextColor.BLUE, BOLD)
										this + newline()
										this + text("The label is used to display the service in lists and information, the identity is used to identify the service in the system").dyeYellow()

										newlines(2)

										this + text("Label: ").dyeGray()
										this + text(service.label).dyeGreen()
										this + newline()
										this + text("Identity: ").dyeGray()
										this + text(service.key().asString()).dyeGreen()
									}
								}
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
				addContent(CompletionAsset.pageCompletion { ceilToInt(registeredServices.size.toDouble() / Constants.ENTRIES_PER_PAGE) })

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

							text {
								this + text("The service '").dyeGray()
								this + text(service.label).dyeYellow().hover { text("Identity: ").dyeGray() + text(service.identity).dyeYellow() }
								this + text("' has been started!").dyeGray()
							}.notification(APPLIED, executor).display()

						} catch (exception: IllegalStateException) {

							text {
								this + text("The service '").dyeGray()
								this + text(service.label).dyeYellow().hover { text("Identity: ").dyeGray() + text(service.identity).dyeYellow() }
								this + text("' is already online!").dyeGray()
							}

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

							text {
								this + text("The service '").dyeGray()
								this + text(service.label).dyeYellow().hover { text("Identity: ").dyeGray() + text(service.identity).dyeYellow() }
								this + text("' has been stopped!").dyeGray()
							}.notification(APPLIED, executor).display()

						} catch (exception: IllegalStateException) {

							text {
								this + text("The service '").dyeGray()
								this + text(service.label).dyeYellow().hover { text("Identity: ").dyeGray() + text(service.identity).dyeYellow() }
								this + text("' is already offline!").dyeGray()
							}.notification(FAIL, executor).display()

						}

					}

				}

				branch {

					addContent("restart")

					ignoreCase()

					concludedExecution {
						val service = registeredServices.first { it.identity == getInput(1) }

						app(service.vendor).restart(service)

						text {
							this + text("The service '").dyeGray()
							this + text(service.label).dyeYellow().hover { text("Identity: ").dyeGray() + text(service.identity).dyeYellow() }
							this + text("' has been restarted!").dyeGray()
						}.notification(APPLIED, executor).display()

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

							text {
								this + text("The service '").dyeGray()
								this + text(service.label).dyeYellow().hover { text("Identity: ").dyeGray() + text(service.identity).dyeYellow() }
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

				branch {

					addContent("reset")

					ignoreCase()

					concludedExecution {
						val service = registeredServices.first { it.identity == getInput(1) }

						try {

							app(service.vendor).reset(service)

							text {
								this + text("The service '").dyeGray()
								this + text(service.label).dyeYellow().hover { text("Identity: ").dyeGray() + text(service.identity).dyeYellow() }
								this + text("' has been reset!").dyeGray()
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