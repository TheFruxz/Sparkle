package de.jet.paper.app.interchange

import de.jet.jvm.extension.collection.replaceVariables
import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.paper.app.JetCache
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.lang
import de.jet.paper.extension.o.allSandBoxes
import de.jet.paper.extension.o.destroyAllSandBoxes
import de.jet.paper.extension.o.destroySandBox
import de.jet.paper.extension.o.getSandBox
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.completion.buildCompletion
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.component.CompletionComponent.Companion
import de.jet.paper.structure.command.completion.infiniteSubParameters
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.tool.display.message.Transmission.Level.*

class SandboxInterchange(vendor: App = system) :
	Interchange(
		vendor = vendor,
		label = "sandbox",
		protectedAccess = true,
		completion = buildCompletion {
			branch {
				content(CompletionComponent.static("list", "dropAll", "runAll"))
			}
			branch {
				content(Companion.static("drop", "run", "info"))
				branch {
					infiniteSubParameters()
					content(Companion.asset(CompletionAsset.SANDBOX))
				}
			}
		}
	) {

	override val execution: InterchangeAccess.() -> InterchangeResult = result@{

		when {
			parameters.size == 1 -> {

				when (parameters.first().lowercase()) {

					"list" -> {

						if (allSandBoxes.isNotEmpty()) {
							buildString {
								appendLine(lang("interchange.internal.sandbox.list.header"))

								allSandBoxes.withIndex().forEach {

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

					@Suppress("SpellCheckingInspection") "dropall", @Suppress("SpellCheckingInspection") "runall" -> {

						if (allSandBoxes.isNotEmpty()) {

							@Suppress("SpellCheckingInspection")
							if (parameters.first().equals("dropall", true)) {

								val message = lang("interchange.internal.sandbox.dropAll")
									.replaceVariables("amount" to allSandBoxes.size)

								destroyAllSandBoxes()

								message.notification(APPLIED, executor).display()

							} else {

								allSandBoxes.forEach { sandBox ->

									sandBox.execute(executor)

								}

								lang("interchange.internal.sandbox.runAll")
									.replaceVariables("amount" to allSandBoxes.size)
									.notification(APPLIED, executor).display()

							}

						} else
							lang("interchange.internal.sandbox.noFound")
								.notification(FAIL, executor).display()

					}

					else -> return@result WRONG_USAGE

				}

			}
			parameters.size > 1 -> {

				val sandBox = getSandBox(parameters[1])

				if (sandBox != null) {

					when (parameters.first().lowercase()) {

						"drop" -> {

							destroySandBox(sandBox)

							lang("interchange.internal.sandbox.drop")
								.replaceVariables("sandbox" to sandBox.identity)
								.notification(APPLIED, executor).display()

						}

						"run" -> {

							sandBox.execute(executor, parameters.drop(2))

						}

						"info" -> {

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

						else -> return@result WRONG_USAGE

					}

				} else
					lang("interchange.internal.sandbox.notFound")
						.replaceVariables("sandbox" to parameters.last())
						.notification(FAIL, executor)

			}

			else -> return@result WRONG_USAGE

		}

		return@result SUCCESS

	}

}