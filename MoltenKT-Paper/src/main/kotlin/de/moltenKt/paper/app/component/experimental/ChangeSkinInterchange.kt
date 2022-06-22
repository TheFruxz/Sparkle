package de.moltenKt.paper.app.component.experimental

import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.extension.mojang.applySkin
import de.moltenKt.paper.extension.mojang.resetSkin
import de.moltenKt.paper.extension.tasky.asSync
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.component.CompletionComponent
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.structure.command.completion.mustNotMatchOutput
import de.moltenKt.paper.tool.display.message.Transmission.Level.*
import kotlinx.coroutines.runBlocking
import java.io.FileNotFoundException

internal class ChangeSkinInterchange : StructuredInterchange("changeskin", protectedAccess = true, userRestriction = ONLY_PLAYERS, structure = buildInterchangeStructure {

	fun tryProcess(executor: InterchangeExecutor, parameters: List<String>, process: suspend () -> Unit): Boolean {
		try {
			runBlocking { process() }
			return true
		} catch (exception: Exception) {

			lang["interchange.internal.changeskin.failed"]
				.replaceVariables("target" to parameters.last())
				.notification(FAIL, executor).display()

		} catch (exception: FileNotFoundException) {

			lang["interchange.internal.changeskin.failed"]
				.replaceVariables("target" to parameters.last())
				.notification(FAIL, executor).display()

		}
		return false
	}

	branch {

		addContent(CompletionComponent.asset(CompletionAsset.ONLINE_PLAYER_NAME))

		branch {
			addContent(CompletionComponent.asset(CompletionAsset.ONLINE_PLAYER_NAME))
			addContent("--reset")

			mustNotMatchOutput()
			ignoreCase()

			execution {
					val target = getInput(0, InterchangeStructureInputRestriction.ONLINE_PLAYER)

					fun notifyTarget() {
						if (executor != target) {
							lang["interchange.internal.changeskin.remote"]
								.notification(INFO, target)
						}
					}

					if (getInput(1).equals("--reset", true)) {

						asSync {
							runBlocking { tryProcess(executor, parameters, target::resetSkin) }
						}

						lang["interchange.internal.changeskin.reset"]
							.replaceVariables("player" to target.name)
							.notification(APPLIED, executor).display()

						notifyTarget()

					} else {
						val skinHolder = getInput(1)

						asSync {
							if (tryProcess(executor, parameters) {
								target.applySkin(skinHolder)
							}) {
								lang["interchange.internal.changeskin.change"]
									.replaceVariables("skin" to parameters.last(), "player" to target.name)
									.notification(APPLIED, executor).display()
							}
						}

						notifyTarget()

					}



				SUCCESS
			}

		}

	}

})