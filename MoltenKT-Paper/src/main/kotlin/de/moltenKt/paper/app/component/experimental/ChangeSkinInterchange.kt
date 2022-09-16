package de.moltenKt.paper.app.component.experimental

import de.moltenKt.paper.extension.display.BOLD
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.mojang.applySkin
import de.moltenKt.paper.extension.mojang.resetSkin
import de.moltenKt.paper.extension.tasky.asSync
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.component.CompletionComponent
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.structure.command.completion.mustNotMatchOutput
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.tool.display.message.Transmission.Level.*
import de.moltenKt.unfold.extension.dyeGold
import de.moltenKt.unfold.extension.dyeGray
import de.moltenKt.unfold.extension.dyeGreen
import de.moltenKt.unfold.extension.dyeYellow
import de.moltenKt.unfold.extension.replace
import de.moltenKt.unfold.extension.style
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text
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