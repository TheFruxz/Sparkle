package de.jet.app.interchange

import de.jet.app.JetCache
import de.jet.library.extension.collection.replaceVariables
import de.jet.library.extension.display.notification
import de.jet.library.extension.lang
import de.jet.library.extension.obj.allSandBoxes
import de.jet.library.extension.obj.destroyAllSandBoxes
import de.jet.library.extension.obj.destroySandBox
import de.jet.library.extension.obj.getSandBox
import de.jet.library.extension.system
import de.jet.library.structure.app.App
import de.jet.library.structure.command.CompletionVariable
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeResult
import de.jet.library.structure.command.InterchangeResult.SUCCESS
import de.jet.library.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.library.structure.command.buildCompletion
import de.jet.library.structure.command.infinite
import de.jet.library.structure.command.isRequired
import de.jet.library.structure.command.live.InterchangeAccess
import de.jet.library.structure.command.mustMatchOutput
import de.jet.library.structure.command.next
import de.jet.library.tool.display.message.Transmission.Level.*
import de.jet.library.tool.timing.calendar.Calendar

class SandboxInterchange(vendor: App = system) :
	Interchange(vendor, "sandbox", requiresAuthorization = true, completion = buildCompletion {

		next(listOf("list", "drop", "run", "info", "dropAll", "runAll")) isRequired true mustMatchOutput true
		next(CompletionVariable.SANDBOX) isRequired false mustMatchOutput true infinite true

	}) {

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