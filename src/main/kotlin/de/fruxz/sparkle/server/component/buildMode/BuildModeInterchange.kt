package de.fruxz.sparkle.server.component.buildMode

import de.fruxz.ascend.extension.container.page
import de.fruxz.ascend.extension.container.toUUID
import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.switchResult
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.framework.Constants
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult.WRONG_USAGE
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructureInputRestriction
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionComponent
import de.fruxz.sparkle.framework.infrastructure.command.completion.ignoreCase
import de.fruxz.sparkle.framework.infrastructure.command.completion.isNotRequired
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.util.extension.visual.BOLD
import de.fruxz.sparkle.framework.util.extension.visual.notification
import de.fruxz.sparkle.framework.util.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.util.extension.entity.buildMode
import de.fruxz.sparkle.framework.util.extension.offlinePlayer
import de.fruxz.sparkle.framework.util.extension.onlinePlayers
import de.fruxz.sparkle.framework.util.visual.message.Transmission.Level.*
import de.fruxz.stacked.extension.dyeGold
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeGreen
import de.fruxz.stacked.extension.dyeRed
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
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
				val pageValue = SparkleCache.buildModePlayers.page(page, Constants.ENTRIES_PER_PAGE)

				if (pageValue.content.isNotEmpty()) {

					text {
						this + text("These players have Build-Mode enabled: ").dyeGray()
						this + text("(Page ${pageValue.pageNumber} of ${pageValue.availablePages.last})").dyeYellow()

						pageValue.content.withIndex().forEach {
							val player = offlinePlayer(it.value.identity.toUUID())

							this + Component.newline()
							this + text(">- ").dyeGray()
							this + text(player.name ?: "Unknown").style(BOLD).let { output ->
								if (player.isOnline) output.dyeGreen() else output.dyeGray()
							}

						}
					}.notification(GENERAL, executor).display()

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
				addContent(CompletionAsset.pageCompletion { ceilToInt(SparkleCache.buildModePlayers.size.toDouble() / Constants.ENTRIES_PER_PAGE) })

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
						}.notification(GENERAL, executor).display()

					}

				}

			}

		}

	}
)