package de.fruxz.sparkle.app.component.component

import de.fruxz.ascend.extension.container.mapToString
import de.fruxz.ascend.extension.container.page
import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.app.SparkleCache
import de.fruxz.sparkle.extension.display.BOLD
import de.fruxz.sparkle.extension.display.notification
import de.fruxz.sparkle.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.extension.system
import de.fruxz.sparkle.structure.command.completion.InterchangeStructureInputRestriction
import de.fruxz.sparkle.structure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.structure.command.completion.component.CompletionAsset
import de.fruxz.sparkle.structure.command.completion.ignoreCase
import de.fruxz.sparkle.structure.command.completion.isNotRequired
import de.fruxz.sparkle.structure.command.structured.StructuredInterchange
import de.fruxz.sparkle.structure.component.Component
import de.fruxz.sparkle.structure.component.file.ComponentManager
import de.fruxz.sparkle.tool.display.message.Transmission
import de.fruxz.sparkle.tool.display.message.Transmission.Level.APPLIED
import de.fruxz.sparkle.tool.display.message.Transmission.Level.GENERAL
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
			SparkleCache.registeredComponents.page(page - 1, de.fruxz.sparkle.Constants.ENTRIES_PER_PAGE).let { (page, pages, content) ->

				text {

					this + text("List of all registered components: ").dyeGray()
					this + text("(Page $page of ${pages.last})").dyeYellow()
					this + newline()
					this + text("$iconDisabled/$iconEnabled Power; $iconAutoStart Autostart; $iconForced Forced; $iconExperimental Experimental; $iconBlocked Blocked").dyeGray()
					this + newline()

					content.forEach { component ->

						this + newline()
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

						this + space()

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

						this + text(component.label).dyeGold().hover {
							text {
								this + text("Label & Identity: ").style(NamedTextColor.BLUE, TextDecoration.BOLD)
								this + newline()
								this + text("The label is used to display the component in lists and information, the identity is used to identify the component in the system").dyeYellow()
								this + newline() + newline()
								this + text("Label: ").dyeGray()
								this + text(component.label).dyeGreen()
								this + newline()
								this + text("Identity: ").dyeGray()
								this + text(component.key().asString()).dyeGreen()
							}
						}

						this + space()

					}

					newlines(2)

				}.notification(GENERAL, executor).display()

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
			SparkleCache.runningComponents.apply {
				if (containsKey(component.identityObject))
					SparkleCache.runningComponents += component.identityObject to Calendar.now()
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
					this + text(component.label).dyeGold()
					this + text("':").dyeGray()
				}

				this + newline()

				this + newline() + text("Label: ").dyeGray() + text(component.label).dyeYellow()
				this + newline() + text("Identity: ").dyeGray() + text(component.key().asString()).dyeYellow()
				this + newline() + text("Running: ").dyeGray() + text(component.isRunning.toDisplay()).dyeYellow()
				this + newline() + text("Configuration: ").dyeGray() + text(component.behaviour.name).dyeYellow()
				this + newline() + text("AutoStart: ").dyeGray() + text(component.isAutoStarting.toDisplay()).dyeYellow()
				this + newline() + text("Experimental: ").dyeGray() + text(component.isExperimental.toDisplay()).dyeYellow()
				this + newline() + text("Running since: ").dyeGray() + text(component.runningSince?.durationToNow()?.toString() ?: "-/-").dyeYellow()

				newlines(2)

			}.notification(GENERAL, executor).display()

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
							(1..ceilToInt(SparkleCache.registeredComponents.size.toDouble() / de.fruxz.sparkle.Constants.ENTRIES_PER_PAGE)).mapToString()
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
					SparkleCache.registeredComponents.forEach {
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