package de.jet.paper.app.interchange.player

import de.jet.jvm.extension.collection.replaceVariables
import de.jet.jvm.extension.collection.second
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.get
import de.jet.paper.extension.lang
import de.jet.paper.extension.mojang.applySkin
import de.jet.paper.extension.mojang.resetSkin
import de.jet.paper.extension.paper.getPlayer
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.CompletionVariable
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeExecutorType.PLAYER
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.buildCompletion
import de.jet.paper.structure.command.isRequired
import de.jet.paper.structure.command.label
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.structure.command.mustMatchOutput
import de.jet.paper.structure.command.next
import de.jet.paper.structure.command.plus
import de.jet.paper.tool.display.message.Transmission.Level.*
import org.bukkit.entity.Player

class ChangeSkinInterchange(vendor: App = system) : Interchange(
	vendor = vendor,
	label = "changeskin",
	requiresAuthorization = true,
	requiredExecutorType = PLAYER,
	completion = buildCompletion {
		next(CompletionVariable.PLAYER_NAME) isRequired true mustMatchOutput true
		next("/reset") plus CompletionVariable.PLAYER_NAME isRequired true mustMatchOutput false label "skin-player-name"
	},

) {
	override val execution: InterchangeAccess.() -> InterchangeResult = {
		val player = executor as Player
		val target = getPlayer(parameters.first())

		fun notifyTarget() {
			if (player != target) {
				target?.let {
					lang["interchange.internal.changeskin.remote"]
						.notification(INFO, it).display()
				}
			}
		}

		if (target != null) {

			when (parameters.second.uppercase()) {
				"/RESET" -> {

					executor.resetSkin()

					lang["interchange.internal.changeskin.reset"]
						.replaceVariables("player" to target.name)
						.notification(APPLIED, executor).display()

					notifyTarget()

				}
				else -> {

					try {

						executor.applySkin(parameters.last())

						lang["interchange.internal.changeskin.change"]
							.replaceVariables("skin" to parameters.last(), "player" to target.name)
							.notification(APPLIED, executor).display()

						notifyTarget()

					} catch (exception: Exception) {

						lang["interchange.internal.changeskin.failed"]
							.replaceVariables("target" to parameters.last())
							.notification(FAIL, executor).display()

					}

				}
			}

		}

		SUCCESS
	}

}