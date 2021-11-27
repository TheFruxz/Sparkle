package de.jet.paper.app.component.feature

import de.jet.jvm.extension.collection.replaceVariables
import de.jet.jvm.extension.math.shorter
import de.jet.paper.app.JetCache.playerMarkerBoxes
import de.jet.paper.extension.display.BOLD
import de.jet.paper.extension.display.YELLOW
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.get
import de.jet.paper.extension.lang
import de.jet.paper.extension.paper.identityObject
import de.jet.paper.extension.paper.templateLocation
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeExecutorType.PLAYER
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.message.Transmission.Level.*
import de.jet.paper.tool.position.LocationBox
import net.kyori.adventure.text.Component
import org.bukkit.FluidCollisionMode.ALWAYS
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

class MarkingFeatureComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "feature/PlayerMarkings"

	override fun component() {
		interchange(MarkingToolInterchange(vendor))
	}

	private class MarkingToolInterchange(vendor: App) :
		Interchange(vendor, "markingtool", requiredExecutorType = PLAYER, requiresAuthorization = true) {

		fun positionData(location: Location) = buildString {
			append('(')
			append("x=${location.x}, y=${location.y}, z=${location.z}")
			append(')')
		}

		val markingTool = Material.GOLDEN_HOE.item.apply {
			label = "${YELLOW}${BOLD}Marking-Tool"
			identity = "jet:marking_tool"
			lore = """
				 
				LEFT-CLICK -> Position-1
				RIGHT-CLICK -> Position-2
				SHIFT-CLICK -> VIEW DATA
				
			""".trimIndent()
			putInteractAction {
				val targetBlock = whoInteract.rayTraceBlocks(10.0, ALWAYS)?.hitBlock
				val actualBox = playerMarkerBoxes[player.identityObject]
				val currentBox = actualBox ?: LocationBox(targetBlock?.location ?: templateLocation)

				if (!whoInteract.isSneaking) {
					if (targetBlock != null) {
						val targetLocation = targetBlock.location
						val targetPrint = positionData(targetBlock.location)
						when {
							action.isLeftClick -> {
								denyInteraction()

								if (actualBox?.first != targetLocation) {

									playerMarkerBoxes[player.identityObject] = currentBox.apply {
										first = targetLocation
									}
									lang["component.markingTool.action.set"].replaceVariables(
										"n" to 1,
										"pos" to targetPrint
									).notification(APPLIED, whoInteract).hover(
										Component.text(
											buildString {
												appendLine(
													lang["component.markingTool.action.view.distance.other"].replaceVariables(
														"distance" to targetLocation.distance(currentBox.last).shorter
													)
												)
												append(
													lang["component.markingTool.action.view.distance.volume"].replaceVariables(
														"volume" to currentBox.blockVolume
													)
												)
											}
										)
									).display()
								} else
									lang["component.markingTool.action.duplicate"].replaceVariables(
										"pos" to targetPrint
									).notification(FAIL, whoInteract).display()
							}
							action.isRightClick -> {
								denyInteraction()

								if (actualBox?.last != targetLocation) {

									playerMarkerBoxes[player.identityObject] = currentBox.apply {
										last = targetLocation
									}
									lang["component.markingTool.action.set"].replaceVariables(
										"n" to 2,
										"pos" to targetPrint
									).notification(APPLIED, whoInteract).hover(
										Component.text(
											buildString {
												appendLine(
													lang["component.markingTool.action.view.distance.other"].replaceVariables(
														"distance" to targetLocation.distance(currentBox.first).shorter
													)
												)
												append(
													lang["component.markingTool.action.view.distance.volume"].replaceVariables(
														"volume" to currentBox.blockVolume
													)
												)
											}
										)
									).display()
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

					if (playerMarkerBoxes[player.identityObject] != null) {
						lang["component.markingTool.action.view.detail"].replaceVariables(
							"1" to positionData(currentBox.first),
							"2" to positionData(currentBox.last),
						).notification(INFO, whoInteract).hover(
							Component.text(
								buildString {
									appendLine(
										lang["component.markingTool.action.view.distance.both"].replaceVariables(
											"distance" to currentBox.distance.shorter
										)
									)
									append(
										lang["component.markingTool.action.view.distance.volume"].replaceVariables(
											"volume" to currentBox.blockVolume
										)
									)
								}
							)
						).display()

					} else
						lang["component.markingTool.action.view.notSet"]
							.notification(FAIL, whoInteract).display()
				}

			}
		}

		override val execution: InterchangeAccess.() -> InterchangeResult = {

			(executor as Player).inventory.addItem(markingTool.produce())

			lang["component.markingTool.interchange.success"]
				.notification(APPLIED, executor).display()

			SUCCESS
		}

		val Item.isMarkingTool: Boolean
			get() = identity == "jet:marking_tool"

	}

}