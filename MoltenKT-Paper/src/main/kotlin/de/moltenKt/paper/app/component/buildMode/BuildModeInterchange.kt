package de.moltenKt.paper.app.component.buildMode

import de.moltenKt.core.extension.container.page
import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.core.extension.container.toUUID
import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.switchResult
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.extension.paper.buildMode
import de.moltenKt.paper.extension.paper.offlinePlayer
import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset
import de.moltenKt.paper.structure.command.completion.component.CompletionComponent
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.structure.command.completion.isNotRequired
import de.moltenKt.paper.tool.display.message.Transmission.Level.*
import org.bukkit.OfflinePlayer

internal class BuildModeInterchange : StructuredInterchange(
	label = "buildmode",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		fun enableBuildMode(executor: InterchangeExecutor, target: OfflinePlayer) {
			if (!target.buildMode) {
				target.buildMode = true
				lang["interchange.internal.buildmode.enable"]
					.replaceVariables("player" to target.name)
					.notification(APPLIED, executor).display()
			} else
				lang["interchange.internal.buildmode.stay"]
					.replaceVariables("player" to target.name)
					.notification(FAIL, executor).display()
		}

		fun disableBuildMode(executor: InterchangeExecutor, target: OfflinePlayer) {
			if (target.buildMode) {
				target.buildMode = false
				lang["interchange.internal.buildmode.disable"]
					.replaceVariables("player" to target.name)
					.notification(APPLIED, executor).display()
			} else
				lang["interchange.internal.buildmode.stay"]
					.replaceVariables("player" to target.name)
					.notification(FAIL, executor).display()
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
				val pageValue = MoltenCache.buildModePlayers.page(page, 6)

				if (pageValue.content.isNotEmpty()) {
					buildString {

						appendLine(
							lang["interchange.internal.buildmode.list.header"].replaceVariables(
								"p1" to pageValue.page + 1,
								"p2" to pageValue.pages,
							)
						)

						pageValue.content.withIndex().forEach {
							val player = offlinePlayer(it.value.identity.toUUID())

							lang["interchange.internal.buildmode.list.line"].replaceVariables(
								"player" to player.name,
								"statusColor" to player.isOnline.switchResult("<green>", "<gray>")
							).let(::appendLine)

						}

					}.notification(INFO, executor).display()
				} else
					lang["interchange.internal.buildmode.list.empty"]
						.notification(FAIL, executor).display()
			}

			concludedExecution {

				displayPlayers(executor, 0)

				SUCCESS
			}

			branch {
				addContent(CompletionAsset.PAGES { ceilToInt(MoltenCache.buildModePlayers.size.toDouble() / 6) })

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

						lang["interchange.internal.buildmode.info"].replaceVariables(
							"state" to target.buildMode.switchResult("<green><bold>enabled</bold>", "<red><bold>disabled</bold>"),
							"player" to target.name
						).notification(INFO, executor).display()

					}

				}

			}

		}

	}
)