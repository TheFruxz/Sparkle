package de.moltenKt.paper.app.component.marking

import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.core.extension.math.shorter
import de.moltenKt.core.extension.tryOrNull
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.display.ui.item
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.extension.paper.displayString
import de.moltenKt.paper.extension.paper.identityObject
import de.moltenKt.paper.extension.paper.isPhysical
import de.moltenKt.paper.extension.paper.templateLocation
import de.moltenKt.paper.runtime.event.interact.MoltenPlayerInteractEvent.Companion.denyInteraction
import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent
import de.moltenKt.paper.tool.display.message.Transmission.Level.*
import de.moltenKt.paper.tool.position.dependent.DependentCubicalShape
import de.moltenKt.unfold.extension.asComponent
import de.moltenKt.unfold.extension.asStyledComponent
import de.moltenKt.unfold.extension.asStyledComponents
import de.moltenKt.unfold.text
import net.kyori.adventure.text.format.NamedTextColor.YELLOW
import net.kyori.adventure.text.format.Style.style
import net.kyori.adventure.text.format.TextDecoration.BOLD
import org.bukkit.FluidCollisionMode.ALWAYS
import org.bukkit.Material

internal class MarkingComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Markings"

	override suspend fun component() {

		interchange(MarkingInterchange())

	}

	companion object {

		val markingItem by lazy {

			Material.GOLDEN_HOE.item {

				label = "Marking Tool".asComponent.style(style(YELLOW, BOLD))
				identity = "MoltenKT:markingTool"
				lore = """
				
				<YELLOW>LEFT-CLICK <gray>-> Set #1 Position
				<YELLOW>RIGHT-CLICK <gray>-> Set #2 Position
				<YELLOW>SNEAK-CLICK <gray>-> View Marking
				
			""".trimIndent().asStyledComponents

				onInteract { event ->
					val user = event.whoInteract
					val targetBlock = user.rayTraceBlocks(15.0, ALWAYS)?.hitBlock
					val actualBox = MoltenCache.playerMarkerBoxes[user.identityObject]
					val currentBox = actualBox ?: DependentCubicalShape(targetBlock?.location ?: templateLocation)

					if (user.isSneaking && !event.action.isPhysical) {
						event.denyInteraction()

						if (MoltenCache.playerMarkerBoxes[user.identityObject] != null) {

							lang["component.markingTool.action.view.detail"].replaceVariables(
								"1" to currentBox.firstLocation.displayString(),
								"2" to currentBox.secondLocation.displayString(),
							).asStyledComponent
								.hoverEvent(text {
									text(
										tryOrNull {
											lang["component.markingTool.action.view.distance.both"].replaceVariables(
												"distance" to currentBox.distance.shorter
											)
										} ?: lang["component.markingTool.action.view.distance.acrossWorlds"]
									)
									text(" ")
									text(lang["component.markingTool.action.view.distance.volume"].replaceVariables(
										"volume" to currentBox.blockVolume
									))
								})
								.notification(INFO, user).display()

						} else
							lang["component.markingTool.action.view.notSet"]
								.notification(FAIL, user).display()

					} else {

						if (targetBlock != null) {
							val targetLocation = targetBlock.location.toCenterLocation()
							val targetPrint = targetBlock.location.displayString()

							when {
								event.action.isLeftClick -> {
									event.denyInteraction()

									if (actualBox?.firstLocation != targetLocation) {

										MoltenCache.playerMarkerBoxes += user.identityObject to currentBox.updateFirstLocation(targetLocation)

										lang["component.markingTool.action.set"].replaceVariables(
											"n" to 1,
											"pos" to targetPrint,
										).asStyledComponent
											.hoverEvent(text {
												text(
													tryOrNull {
														lang["component.markingTool.action.view.distance.other"].replaceVariables(
															"distance" to targetLocation.distance(currentBox.secondLocation).shorter
														)
													} ?: lang["component.markingTool.action.view.distance.acrossWorlds"]
												)
											})
											.notification(APPLIED, user).display()

									} else
										lang["component.markingTool.action.duplicate"].replaceVariables(
											"pos" to targetPrint
										).notification(FAIL, user).display()
								}

								event.action.isRightClick -> {
									event.denyInteraction()

									if (actualBox?.secondLocation != targetLocation) {

										MoltenCache.playerMarkerBoxes += user.identityObject to currentBox.updateSecondLocation(targetLocation)

										lang["component.markingTool.action.set"].replaceVariables(
											"n" to 2,
											"pos" to targetPrint,
										).asStyledComponent
											.hoverEvent(text {
												text(
													tryOrNull {
														lang["component.markingTool.action.view.distance.other"].replaceVariables(
															"distance" to targetLocation.distance(currentBox.firstLocation).shorter
														)
													} ?: lang["component.markingTool.action.view.distance.acrossWorlds"]
												)
											})
											.notification(APPLIED, user)
											.display()

									} else
										lang["component.markingTool.action.duplicate"].replaceVariables(
											"pos" to targetPrint
										).notification(FAIL, user).display()
								}
							}

						} else
							lang["component.markingTool.action.wrongLook"]
								.notification(FAIL, user).display()

					}

				}

			}

		}

	}

}