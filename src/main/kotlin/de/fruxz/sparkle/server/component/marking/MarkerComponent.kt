package de.fruxz.sparkle.server.component.marking

import dev.fruxz.ascend.extension.math.round
import dev.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.effect.particle.ParticleType
import de.fruxz.sparkle.framework.event.interact.SparklePlayerInteractEvent.Companion.denyInteraction
import de.fruxz.sparkle.framework.extension.component
import de.fruxz.sparkle.framework.extension.effect.offset
import de.fruxz.sparkle.framework.extension.effect.particleOf
import de.fruxz.sparkle.framework.extension.entity.identityObject
import de.fruxz.sparkle.framework.extension.isPhysical
import de.fruxz.sparkle.framework.extension.templateLocation
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.extension.world.displayString
import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent
import de.fruxz.sparkle.framework.positioning.dependent.DependentCubicalShape
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance.Companion.APPLIED
import de.fruxz.sparkle.server.SparkleCache
import dev.fruxz.stacked.extension.asComponent
import dev.fruxz.stacked.extension.asStyledComponents
import dev.fruxz.stacked.extension.dyeGray
import dev.fruxz.stacked.extension.dyeLightPurple
import dev.fruxz.stacked.extension.dyeRed
import dev.fruxz.stacked.hover
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import dev.fruxz.stacked.extension.asComponent
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.NamedTextColor.YELLOW
import net.kyori.adventure.text.format.Style.style
import net.kyori.adventure.text.format.TextDecoration.BOLD
import org.bukkit.Color
import org.bukkit.FluidCollisionMode.ALWAYS
import org.bukkit.Material
import org.bukkit.Particle.DustTransition
import java.nio.file.Path
import kotlin.io.path.*

internal class MarkerComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Marker"

	override suspend fun component() {

		additionalStart {
			Companion::markingItem.get() // initialize the marking item
			saves.createDirectories() // create the save directory
		}

		interchange(MarkerInterchange())

	}

	companion object {

		val saves: Path by lazy { SparklePath.componentPath(component(MarkerComponent::class)) / "saves" }

		@OptIn(ExperimentalPathApi::class)
		val savedFiles: List<Path>
			get() = saves.walk().filter { it.extension == "json" }.toList()

		val markingItem by lazy {

			Material.GOLDEN_HOE.item {

				label = "Marking Tool".asComponent.style(style(YELLOW, BOLD))
				itemIdentity = "markingTool"
				lore = """
				
				<YELLOW>LEFT-CLICK <gray>-> Set #1 Position
				<YELLOW>RIGHT-CLICK <gray>-> Set #2 Position
				<YELLOW>SNEAK-CLICK <gray>-> View Marking
				
			""".trimIndent().asStyledComponents

				onItemInteract("marker-interaction") { event ->
					val user = event.whoInteract
					val targetBlock = user.rayTraceBlocks(25.0, ALWAYS)?.hitBlock
					val actualBox = SparkleCache.playerMarkerBoxes[user.identityObject]
					val currentBox = actualBox ?: DependentCubicalShape(targetBlock?.location ?: templateLocation)

					if (user.isSneaking && !event.action.isPhysical) {
						event.denyInteraction()

						if (SparkleCache.playerMarkerBoxes[user.identityObject] != null) {

							text {
								this + text("You have the locations ").dyeGray()
								this + text(currentBox.firstLocation.displayString()).dyeLightPurple()
								this + text(" and ").dyeGray()
								this + text(currentBox.secondLocation.displayString()).dyeLightPurple()
								this + text(" marked.").dyeGray()
							}.hover {
								text {
									this + text("Both locations are ").dyeGray()
									this + text("${tryOrNull { currentBox.distance.round(2) } ?: Double.POSITIVE_INFINITY} Block(s) ").dyeLightPurple()
									this + text("apart.").dyeGray()
									this + newline() + newline()
									this + text("The selection contains a volume of ").dyeGray()
									this + text("${currentBox.blockVolume} Block(s)").dyeLightPurple()
								}
							}.notification(TransmissionAppearance.GENERAL, user).display()

						} else {

							text {
								this + text("You have currently ").dyeGray()
								this + text("no locations ").dyeRed()
								this + text("marked, that are valid for this action.").dyeGray()
							}.notification(TransmissionAppearance.FAIL, user).display()

						}

					} else {

						if (targetBlock != null) {
							val targetLocation = targetBlock.location.toCenterLocation()
							val targetPrint = targetBlock.location.displayString()

							fun actionProcessedMessage(positionNumber: Int) = text {
								this + text("You have set the location #").dyeGray()
								this + text("$positionNumber").dyeLightPurple()
								this + text(" to ").dyeGray()
								this + text(targetPrint).dyeLightPurple()
								this + text(".").dyeGray()
							}

							fun actionAlreadySet() = text {
								this + text("This position is ").dyeGray()
								this + text("already set").dyeRed()
								this + text(" to ").dyeGray()
								this + text(targetPrint).dyeLightPurple()
								this + text(".").dyeGray()
							}

							when {
								event.action.isLeftClick -> {
									event.denyInteraction()

									if (actualBox?.firstLocation != targetLocation) {

										SparkleCache.playerMarkerBoxes += user.identityObject to currentBox.updateFirstLocation(targetLocation)

										actionProcessedMessage(1)
											.hover {
												text {
													this + text("Other position is ").dyeGray()
													this + text("${tryOrNull { targetLocation.distance(currentBox.secondLocation).round(2) } ?: Double.POSITIVE_INFINITY} Block(s) ").dyeLightPurple()
													this + text("away.").dyeGray()
												}
											}
											.notification(APPLIED, user).display()

										particleOf(ParticleType.DUST_COLOR_TRANSITION)
											.putData(DustTransition(
												Color.fromRGB(NamedTextColor.LIGHT_PURPLE.value()),
												Color.fromRGB(NamedTextColor.DARK_PURPLE.value()),
												1F,
											))
											.offset(.3)
											.location(targetLocation)
											.count(30)
											.spawn()

									} else {
										actionAlreadySet().notification(TransmissionAppearance.FAIL, user).display()
									}
								}

								event.action.isRightClick -> {
									event.denyInteraction()

									if (actualBox?.secondLocation != targetLocation) {

										SparkleCache.playerMarkerBoxes += user.identityObject to currentBox.updateSecondLocation(targetLocation)

										actionProcessedMessage(2)
											.hover {
												text {
													this + text("Other position is ").dyeGray()
													this + text("${tryOrNull { targetLocation.distance(currentBox.firstLocation).round(2) } ?: Double.POSITIVE_INFINITY} Block(s) ").dyeLightPurple()
													this + text("away.").dyeGray()
												}
											}
											.notification(APPLIED, user).display()

										particleOf(ParticleType.DUST_COLOR_TRANSITION)
											.putData(DustTransition(
												Color.fromRGB(NamedTextColor.LIGHT_PURPLE.value()),
												Color.fromRGB(NamedTextColor.DARK_PURPLE.value()),
												1F,
											))
											.offset(.3)
											.location(targetLocation)
											.count(30)
											.spawn()

									} else {
										actionAlreadySet().notification(TransmissionAppearance.FAIL, user).display()
									}
								}
							}

						} else {

							text {
								this + text("You are ").dyeGray()
								this + text("not").dyeRed()
								this + text(" looking at a ").dyeGray()
								this + text("valid block").dyeRed()
								this + text("!").dyeGray()
							}.notification(TransmissionAppearance.FAIL, user).display()

						}

					}

				}

			}

		}

	}

}