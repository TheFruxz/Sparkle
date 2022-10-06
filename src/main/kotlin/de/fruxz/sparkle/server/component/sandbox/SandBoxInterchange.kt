package de.fruxz.sparkle.server.component.sandbox

import de.fruxz.ascend.extension.container.page
import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.framework.Constants
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.WRONG_USAGE
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructureInputRestriction
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.ignoreCase
import de.fruxz.sparkle.framework.infrastructure.command.completion.infiniteSubParameters
import de.fruxz.sparkle.framework.infrastructure.command.completion.isNotRequired
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.allSandBoxes
import de.fruxz.sparkle.framework.extension.destroyAllSandBoxes
import de.fruxz.sparkle.framework.extension.destroySandBox
import de.fruxz.sparkle.framework.extension.getSandBox
import de.fruxz.sparkle.framework.visual.message.Transmission.Level.*
import de.fruxz.stacked.extension.dyeGold
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeGreen
import de.fruxz.stacked.extension.dyeRed
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import net.kyori.adventure.text.Component.newline

internal class SandBoxInterchange : StructuredInterchange(
	"sandbox",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		branch {
			addContent("dropAll")

			ignoreCase()

			concludedExecution {

				if (allSandBoxes.isNotEmpty()) {

					val message = text {
						this + text("Successfully").dyeGreen()
						this + text(" dropped ${allSandBoxes.size} sandboxes").dyeGray()
					}

					destroyAllSandBoxes()

					message.notification(APPLIED, executor).display()

				} else {

					text {
						this + text("There are currently ").dyeGray()
						this + text("no sandboxes").dyeRed()
						this + text(" registered!").dyeGray()
					}.notification(FAIL, executor).display()

				}
			}

		}

		branch {
			addContent("runAll")

			ignoreCase()

			concludedExecution {

				if (allSandBoxes.isNotEmpty()) {

					allSandBoxes.forEach { sandBox ->
						sandBox.execute(executor)
					}

					text {
						this + text("Successfully").dyeGreen()
						this + text(" started ${allSandBoxes.size} sandboxes").dyeGray()
					}.notification(APPLIED, executor).display()

				} else {

					text {
						this + text("There are currently ").dyeGray()
						this + text("no sandboxes").dyeRed()
						this + text(" registered!").dyeGray()
					}.notification(FAIL, executor).display()

				}
			}

		}

		branch {

			addContent("list")
			ignoreCase()

			fun displaySandBoxes(executor: InterchangeExecutor, page: Int) {
				val pageValue = allSandBoxes.page(page, Constants.ENTRIES_PER_PAGE)

				if (pageValue.content.isNotEmpty()) {

					text {
						this + text("List of all registered SandBoxes: ").dyeGray()
						this + text("(Page ${page + 1} of ${pageValue.availablePages.last})").dyeYellow()

						pageValue.content.forEach { sandBox ->
							this + newline() + text(sandBox.identity).dyeYellow() + text(" (from: ${sandBox.vendor.label})").dyeGray()
						}

					}.notification(GENERAL, executor).display()

				} else {

					text {
						this + text("There are currently ").dyeGray()
						this + text("no sandboxes").dyeRed()
						this + text(" registered!").dyeGray()
					}.notification(FAIL, executor).display()

				}
			}

			concludedExecution {

				displaySandBoxes(executor, 0)

				SUCCESS
			}

			branch {
				addContent(CompletionAsset.pageCompletion { ceilToInt(allSandBoxes.size.toDouble() / Constants.ENTRIES_PER_PAGE) })

				isNotRequired()

				execution {
					val page = getInput(1, InterchangeStructureInputRestriction.LONG).toInt() - 1

					if (page >= 0) {
						displaySandBoxes(executor, page)
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

				addContent(CompletionAsset.SANDBOX)

				branch {

					addContent("drop")

					concludedExecution {
						val sandBox = getSandBox(getInput(1))!!

						destroySandBox(sandBox)

						text {
							this + text("Successfully").dyeGreen()
							this + text(" dropped sandbox ").dyeGray()
							this + text(sandBox.identity).dyeYellow()
						}.notification(APPLIED, executor).display()

					}

				}

				branch {

					addContent("run")

					infiniteSubParameters()

					concludedExecution {
						val sandBox = getSandBox(getInput(1))!!

						sandBox.execute(executor, parameters.drop(3))

					}

				}

				branch {

					addContent("info")

					concludedExecution {
						val sandBox = getSandBox(getInput(1))!!

						text {
							this + text("Info about registered SandBox: ").dyeGray()
							this + newline() + text("Vendor-Identifier: ").dyeGold() + text(sandBox.vendor.identity).dyeYellow()
							this + newline() + text("Vendor-Label: ").dyeGold() + text(sandBox.vendor.label).dyeYellow()
							this + newline() + text("Identity: ").dyeGold() + text(sandBox.identity).dyeYellow()
							this + newline() + text("Since: ").dyeGold() + text(sandBox.creationTime.durationTo(Calendar.now()).toString()).dyeYellow()
							this + newline() + text("Creation-Position: ").dyeGold() + text(sandBox.creationLocation).dyeYellow()
							this + newline() + text("Cached Calls: ").dyeGold() + text((SparkleCache.registeredSandBoxCalls[sandBox.identityObject]?.toString() ?: "none")).dyeYellow()
						}.notification(GENERAL, executor).display()

					}

				}

			}

		}

	}
)