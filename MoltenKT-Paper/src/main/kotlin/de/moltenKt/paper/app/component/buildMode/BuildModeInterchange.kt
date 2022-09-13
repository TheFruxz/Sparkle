package de.moltenKt.paper.app.component.buildMode

import de.moltenKt.core.extension.container.page
import de.moltenKt.core.extension.container.toUUID
import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.switchResult
import de.moltenKt.paper.Constants
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.BOLD
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.paper.buildMode
import de.moltenKt.paper.extension.paper.offlinePlayer
import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.component.CompletionComponent
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.structure.command.completion.isNotRequired
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.tool.display.message.Transmission.Level.*
import de.moltenKt.unfold.extension.dyeGold
import de.moltenKt.unfold.extension.dyeGray
import de.moltenKt.unfold.extension.dyeGreen
import de.moltenKt.unfold.extension.dyeRed
import de.moltenKt.unfold.extension.dyeYellow
import de.moltenKt.unfold.extension.style
import de.moltenKt.unfold.plus
import de.moltenKt.unfold.text
import net.kyori.adventure.text.Component
import org.bukkit.OfflinePlayer

internal class BuildModeInterchange : StructuredInterchange(
	label = "buildmode",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		fun enableBuildMode(executor: InterchangeExecutor, target: OfflinePlayer) {
			if (!target.buildMode) {
				target.buildMode = true

				text {
					this + text("The ").dyeGray()
					this + text("Build-Mode").dyeGold()
					this + text(" of the Player '").dyeGray()
					this + text(target.name ?: "Unknown").dyeYellow()
					this + text("' has been ").dyeGray()
					this + text("enabled").dyeGreen()
					this + text("!").dyeGray()
				}.notification(APPLIED, executor).display()

			} else {
				text {
					this + text("The ").dyeGray()
					this + text("Build-Mode").dyeGold()
					this + text(" of the Player '").dyeGray()
					this + text(target.name ?: "Unknown").dyeYellow()
					this + text("' is already ").dyeGray()
					this + text("enabled").dyeGreen()
					this + text("!").dyeGray()
				}.notification(FAIL, executor).display()
			}
		}

		fun disableBuildMode(executor: InterchangeExecutor, target: OfflinePlayer) {
			if (target.buildMode) {
				target.buildMode = false

				text {
					this + text("The ").dyeGray()
					this + text("Build-Mode").dyeGold()
					this + text(" of the Player '").dyeGray()
					this + text(target.name ?: "Unknown").dyeYellow()
					this + text("' has been ").dyeGray()
					this + text("disabled").dyeGreen()
					this + text("!").dyeGray()
				}.notification(APPLIED, executor).display()

			} else {

				text {
					this + text("The ").dyeGray()
					this + text("Build-Mode").dyeGold()
					this + text(" of the Player '").dyeGray()
					this + text(target.name ?: "Unknown").dyeYellow()
					this + text("' is already ").dyeGray()
					this + text("disabled").dyeGreen()
					this + text("!").dyeGray()
				}.notification(FAIL, executor).display()

			}
		}

		fun toggleBuildMode(executor: InterchangeExecutor, target: OfflinePlayer) {

			when (target.buildMode) {
				true -> disableBuildMode(executor, target)
				false -> enableBuildMode(executor, target)
			}

		}

		branch {
			addContent("list")

			ignoreCase()

			fun displayPlayers(executor: InterchangeExecutor, page: Int) {
				val pageValue = MoltenCache.buildModePlayers.page(page, Constants.ENTRIES_PER_PAGE)

				if (pageValue.content.isNotEmpty()) {

					text {
						this + text("These players have Build-Mode enabled: ").dyeGray()
						this + text("(Page ${pageValue.page + 1} of ${pageValue.pages})").dyeYellow()

						pageValue.content.withIndex().forEach {
							val player = offlinePlayer(it.value.identity.toUUID())

							this + Component.newline()
							this + text(">- ").dyeGray()
							this + text(player.name ?: "Unknown").style(BOLD).let { output ->
								if (player.isOnline) output.dyeGreen() else output.dyeGray()
							}

						}
					}.notification(INFO, executor).display()

				} else {

					text {
						this + text("There are currently ").dyeGray()
						this + text("no players").dyeRed()
						this + text(" with Build-Mode enabled!").dyeGray()
					}.notification(FAIL, executor).display()

				}
			}

			concludedExecution {

				displayPlayers(executor, 0)

				SUCCESS
			}

			branch {
				addContent(CompletionAsset.PAGES { ceilToInt(MoltenCache.buildModePlayers.size.toDouble() / Constants.ENTRIES_PER_PAGE) })

				isNotRequired()

				execution {
					val page = getInput(1, InterchangeStructureInputRestriction.LONG).toInt() - 1

					if (page >= 0) {
						displayPlayers(executor, page)
					} else
						return@execution WRONG_USAGE

					return@execution SUCCESS
				}

			}

		}

		branch {

			addContent("enableAll")

			ignoreCase()

			concludedExecution {

				onlinePlayers.forEach { enableBuildMode(executor, it) }

			}

		}

		branch {

			addContent("disableAll")

			ignoreCase()

			concludedExecution {

				onlinePlayers.forEach { disableBuildMode(executor, it) }

			}

		}

		branch {

			addContent("toggleAll")

			ignoreCase()

			concludedExecution {

				onlinePlayers.forEach { toggleBuildMode(executor, it) }

			}

		}

		branch {

			addContent("at", "@")

			ignoreCase()

			branch {

				addContent(CompletionComponent.asset(CompletionAsset.OFFLINE_PLAYER_NAME))

				branch {

					addContent("enable")

					ignoreCase()

					concludedExecution {
						val target = getInput(1, InterchangeStructureInputRestriction.OFFLINE_PLAYER)

						enableBuildMode(executor, target)

					}

				}

				branch {

					addContent("disable")

					ignoreCase()

					concludedExecution {
						val target = getInput(1, InterchangeStructureInputRestriction.OFFLINE_PLAYER)

						disableBuildMode(executor, target)

					}

				}

				branch {

					addContent("toggle")

					ignoreCase()

					concludedExecution {
						val target = getInput(1, InterchangeStructureInputRestriction.OFFLINE_PLAYER)

						toggleBuildMode(executor, target)

					}

				}

				branch {

					addContent("info")

					ignoreCase()

					concludedExecution {
						val target = getInput(1, InterchangeStructureInputRestriction.OFFLINE_PLAYER)

						text {
							this + text("Build mode of the Player '").dyeGray()
							this + text(target.name ?: "Unknown").dyeYellow()
							this + text("' is ").dyeGray()
							this + text(target.buildMode.switchResult("enabled", "disabled")).dyeGreen()
							this + text("!").dyeGray()
						}.notification(INFO, executor).display()

					}

				}

			}

		}

	}
)