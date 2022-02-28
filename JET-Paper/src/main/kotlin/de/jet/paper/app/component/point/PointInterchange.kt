package de.jet.paper.app.component.point

import de.jet.jvm.extension.container.mapToString
import de.jet.jvm.extension.container.page
import de.jet.jvm.extension.container.replaceVariables
import de.jet.jvm.extension.math.ceilToInt
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.paper.app.JetData
import de.jet.paper.app.component.point.asset.Point
import de.jet.paper.app.component.point.asset.PointConfig
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.getSystemTranslated
import de.jet.paper.extension.interchange.InterchangeExecutor
import de.jet.paper.extension.lang
import de.jet.paper.extension.system
import de.jet.paper.extension.tasky.sync
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.jet.paper.structure.command.StructuredInterchange
import de.jet.paper.structure.command.completion.InterchangeStructureInputRestriction
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.component.CompletionComponent.Companion
import de.jet.paper.structure.command.completion.ignoreCase
import de.jet.paper.structure.command.completion.isNotRequired
import de.jet.paper.structure.command.completion.mustNotMatchOutput
import de.jet.paper.tool.display.message.Transmission.Level.*
import org.bukkit.entity.Player

internal class PointInterchange : StructuredInterchange("point", protectedAccess = true, userRestriction = ONLY_PLAYERS, structure = buildInterchangeStructure {

	branch {
		addContent("list")

		ignoreCase()

		fun displayServices(executor: InterchangeExecutor, page: Int) {
			val pageValue = JetData.savedPoints.content.points.page(page, 6)

			if (pageValue.content.isNotEmpty()) {
				buildString {

					appendLine(
						lang("interchange.internal.essentials.point.list.header").replaceVariables(
							"p1" to pageValue.page + 1,
							"p2" to pageValue.pages,
						)
					)

					pageValue.content.withIndex().forEach {
						val point = it.value

						lang("interchange.internal.essentials.point.list.entry").replaceVariables(
							"point" to point.identity,
							"x" to point.bukkitLocation.blockX,
							"y" to point.bukkitLocation.blockY,
							"z" to point.bukkitLocation.blockZ,
						).let(::appendLine)

					}

				}.notification(INFO, executor).display()
			} else
				lang("interchange.internal.essentials.point.list.empty")
					.notification(FAIL, executor).display()
		}

		concludedExecution {

			displayServices(executor, 0)

			SUCCESS
		}

		branch {
			addContent(CompletionAsset(
				vendor = system,
				thisIdentity = "Page",
				true,
				listOf(InterchangeStructureInputRestriction.LONG),
				generator = {
					(1..ceilToInt(JetData.savedPoints.content.points.size.toDouble() / 6)).mapToString()
				},
			))

			isNotRequired()

			execution {
				val page = getInput(1, InterchangeStructureInputRestriction.LONG).toInt() - 1

				if (page >= 0) {
					displayServices(executor, page)
				} else
					return@execution WRONG_USAGE

				return@execution SUCCESS
			}

		}

	}

	branch {

		addContent("at", "@")

		ignoreCase()

		branch {

			addContent("...")
			addContent(CompletionAsset.POINT)

			mustNotMatchOutput()

			branch {

				addContent("create")

				ignoreCase()

				concludedExecution {
					val points = JetData.savedPoints.content.points.toMutableList()
					val pointName = getInput(1)
					val point = points.firstOrNull { it.identity == pointName }

					executor as Player

					if (point == null) {

						JetData.savedPoints.content =
							PointConfig(points + Point(pointName, executor.location))

						getSystemTranslated(system,
							Address.address("interchange.internal.essentials.point.edit.created")
						)
							.replaceVariables("point" to pointName)
							.notification(APPLIED, executor).display()

					} else
						getSystemTranslated(
							system,
							Address.address("interchange.internal.essentials.point.edit.exists")
						)
							.replaceVariables("point" to pointName)
							.notification(FAIL, executor).display()

				}

			}

			branch {

				addContent("delete")

				ignoreCase()

				concludedExecution {
					val points = JetData.savedPoints.content.points.toMutableList()
					val point = points.firstOrNull { it.identity == getInput(1) }

					if (point != null) {

						points.removeAll { it.identity.equals(parameters.dropLast(1).last(), true) }

						JetData.savedPoints.content = PointConfig(points)

						getSystemTranslated(system, Address.address("interchange.internal.essentials.point.edit.removed"))
							.replaceVariables("point" to parameters.last())
							.notification(APPLIED, executor).display()

					} else
						getSystemTranslated(system, Address.address("interchange.internal.essentials.point.edit.notFound"))
							.replaceVariables("point" to parameters.last())
							.notification(FAIL, executor).display()

				}

			}

			branch {

				addContent("teleport")

				ignoreCase()

				concludedExecution {
					val points = JetData.savedPoints.content.points.toMutableList()
					val point = points.firstOrNull { it.identity == getInput(1) }

					executor as Player

					if (point != null) {

						sync { executor.teleport(point.bukkitLocation) }

						getSystemTranslated(
							system,
							Address.address("interchange.internal.essentials.point.edit.teleportedSelf")
						)
							.replaceVariables("point" to parameters.last())
							.notification(APPLIED, executor).display()

					} else
						getSystemTranslated(system,
							Address.address("interchange.internal.essentials.point.edit.notFound")
						)
							.replaceVariables("point" to parameters.last())
							.notification(FAIL, executor).display()

				}

			}

		}

	}

})