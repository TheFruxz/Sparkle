package de.fruxz.sparkle.app.component.point

import de.fruxz.ascend.extension.container.page
import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.sparkle.app.SparkleData
import de.fruxz.sparkle.app.component.point.asset.Point
import de.fruxz.sparkle.app.component.point.asset.PointConfig
import de.fruxz.sparkle.extension.display.notification
import de.fruxz.sparkle.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.extension.tasky.asSync
import de.fruxz.sparkle.structure.command.InterchangeResult.SUCCESS
import de.fruxz.sparkle.structure.command.InterchangeResult.WRONG_USAGE
import de.fruxz.sparkle.structure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.fruxz.sparkle.structure.command.completion.InterchangeStructureInputRestriction
import de.fruxz.sparkle.structure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.structure.command.completion.component.CompletionAsset
import de.fruxz.sparkle.structure.command.completion.ignoreCase
import de.fruxz.sparkle.structure.command.completion.isNotRequired
import de.fruxz.sparkle.structure.command.completion.mustNotMatchOutput
import de.fruxz.sparkle.structure.command.structured.StructuredInterchange
import de.fruxz.sparkle.tool.display.message.Transmission.Level.*
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.extension.dyeRed
import de.fruxz.stacked.extension.dyeYellow
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import net.kyori.adventure.text.Component.newline
import org.bukkit.entity.Player

internal class PointInterchange : StructuredInterchange("point", protectedAccess = true, userRestriction = ONLY_PLAYERS, structure = buildInterchangeStructure {

	branch {
		addContent("list")

		ignoreCase()

		fun displayServices(executor: InterchangeExecutor, page: Int) {
			val pageValue = SparkleData.savedPoints.content.points.page(page, de.fruxz.sparkle.Constants.ENTRIES_PER_PAGE)

			if (pageValue.content.isNotEmpty()) {

				text {
					this + text("List of all saved points: ").dyeGray()
					this + text("(Page $page of ${pageValue.availablePages.last})").dyeYellow()
					this + newline() + newline()

					pageValue.content.forEach {
						this + text {
							this + text(it.identity).dyeYellow()
							this + text(" - ")
							this + text("X: ${it.bukkitLocation.blockX} Y: ${it.bukkitLocation.blockY} Z: ${it.bukkitLocation.blockZ}").dyeGray()
						}
					}

				}.notification(GENERAL, executor).display()

			} else {

				text {
					this + text("There are currently ").dyeGray()
					this + text("no points").dyeRed()
					this + text(" saved!").dyeGray()
				}.notification(FAIL, executor).display()

			}
		}

		concludedExecution {

			displayServices(executor, 0)

			SUCCESS
		}

		branch {
			addContent(CompletionAsset.PAGES { ceilToInt(SparkleData.savedPoints.content.points.size.toDouble() / de.fruxz.sparkle.Constants.ENTRIES_PER_PAGE) })

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
					val points = SparkleData.savedPoints.content.points.toMutableList()
					val pointName = getInput(1)
					val point = points.firstOrNull { it.identity == pointName }

					executor as Player

					if (point == null) {

						SparkleData.savedPoints.content =
							PointConfig(points + Point(pointName, executor.location))

						text {
							this + text("A new point called '").dyeGray()
							this + text(pointName).dyeYellow()
							this + text("' has been created!").dyeGray()
						}.notification(APPLIED, executor).display()

					} else {

						text {
							this + text("A point called '").dyeGray()
							this + text(pointName).dyeYellow()
							this + text("' already exists!").dyeGray()
						}.notification(FAIL, executor).display()

					}

				}

			}

			branch {

				addContent("delete")

				ignoreCase()

				concludedExecution {
					val points = SparkleData.savedPoints.content.points.toMutableList()
					val point = points.firstOrNull { it.identity == getInput(1) }

					if (point != null) {

						points.removeAll { it.identity.equals(parameters.dropLast(1).last(), true) }

						SparkleData.savedPoints.content = PointConfig(points)

						text {
							this + text("The point called '").dyeGray()
							this + text(point.identity).dyeYellow()
							this + text("' has been removed!").dyeGray()
						}.notification(APPLIED, executor).display()

					} else {

						text {
							this + text("The point called '").dyeGray()
							this + text(getInput(1)).dyeYellow()
							this + text("' does not exist!").dyeGray()
						}.notification(FAIL, executor).display()

					}

				}

			}

			branch {

				addContent("teleport")

				ignoreCase()

				concludedExecution {
					val points = SparkleData.savedPoints.content.points.toMutableList()
					val point = points.firstOrNull { it.identity == getInput(1) }

					executor as Player

					if (point != null) {

						asSync { executor.teleport(point.bukkitLocation) }

						text {
							this + text("You have been teleported to the point called '").dyeGray()
							this + text(point.identity).dyeYellow()
							this + text("'!").dyeGray()
						}.notification(APPLIED, executor).display()

					} else {

						text {
							this + text("The point called '").dyeGray()
							this + text(getInput(1)).dyeYellow()
							this + text("' does not exist!").dyeGray()
						}.notification(FAIL, executor).display()

					}

				}

			}

		}

	}

})