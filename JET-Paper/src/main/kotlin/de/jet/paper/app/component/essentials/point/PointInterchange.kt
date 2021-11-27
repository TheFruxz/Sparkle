package de.jet.paper.app.component.essentials.point

import de.jet.jvm.extension.collection.replaceVariables
import de.jet.jvm.tool.smart.positioning.Address.Companion.address
import de.jet.paper.app.JetData
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.getSystemTranslated
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.CompletionVariable
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeExecutorType.PLAYER
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.buildCompletion
import de.jet.paper.structure.command.isRequired
import de.jet.paper.structure.command.mustMatchOutput
import de.jet.paper.structure.command.next
import de.jet.paper.tool.display.message.Transmission.Level.*
import org.bukkit.entity.Player

class PointInterchange(vendor: App) : Interchange(
	vendor,
	"point",
	requiresAuthorization = true,
	requiredExecutorType = PLAYER,
	completion = buildCompletion {
		next("create", "delete", "list", "teleport", "teleportAll") isRequired true mustMatchOutput true
		next(CompletionVariable(vendor, "<Point>", true, { input, ignoreCase ->
			JetData.savedPoints.content.points.any { it.identity.equals(input, ignoreCase) }
		}) {
			JetData.savedPoints.content.points.map(Point::identity)
		}) isRequired false mustMatchOutput false
	}) {

	override val execution = execution {
		val points = JetData.savedPoints.content.points.toMutableList()

		executor as Player

		when {

			parameters.size == 1 && parameters.first().equals("list", true) -> {

				if (points.isNotEmpty()) {
					buildString {
						appendLine(
							getSystemTranslated(
								system,
								address("interchange.internal.essentials.point.list.header")
							).replaceVariables("amount" to points.size)
						)
						points.map(Point::identity).forEach { identity ->
							append(
								getSystemTranslated(
									system,
									address("interchange.internal.essentials.point.list.stash")
								).replaceVariables("amount" to points.size).replaceVariables("point-array" to identity) + ", "
							)
						}
					}.removeSuffix(", ").notification(INFO, executor).display()
				} else
					getSystemTranslated(system, address("interchange.internal.essentials.point.list.empty"))
						.notification(FAIL, executor).display()

			}

			parameters.size == 2 -> {

				val searchedPoint =
					JetData.savedPoints.content.points.firstOrNull { it.identity.equals(parameters.last(), true) }

				when (parameters.first().lowercase()) {

					"delete" -> {

						if (searchedPoint != null) {

							points.removeAll { it.identity.equals(parameters.last(), true) }

							JetData.savedPoints.content = PointConfig(points)

							getSystemTranslated(system, address("interchange.internal.essentials.point.edit.removed"))
								.replaceVariables("point" to parameters.last())
								.notification(APPLIED, executor).display()

						} else
							getSystemTranslated(system, address("interchange.internal.essentials.point.edit.notFound"))
								.replaceVariables("point" to parameters.last())
								.notification(FAIL, executor).display()

					}

					"teleport" -> {

						if (searchedPoint != null) {

							executor.teleport(searchedPoint.bukkitLocation)

							getSystemTranslated(
								system,
								address("interchange.internal.essentials.point.edit.teleportedSelf")
							)
								.replaceVariables("point" to parameters.last())
								.notification(APPLIED, executor).display()

						} else
							getSystemTranslated(system, address("interchange.internal.essentials.point.edit.notFound"))
								.replaceVariables("point" to parameters.last())
								.notification(FAIL, executor).display()

					}

					"teleportAll" -> {

						if (searchedPoint != null) {

							executor.teleport(searchedPoint.bukkitLocation)

							getSystemTranslated(
								system,
								address("interchange.internal.essentials.point.edit.teleportedAll")
							)
								.replaceVariables("point" to parameters.last())
								.notification(APPLIED, executor).display()

						} else
							getSystemTranslated(system, address("interchange.internal.essentials.point.edit.notFound"))
								.replaceVariables("point" to parameters.last())
								.notification(FAIL, executor).display()

					}

					"create" -> {

						if (searchedPoint == null) {

							JetData.savedPoints.content =
								PointConfig(points + Point(parameters.last(), executor.location))

							getSystemTranslated(system, address("interchange.internal.essentials.point.edit.created"))
								.replaceVariables("point" to parameters.last())
								.notification(APPLIED, executor).display()

						} else
							getSystemTranslated(
								system,
								address("interchange.internal.essentials.point.edit.exists")
							)
								.replaceVariables("point" to parameters.last())
								.notification(FAIL, executor).display()

					}

					else -> return@execution WRONG_USAGE

				}

			}

			else -> return@execution WRONG_USAGE

		}

		SUCCESS
	}

}