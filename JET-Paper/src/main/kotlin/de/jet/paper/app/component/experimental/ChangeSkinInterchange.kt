package de.jet.paper.app.component.experimental

import de.jet.jvm.extension.collection.replaceVariables
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.get
import de.jet.paper.extension.lang
import de.jet.paper.extension.mojang.applySkin
import de.jet.paper.extension.mojang.resetSkin
import de.jet.paper.structure.command.BranchedInterchange
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.jet.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.ignoreCase
import de.jet.paper.structure.command.completion.mustNotMatchOutput
import de.jet.paper.tool.display.message.Transmission.Level.*

internal class ChangeSkinInterchange : BranchedInterchange("changeskin", protectedAccess = true, userRestriction = ONLY_PLAYERS, structure = buildInterchangeStructure {

	branch {

		addContent(CompletionComponent.asset(CompletionAsset.ONLINE_PLAYER_NAME))

		branch {
			addContent(CompletionComponent.asset(CompletionAsset.ONLINE_PLAYER_NAME))
			addContent(CompletionComponent.static("--reset"))

			mustNotMatchOutput()
			ignoreCase()

			execution {
				try {
					val target = getInput(0, InterchangeStructureInputRestriction.ONLINE_PLAYER)

					fun notifyTarget() {
						if (executor != target) {
							lang["interchange.internal.changeskin.remote"]
								.notification(INFO, target)
						}
					}

					if (getInput(1).equals("--reset", true)) {

						target.resetSkin()

						lang["interchange.internal.changeskin.reset"]
							.replaceVariables("player" to target.name)
							.notification(APPLIED, executor).display()

						notifyTarget()

					} else {
						val skinHolder = getInput(1)

						target.applySkin(skinHolder)

						lang["interchange.internal.changeskin.change"]
							.replaceVariables("skin" to parameters.last(), "player" to target.name)
							.notification(APPLIED, executor).display()

						notifyTarget()

					}

				} catch (exception: Exception) {

					lang["interchange.internal.changeskin.failed"]
						.replaceVariables("target" to parameters.last())
						.notification(FAIL, executor).display()

				}

				SUCCESS
			}

		}

	}

}) {
}