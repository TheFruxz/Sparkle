package de.moltenKt.paper.app.component.service

import de.moltenKt.core.extension.container.page
import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.switchResult
import de.moltenKt.paper.Constants
import de.moltenKt.paper.app.MoltenCache.registeredServices
import de.moltenKt.paper.extension.app
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.component.CompletionComponent
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.structure.command.completion.isNotRequired
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.tool.display.message.Transmission.Level.*
import de.moltenKt.unfold.extension.dyeDarkGray
import de.moltenKt.unfold.extension.dyeGold
import de.moltenKt.unfold.extension.dyeGray
import de.moltenKt.unfold.extension.dyeGreen
import de.moltenKt.unfold.extension.dyeRed
import de.moltenKt.unfold.extension.dyeYellow
import de.moltenKt.unfold.extension.newlines
import de.moltenKt.unfold.extension.style
import de.moltenKt.unfold.hover
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

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
										this + text("Label & Identity: ").style(NamedTextColor.BLUE, TextDecoration.BOLD)
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
				addContent(CompletionAsset.PAGES { ceilToInt(registeredServices.size.toDouble() / Constants.ENTRIES_PER_PAGE) })

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