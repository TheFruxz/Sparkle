package de.moltenKt.paper.app.component.component

import de.moltenKt.core.extension.container.mapToString
import de.moltenKt.core.extension.container.page
import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.Constants
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.BOLD
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.structure.command.completion.isNotRequired
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.structure.component.file.ComponentManager
import de.moltenKt.paper.tool.display.message.Transmission
import de.moltenKt.paper.tool.display.message.Transmission.Level.APPLIED
import de.moltenKt.paper.tool.display.message.Transmission.Level.INFO
import de.moltenKt.unfold.extension.dyeDarkGray
import de.moltenKt.unfold.extension.dyeGray
import de.moltenKt.unfold.extension.dyeGreen
import de.moltenKt.unfold.extension.dyeRed
import de.moltenKt.unfold.extension.dyeYellow
import de.moltenKt.unfold.extension.style
import de.moltenKt.unfold.hover
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.Component.space
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

internal class ComponentInterchange : StructuredInterchange("component", protectedAccess = true, structure = buildInterchangeStructure {

		val iconDisabled = "⭘"
		val iconEnabled = "⏻"
		val iconAutoStart = "⚡"
		val iconForced = "☄"
		val iconExperimental = "⚗"
		val iconBlocked = "✘"

		fun list(page: Int, executor: InterchangeExecutor) {
			MoltenCache.registeredComponents.page(page - 1, Constants.ENTRIES_PER_PAGE).let { (page, pages, content) ->

				text {

					this + text("List of all registered components: ").dyeGray()
					this + text("(Page $page of ${pages.last})").dyeYellow()
					this + newline()
					this + text("$iconDisabled/$iconEnabled Power; $iconAutoStart Autostart; $iconForced Forced; $iconExperimental Experimental; $iconBlocked Blocked").dyeGray()
					this + newline() + newline()

					content.forEach { component ->

						this + when {
							component.isBlocked -> text(iconBlocked).hover {
								text {
									this + text("Blocked: ").style(NamedTextColor.BLUE, BOLD)
									this + newline()
									this + text("This component is blocked by the server owner (via file) and cannot be used!").dyeGray()
								}
							}.dyeRed()
							component.isRunning -> text(iconEnabled).hover {
								text {
									this + text("Enabled: ").style(NamedTextColor.BLUE, BOLD)
									this + newline()
									this + text("This component is currently running and executing its code!").dyeGray()
								}
							}.dyeGreen()
							else -> text(iconDisabled).hover {
								text {
									this + text("Disabled: ").style(NamedTextColor.BLUE, BOLD)
									this + newline()
									this + text("This component is currently not running and not executing its code!").dyeGray()
								}
							}.dyeGray()
						}

						this + space()

						this + when {
							component.isAutoStarting -> text(iconAutoStart).hover {
								text {
									this + text("Autostart: ").style(NamedTextColor.BLUE, BOLD)
									this + newline()
									this + text("This component will be started automatically on server start!").dyeGray()
								}
							}.dyeGreen()
							else -> text(iconAutoStart).dyeGray()
						}

						this + space()

						this + when {
							component.isForced -> text(iconForced).hover {
								text {
									this + text("Forced: ").style(NamedTextColor.BLUE, BOLD)
									this + newline()
									this + text("This component is forced to be started and cannot be stopped!").dyeGray()
								}
							}.dyeGreen()
							else -> text(iconForced).dyeGray()
						}

						this + when {
							component.isExperimental -> text(iconExperimental).hover {
								text {
									this + text("Experimental: ").style(NamedTextColor.BLUE, BOLD)
									this + newline()
									this + text("This component is experimental and may not work as expected!").dyeGray()
								}
							}.dyeYellow()
							else -> text(iconExperimental).dyeGray()
						}

						this + text(" | ").dyeDarkGray()

						this + text(component.label).hover {
							text {
								this + text("Label & Identity: ").style(NamedTextColor.BLUE, TextDecoration.BOLD)
								this + newline()
								this + text("The label is used to display the component in lists and information, the identity is used to identify the component in the system").dyeYellow()
								this + newline() + newline()
								this + text("Label: ").dyeGray()
								this + text(component.label).dyeGreen()
								this + newline()
								this + text("Identity: ").dyeGreen()
								this + text(component.key().asString()).dyeGreen()
							}
						}

					}

				}.notification(INFO, executor).display()

			}
		}

		fun start(component: Component, executor: InterchangeExecutor) {
			if (!component.isBlocked) {
				if (!component.isRunning) {

					component.vendor.start(component.identityObject)

					text {
						this + text("The component '").dyeGray()
						this + text(component.label).dyeYellow()
						this + text("' has been started!").dyeGray()
					}.notification(APPLIED, executor).display()

				} else {

					text {
						this + text("The component '").dyeGray()
						this + text(component.label).dyeYellow()
						this + text("' is already running!").dyeGray()
					}.notification(Transmission.Level.FAIL, executor).display()

				}
			} else {

				text {
					this + text("The component '").dyeGray()
					this + text(component.label).dyeYellow()
					this + text("' is blocked and cannot be started!").dyeGray()
				}.notification(Transmission.Level.FAIL, executor).display()

			}
		}

		fun stop(component: Component, executor: InterchangeExecutor) {
			if (component.isRunning) {

				if (component.canBeStopped) {

					component.vendor.stop(component.identityObject)

					text {
						this + text("The component '").dyeGray()
						this + text(component.label).dyeYellow()
						this + text("' has been stopped!").dyeGray()
					}.notification(APPLIED, executor).display()

				} else {

					text {
						this + text("The component '").dyeGray()
						this + text(component.label).dyeYellow()
						this + text("' is forced and cannot be stopped!").dyeGray()
					}.notification(Transmission.Level.FAIL, executor).display()

				}

			} else {

				text {
					this + text("The component '").dyeGray()
					this + text(component.label).dyeYellow()
					this + text("' is not running!").dyeGray()
				}.notification(Transmission.Level.FAIL, executor).display()

			}
		}

		fun restart(component: Component, executor: InterchangeExecutor) {
			if (component.isRunning) stop(component, executor)
			start(component, executor)
		}

		fun toggleAutostart(component: Component, executor: InterchangeExecutor) {
			if (component.canBeAutoStartToggled) {

				ComponentManager.editComponent(component.identityObject, isAutoStart = !component.isAutoStarting)

				text {
					this + text("The component '").dyeGray()
					this + text(component.label).dyeYellow()
					this + text("' has been ").dyeGray()
					this + text(if (component.isAutoStarting) "added" else "removed").dyeGreen()
					this + text(" from the autostart list!").dyeGray()
				}.notification(APPLIED, executor).display()

			} else {

				text {
					this + text("The component '").dyeGray()
					this + text(component.label).dyeYellow()
					this + text("' cannot be added to the autostart list!").dyeGray()
				}.notification(Transmission.Level.FAIL, executor).display()

			}
		}

		fun reset(component: Component, executor: InterchangeExecutor) {
			MoltenCache.runningComponents.apply {
				if (containsKey(component.identityObject))
					MoltenCache.runningComponents += component.identityObject to Calendar.now()
			}

			text {
				this + text("The component '").dyeGray()
				this + text(component.label).dyeYellow()
				this + text("' has been reset!").dyeGray()
			}.notification(APPLIED, executor).display()

		}

		fun info(component: Component, executor: InterchangeExecutor) {
			fun Any.toDisplay() = when (this) {
				is Boolean -> if (this) "YES" else "NO"
				else -> "$this"
			}

			text {
				this + text {
					this + text("Information about the component '").dyeGray()
					this + text(component.label).dyeYellow()
					this + text("':").dyeGray()
				}

				this + newline() + text("Label: ").dyeGray() + text(component.label).dyeYellow()
				this + newline() + text("Identity: ").dyeGray() + text(component.key().asString()).dyeYellow()
				this + newline() + text("Running: ").dyeGray() + text(component.isRunning.toDisplay()).dyeYellow()
				this + newline() + text("Configuration: ").dyeGray() + text(component.behaviour.name).dyeYellow()
				this + newline() + text("AutoStart: ").dyeGray() + text(component.isAutoStarting.toDisplay()).dyeYellow()
				this + newline() + text("Experimental: ").dyeGray() + text(component.isExperimental.toDisplay()).dyeYellow()
				this + newline() + text("Running since: ").dyeGray() + text(component.runningSince?.durationToNow()?.toString() ?: "-/-").dyeYellow()

			}.notification(INFO, executor).display()

		}

		branch {

			addContent(
				"list",
				"stopAll",
				"startAll",
				"restartAll",
				"autostartAll",
				"resetAll"
			)

			branch {

				addContent(
					CompletionAsset<Long>(
						vendor = system,
						thisIdentity = "componentPage",
						refreshing = true,
						supportedInputType = listOf(InterchangeStructureInputRestriction.LONG),
						generator = {
							(1..ceilToInt(MoltenCache.registeredComponents.size.toDouble() / Constants.ENTRIES_PER_PAGE)).mapToString()
						})
				)
				isNotRequired()

				concludedExecution {

					val page = getInput(1, InterchangeStructureInputRestriction.LONG)

					list(page.toInt(), executor)

				}

			}

			concludedExecution {

				fun processAllComponents(function: (Component, InterchangeExecutor) -> Unit) {
					MoltenCache.registeredComponents.forEach {
						function(it, executor)
					}
				}

				when (getInput(0)) {

					"list" -> {
						list(1, executor)
					}

					"stopAll" -> {
						processAllComponents(::stop)
					}

					"startAll" -> {
						processAllComponents(::start)
					}

					"restartAll" -> {
						processAllComponents(::restart)
					}

					"autostartAll" -> {
						processAllComponents(::toggleAutostart)
					}

					"resetAll" -> {
						processAllComponents(::reset)
					}

				}

			}

		}

		branch {

			addContent("at", "@")

			ignoreCase()

			branch {

				addContent(CompletionAsset.COMPONENT)

				branch {

					addContent("start")

					ignoreCase()

					concludedExecution {

						start(getInput(1, CompletionAsset.COMPONENT), executor)

					}

				}

				branch {

					addContent("stop")

					ignoreCase()

					concludedExecution {

						stop(getInput(1, CompletionAsset.COMPONENT), executor)

					}

				}

				branch {

					addContent("restart")

					ignoreCase()

					concludedExecution {

						restart(getInput(1, CompletionAsset.COMPONENT), executor)

					}

				}

				branch {

					addContent("reset")

					ignoreCase()

					concludedExecution {

						reset(getInput(1, CompletionAsset.COMPONENT), executor)

					}

				}

				branch {

					addContent("autostart")

					ignoreCase()

					concludedExecution {

						toggleAutostart(getInput(1, CompletionAsset.COMPONENT), executor)

					}

				}

				branch {

					addContent("info")

					ignoreCase()

					concludedExecution {

						info(getInput(1, CompletionAsset.COMPONENT), executor)

					}

				}

			}

		}

	})