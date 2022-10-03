package de.fruxz.sparkle.server.component.experimental

import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructureInputRestriction
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionComponent
import de.fruxz.sparkle.framework.infrastructure.command.completion.ignoreCase
import de.fruxz.sparkle.framework.infrastructure.command.completion.mustNotMatchOutput
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.util.extension.visual.BOLD
import de.fruxz.sparkle.framework.util.extension.visual.notification
import de.fruxz.sparkle.framework.util.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.util.extension.mojang.applySkin
import de.fruxz.sparkle.framework.util.extension.mojang.resetSkin
import de.fruxz.sparkle.framework.util.extension.coroutines.asSync
import de.fruxz.sparkle.framework.util.visual.message.Transmission.Level.*
import de.fruxz.stacked.extension.dyeGold
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeGreen
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.extension.replace
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.text.format.NamedTextColor
import java.io.FileNotFoundException

internal class ChangeSkinInterchange : StructuredInterchange("changeskin", protectedAccess = true, userRestriction = ONLY_PLAYERS, structure = buildInterchangeStructure {

	val failMessage = text {
		this + text("FAILED").style(NamedTextColor.RED, BOLD)
		this + text(" to get skin of player called '").dyeGray()
		this + text("%player%").dyeYellow()
		this + text("'!").dyeGray()
	}

	fun tryProcess(executor: InterchangeExecutor, parameters: List<String>, process: suspend () -> Unit): Boolean {
		try {
			runBlocking { process() }
			return true
		} catch (exception: Exception) {

			failMessage
				.replace("%player%", parameters.last())
				.notification(FAIL, executor).display()

		} catch (exception: FileNotFoundException) {

			failMessage
				.replace("%player%", parameters.last())
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
							text {
								this + text("A ").dyeGray()
								this + text("remote").dyeYellow()
								this + text(" user applied ").dyeGray()
								this + text("skin changes ").dyeGold()
								this + text("at your profile!").dyeGray()
							}.notification(WARNING, target)
						}
					}

					if (getInput(1).equals("--reset", true)) {

						asSync {
							runBlocking { tryProcess(executor, parameters, target::resetSkin) }
						}

						text {
							this + text("Successfully ").dyeGray()
							this + text("reset").dyeYellow()
							this + text(" the skin of '").dyeGray()
							this + text(target.name).dyeYellow()
							this + text("' back to its default!").dyeGray()
						}.notification(APPLIED, executor).display()

						notifyTarget()

					} else {
						val skinHolder = getInput(1)

						asSync {
							if (tryProcess(executor, parameters) {
								target.applySkin(skinHolder)
							}) {

								text {
									this + text("Successfully ").dyeGreen()
									this + text("applied skin of '").dyeGray()
									this + text(skinHolder).dyeYellow()
									this + text("' to '").dyeGray()
									this + text(target.name).dyeYellow()
									this + text("'!").dyeGray()
								}.notification(APPLIED, executor).display()

							}
						}

						notifyTarget()

					}

				SUCCESS
			}

		}

	}

})