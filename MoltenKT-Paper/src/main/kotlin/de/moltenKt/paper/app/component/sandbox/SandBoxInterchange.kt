package de.moltenKt.paper.app.component.sandbox

import de.moltenKt.core.extension.container.page
import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.Constants
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.extension.objectBound.allSandBoxes
import de.moltenKt.paper.extension.objectBound.destroyAllSandBoxes
import de.moltenKt.paper.extension.objectBound.destroySandBox
import de.moltenKt.paper.extension.objectBound.getSandBox
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.structure.command.completion.infiniteSubParameters
import de.moltenKt.paper.structure.command.completion.isNotRequired
import de.moltenKt.paper.tool.display.message.Transmission.Level.*

internal class SandBoxInterchange : StructuredInterchange(
	"sandbox",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		branch {
			addContent("dropAll")

			ignoreCase()

			concludedExecution {

				if (allSandBoxes.isNotEmpty()) {

					val message = lang["interchange.internal.sandbox.dropAll"]
						.replaceVariables("amount" to allSandBoxes.size)

					destroyAllSandBoxes()

					message.notification(APPLIED, executor).display()

				} else
					lang["interchange.internal.sandbox.noFound"]
						.notification(FAIL, executor).display()
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

					lang["interchange.internal.sandbox.runAll"]
						.replaceVariables("amount" to allSandBoxes.size)
						.notification(APPLIED, executor).display()

				} else
					lang["interchange.internal.sandbox.noFound"]
						.notification(FAIL, executor).display()
			}

		}

		branch {

			addContent("list")
			ignoreCase()

			fun displaySandBoxes(executor: InterchangeExecutor, page: Int) {
				val pageValue = allSandBoxes.page(page, Constants.ENTRIES_PER_PAGE)

				if (pageValue.content.isNotEmpty()) {
					buildString {
						appendLine(
							lang["interchange.internal.sandbox.list.header"].replaceVariables(
								"p1" to pageValue.page + 1,
								"p2" to pageValue.pages,
							)
						)

						pageValue.content.withIndex().forEach {

							lang["interchange.internal.sandbox.list.line"].replaceVariables(
								"sandbox" to it.value.identity,
								"vendor" to it.value.vendor.appLabel,
							).let { message ->
								if (it.index == allSandBoxes.indices.last) {
									append(message)
								} else
									appendLine(message)
							}

						}

					}.notification(INFO, executor).display()
				} else
					lang["interchange.internal.sandbox.noFound"]
						.notification(FAIL, executor).display()
			}

			concludedExecution {

				displaySandBoxes(executor, 0)

				SUCCESS
			}

			branch {
				addContent(CompletionAsset.PAGES { ceilToInt(allSandBoxes.size.toDouble() / Constants.ENTRIES_PER_PAGE) })

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

						lang["interchange.internal.sandbox.drop"]
							.replaceVariables("sandbox" to sandBox.identity)
							.notification(APPLIED, executor).display()

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

						buildString {
							appendLine(lang["interchange.internal.sandbox.info.header"])

							mapOf(
								"VendorId" to sandBox.vendor.identity,
								"VendorLabel" to sandBox.vendor.appLabel,
								"Identity" to sandBox.identity,
								"Since" to sandBox.creationTime.durationTo(Calendar.now()).toString(),
								"CreationPos" to sandBox.creationLocation,
								"Cached Calls" to (MoltenCache.registeredSandBoxCalls[sandBox.identityObject]
									?: "none")
							).forEach { (key, value) ->
								appendLine(
									lang["interchange.internal.sandbox.info.line"].replaceVariables(
										"key" to key,
										"value" to value,
									)
								)
							}

						}.notification(INFO, executor).display()

					}

				}

			}

		}

	}
)