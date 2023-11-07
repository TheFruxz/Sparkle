package de.fruxz.sparkle.server.component.marking

import dev.fruxz.ascend.extension.container.paged
import de.fruxz.sparkle.framework.effect.particle.ParticleType
import de.fruxz.sparkle.framework.effect.sound.SoundLibrary
import de.fruxz.sparkle.framework.extension.component
import de.fruxz.sparkle.framework.extension.coroutines.doAsync
import de.fruxz.sparkle.framework.extension.coroutines.doSync
import de.fruxz.sparkle.framework.extension.effect.particleOf
import de.fruxz.sparkle.framework.extension.entity.markerOrNull
import de.fruxz.sparkle.framework.extension.structureManager
import de.fruxz.sparkle.framework.extension.subKey
import de.fruxz.sparkle.framework.extension.visual.BLUE
import de.fruxz.sparkle.framework.extension.visual.BOLD
import de.fruxz.sparkle.framework.extension.visual.message
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.extension.visual.ui.mainHand
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.completion.mustNotMatchOutput
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredPlayerInterchange
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance.Companion.APPLIED
import de.fruxz.sparkle.server.SparkleData
import de.fruxz.sparkle.server.component.marking.MarkerManager.loadMarking
import de.fruxz.sparkle.server.component.marking.MarkerManager.saveMarking
import dev.fruxz.stacked.extension.dyeGray
import dev.fruxz.stacked.extension.dyeGreen
import dev.fruxz.stacked.extension.dyeLightPurple
import dev.fruxz.stacked.extension.dyeRed
import dev.fruxz.stacked.extension.dyeYellow
import dev.fruxz.stacked.extension.newline
import dev.fruxz.stacked.extension.newlines
import dev.fruxz.stacked.extension.style
import dev.fruxz.stacked.hover
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import kotlinx.coroutines.delay
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Particle.DustTransition
import kotlin.io.path.extension
import kotlin.io.path.name
import kotlin.io.path.pathString
import kotlin.io.path.relativeTo
import kotlin.time.Duration.Companion.seconds

internal class MarkerInterchange : StructuredPlayerInterchange(
	label = "marker",
	structure = buildInterchangeStructure {

		branch {

			addContent("tool")

			concludedExecution {

				executor.let {

					it.inventory.mainHand = MarkerComponent.markingItem

					text {
						this + text("You have received ").dyeGray()
						this + text("the marker tool").dyeLightPurple()
						this + text("!").dyeGray()
					}.message(executor).promptSound(SoundLibrary.ITEM_RECEIVE).display()

				} ?: text("You must be a player to get the wand!").dyeRed().message(executor).display()

			}

		}

		branch {

			addContent("load")

			branch {

				addContent(
					CompletionAsset.files(MarkerComponent.saves, filter = {
						it.extension == "json"
					})
				)

				concludedExecution {

					executor.let {
						loadMarking(it, getInput()).invokeOnCompletion {
							when (it) {
								null -> text("Marking loaded successfully!").dyeLightPurple().notification(TransmissionAppearance.APPLIED, executor).display()
								else -> text("The marking '${getInput()}' could not be loaded!!").dyeRed().notification(TransmissionAppearance.ERROR, executor).display()
							}
						}
					} ?: text("You must be a player to load a marker!").dyeRed().message(executor).display()

				}

			}

		}

		branch {

			addContent("save")

			branch {

				addContent("name")
				mustNotMatchOutput()

				concludedExecution {
					executor.let {
						saveMarking(it, getInput()).invokeOnCompletion {
							when (it) {
								null -> text("Marking saved successfully!").dyeLightPurple().notification(TransmissionAppearance.APPLIED, executor).display()
								else -> text("No save-able marking got found!").dyeRed().notification(TransmissionAppearance.ERROR, executor).display()
							}
						}
					} ?: text("You must be a player to save a marker!").dyeRed().message(executor).display()
				}

			}

		}

		branch {

			addContent("saveAsStructure")


			branch {

				addContent("name")
				mustNotMatchOutput()

				concludedExecution {
					val marker = executor.markerOrNull

					if (marker != null) {

						structureManager.saveStructure(vendor.key.subKey(getInput()), marker.structure(true))

						text {
							this + text("The structure has been saved as ").dyeGray()
							this + text(getInput()).dyeLightPurple()
							this + text("!").dyeGray()
						}.notification(APPLIED, executor).display()

					} else {
						text("You have currently no marker set, which can be saved!").dyeRed().message(executor).display()
					}

				}

			}

		}

		branch {

			addContent("list")

			concludedExecution {
				listMarkings(this)
			}

			branch {

				addContent(CompletionAsset.pageCompletion { MarkerComponent.savedFiles.size })

				concludedExecution {
					listMarkings(this, getInput().toInt())
				}

			}

		}

		branch {

			addContent("show")

			concludedExecution {

				executor.markerOrNull?.let {

					doAsync { _ ->

						val particle =
							particleOf(ParticleType.DUST_COLOR_TRANSITION)
								.putData(
									DustTransition(
										Color.fromRGB(NamedTextColor.LIGHT_PURPLE.value()),
										Color.WHITE,
										2F,
									)
								)
								.count(1)


						repeat(6) { _ ->
							it.outlineBlockLocations.forEach { gridBlock ->
								particle.location(gridBlock).spawn()
							}
							delay(.5.seconds)
						}

					}

					text {
						this + text("Successfully").style(NamedTextColor.GREEN, BOLD)
						this + text(" displayed the marker!").dyeGray()
					}.notification(TransmissionAppearance.GENERAL, executor).display()

				} ?: text {
					this + text("You have currently ").dyeGray()
					this + text("no").dyeRed()
					this + text(" no marker set.").dyeGray()
				}.notification(TransmissionAppearance.FAIL, executor).display()

			}

		}

		branch {

			addContent("visit")

			concludedExecution {
				executor.markerOrNull?.let {

					executor.let { player ->

						doSync { _ ->

							player.teleportAsync(it.center) // quick and cheap teleportation

							if (player.allowFlight) player.isFlying = true // if can fly, do fly, because of possibility of being in the air

						}

						text {
							this + text("Successfully").style(NamedTextColor.GREEN, BOLD)
							this + text(" teleported to center of the marker!").dyeGray()
						}.notification(TransmissionAppearance.GENERAL, executor).display()

					} ?: text("You must be a player to visit a marker!").dyeRed().message(executor).display()

				} ?: text {
					this + text("You have currently ").dyeGray()
					this + text("no").dyeRed()
					this + text(" no marker set.").dyeGray()
				}.notification(TransmissionAppearance.FAIL, executor).display()
			}

		}

	}
) {

	private companion object {

		val markerComponent: MarkerComponent
			get() = component(MarkerComponent::class)

		fun listMarkings(access: InterchangeAccess<*>, page: Int = 1) = with(access) {
			val paged = MarkerComponent.savedFiles.paged(page - 1, SparkleData.systemConfig.entriesPerListPage)

			text {
				this + text("List of saved marker files: ").dyeGray()
				this + text("(Page $page of ${paged.availablePages.last})").dyeYellow()

                newlines(2)

                paged.forEach { path ->
	                val relative = path.relativeTo(MarkerComponent.saves).pathString

	                this + text("⏏").dyeGreen()
						.hover {
							text {
								this + text("Load marker").style(BLUE, TextDecoration.BOLD)
								newlines(2)
								this + text("Click to load the marker from the file ").dyeGray()
							}
		                }
		                .clickEvent(ClickEvent.runCommand("/marker load ${path.fileName.name}"))
	                this + text(" ")
	                this + text(relative).dyeYellow()
	                newline()
                }

				newline()

			}.notification(TransmissionAppearance.GENERAL, executor).display()

		}

	}

}