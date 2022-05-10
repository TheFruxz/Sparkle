package de.moltenKt.paper.app.component.marking

import de.moltenKt.core.extension.container.replaceVariables
import de.moltenKt.core.extension.math.shorter
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.display.ui.item
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.extension.paper.identityObject
import de.moltenKt.paper.extension.paper.templateLocation
import de.moltenKt.paper.extension.tasky.sync
import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.ignoreCase
import de.moltenKt.paper.tool.display.message.Transmission.Level.APPLIED
import de.moltenKt.paper.tool.display.message.Transmission.Level.FAIL
import de.moltenKt.paper.tool.position.CubicalShape
import de.moltenKt.unfold.extension.asStyledComponent
import de.moltenKt.unfold.newline
import de.moltenKt.unfold.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.FluidCollisionMode.ALWAYS
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

internal class MarkingInterchange : StructuredInterchange(
	label = "markings",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		branch {

			fun positionData(location: Location) = buildString {
				append('(')
				append("x=${location.x}, y=${location.y}, z=${location.z}")
				append(')')
			}

			fun produceMarkingItem() = Material.GOLDEN_HOE.item.apply {
				label = text("Marking-Tool").style(Style.style(NamedTextColor.YELLOW, TextDecoration.BOLD))
				identity = "MoltenKT:marking_tool"
				lore = listOf(
					Component.empty(),
					text("LEFT-CLICK -> Position-1"),
					text("RIGHT-CLICK -> Position-2"),
					text("SHIFT-CLICK -> VIEW DATA"),
					Component.empty(),
				)

				@Suppress("UnnecessaryOptInAnnotation")
				onInteractWith {
					val targetBlock = whoInteract.rayTraceBlocks(10.0, ALWAYS)?.hitBlock
					val actualBox = MoltenCache.playerMarkerBoxes[player.identityObject]
					val currentBox = actualBox ?: CubicalShape(targetBlock?.location ?: templateLocation)

					if (!whoInteract.isSneaking) {
						if (targetBlock != null) {
							val targetLocation = targetBlock.location
							val targetPrint = positionData(targetBlock.location)
							when {
								action.isLeftClick -> {
									denyInteraction()

									if (actualBox?.firstLocation != targetLocation) {

										MoltenCache.playerMarkerBoxes += player.identityObject to currentBox.updateFirstLocation(targetLocation)

										lang["component.markingTool.action.set"].replaceVariables(
											"n" to 1,
											"pos" to targetPrint,
										).asStyledComponent
											.hoverEvent(text {
												text(lang["component.markingTool.action.view.distance.other"].replaceVariables(
													"distance" to targetLocation.distance(currentBox.secondLocation).shorter
												))
												newline()
												text(lang["component.markingTool.action.view.distance.volume"].replaceVariables(
													"volume" to currentBox.blockVolume
												))
											})
											.notification(APPLIED, whoInteract)
											.display()

									} else
										lang["component.markingTool.action.duplicate"].replaceVariables(
											"pos" to targetPrint
										).notification(FAIL, whoInteract).display()
								}
								action.isRightClick -> {
									denyInteraction()

									if (actualBox?.secondLocation != targetLocation) {

										MoltenCache.playerMarkerBoxes += player.identityObject to currentBox.updateSecondLocation(targetLocation)

										lang["component.markingTool.action.set"].replaceVariables(
											"n" to 2,
											"pos" to targetPrint,
										).asStyledComponent
											.hoverEvent(text {
												text(lang["component.markingTool.action.view.distance.other"].replaceVariables(
													"distance" to targetLocation.distance(currentBox.firstLocation).shorter
												))
												newline()
												text(lang["component.markingTool.action.view.distance.volume"].replaceVariables(
													"volume" to currentBox.blockVolume
												))
											})
											.notification(APPLIED, whoInteract)
											.display()

									} else
										lang["component.markingTool.action.duplicate"].replaceVariables(
											"pos" to targetPrint
										).notification(FAIL, whoInteract).display()
								}
							}

						} else
							lang["component.markingTool.action.wrongLook"]
								.notification(FAIL, whoInteract).display()

					} else {

						if (MoltenCache.playerMarkerBoxes[player.identityObject] != null) {

							lang["component.markingTool.action.view.detail"].replaceVariables(
								"1" to positionData(currentBox.firstLocation),
								"2" to positionData(currentBox.secondLocation),
							).asStyledComponent
								.hoverEvent(text {
									text(lang["component.markingTool.action.view.distance.both"].replaceVariables(
										"distance" to currentBox.distance.shorter
									))
									newline()
									text(lang["component.markingTool.action.view.distance.volume"].replaceVariables(
										"volume" to currentBox.blockVolume
									))
								})

						} else
							lang["component.markingTool.action.view.notSet"]
								.notification(FAIL, whoInteract).display()
					}

				}

			}

			addContent("giveItem")

			ignoreCase()

			concludedExecution {

				sync { (executor as Player).inventory.addItem(produceMarkingItem().produce()) }

				lang["component.markingTool.interchange.success"]
					.notification(APPLIED, executor).display()

			}

		}

	}
)