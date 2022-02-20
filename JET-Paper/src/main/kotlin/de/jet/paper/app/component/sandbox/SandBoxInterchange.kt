package de.jet.paper.app.component.sandbox

import de.jet.jvm.extension.container.mapToString
import de.jet.jvm.extension.container.page
import de.jet.jvm.extension.container.replaceVariables
import de.jet.jvm.extension.math.ceilToInt
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetCache
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.extension.lang
import de.jet.paper.extension.objectBound.allSandBoxes
import de.jet.paper.extension.objectBound.destroyAllSandBoxes
import de.jet.paper.extension.objectBound.destroySandBox
import de.jet.paper.extension.objectBound.getSandBox
import de.jet.paper.extension.system
import de.jet.paper.structure.command.StructuredInterchange
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.component.CompletionComponent.Companion
import de.jet.paper.structure.command.completion.ignoreCase
import de.jet.paper.structure.command.completion.isNotRequired
import de.jet.paper.tool.display.message.Transmission.Level.*

internal class SandBoxInterchange : StructuredInterchange("sandbox", buildInterchangeStructure {

	branch {
		addContent(CompletionComponent.static("dropAll"))

		ignoreCase()

		concludedExecution {

			if (allSandBoxes.isNotEmpty()) {

				val message = lang("interchange.internal.sandbox.dropAll")
					.replaceVariables("amount" to allSandBoxes.size)

				destroyAllSandBoxes()

				message.notification(APPLIED, executor).display()

			} else
				lang("interchange.internal.sandbox.noFound")
					.notification(FAIL, executor).display()
		}

	}

	branch {
		addContent(CompletionComponent.static("runAll"))

		ignoreCase()

		concludedExecution {

			if (allSandBoxes.isNotEmpty()) {
				allSandBoxes.forEach { sandBox ->

					sandBox.execute(executor)

				}

				lang("interchange.internal.sandbox.runAll")
					.replaceVariables("amount" to allSandBoxes.size)
					.notification(APPLIED, executor).display()

			} else
				lang("interchange.internal.sandbox.noFound")
					.notification(FAIL, executor).display()
		}

	}

	branch {
		addContent(CompletionComponent.static("list"))

		ignoreCase()

		fun displaySandBoxes(executor: InterchangeExecutor, page: Int) {
			val pageValue = allSandBoxes.page(page, 6)

			if (pageValue.content.isNotEmpty()) {
				buildString {
					appendLine(
						lang("interchange.internal.sandbox.list.header").replaceVariables(
							"p1" to pageValue.page+1,
							"p2" to pageValue.pages,
						)
					)

					pageValue.content.withIndex().forEach {

						lang("interchange.internal.sandbox.list.line").replaceVariables(
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
				lang("interchange.internal.sandbox.noFound")
					.notification(FAIL, executor).display()
		}

		concludedExecution {

			displaySandBoxes(executor, 0)

			SUCCESS
		}

		branch {
			addContent(CompletionComponent.asset(CompletionAsset(
				vendor = system,
				thisIdentity = "Page",
				true,
				listOf(InterchangeStructureInputRestriction.LONG),
				generator = {
					(1..ceilToInt(allSandBoxes.size.toDouble() / 6)).mapToString()
				},
			)))

			isNotRequired()

			execution {
				val page = getInput(1, InterchangeStructureInputRestriction.LONG).toInt()-1

				if (page >= 0) {
					displaySandBoxes(executor, page)
				} else
					return@execution WRONG_USAGE

				return@execution SUCCESS
			}

		}

	}

	branch {

		addContent(CompletionComponent.static("do"))

		ignoreCase()

		branch {

			addContent(Companion.asset(CompletionAsset.SANDBOX))

			branch {

				addContent(Companion.static("drop"))

				concludedExecution {
					val sandBox = getSandBox(getInput(1))!!

					destroySandBox(sandBox)

					lang("interchange.internal.sandbox.drop")
						.replaceVariables("sandbox" to sandBox.identity)
						.notification(APPLIED, executor).display()

				}

			}

			branch {

				addContent(Companion.static("run"))

				concludedExecution {
					val sandBox = getSandBox(getInput(1))!!

					sandBox.execute(executor)

				}

			}

			branch {

				addContent(Companion.static("info"))

				concludedExecution {
					val sandBox = getSandBox(getInput(1))!!

					buildString {
						appendLine(lang("interchange.internal.sandbox.info.header"))

						mapOf(
							"VendorId" to sandBox.vendor.identity,
							"VendorLabel" to sandBox.vendor.appLabel,
							"Identity" to sandBox.identity,
							"Since" to sandBox.creationTime.durationTo(Calendar.now()).toString(),
							"CreationPos" to sandBox.creationLocation,
							"Cached Calls" to (JetCache.registeredSandBoxCalls[sandBox.identityObject]
								?: "none")
						).forEach { (key, value) ->
							appendLine(
								lang("interchange.internal.sandbox.info.line").replaceVariables(
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

})