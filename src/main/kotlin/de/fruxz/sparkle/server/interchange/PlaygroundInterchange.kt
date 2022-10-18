package de.fruxz.sparkle.server.interchange

import de.fruxz.ascend.tool.smart.composition.SuspendComposable
import de.fruxz.sparkle.framework.extension.asPlayerOrNull
import de.fruxz.sparkle.framework.extension.effect.playSoundEffect
import de.fruxz.sparkle.framework.extension.effect.soundOf
import de.fruxz.sparkle.framework.extension.player
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.extension.visual.ui.skull
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.visual.canvas.Canvas.CanvasPrototypeAPI
import de.fruxz.sparkle.framework.visual.canvas.PaginationType
import de.fruxz.sparkle.framework.visual.canvas.buildCanvas
import de.fruxz.sparkle.framework.visual.color.ColorType
import de.fruxz.sparkle.framework.visual.color.DyeableMaterial.BANNER
import org.bukkit.Material.BARREL
import org.bukkit.Material.IRON_DOOR
import org.bukkit.Sound.ENTITY_CAT_BEG_FOR_FOOD

internal class PlaygroundInterchange : StructuredInterchange("playground", buildInterchangeStructure {

	branch {

		addContent("transmissionSound")

		branch {

			addContent(CompletionAsset.TRANSMISSION_LEVEL)

			concludedExecution {

				val level = getInput(1, CompletionAsset.TRANSMISSION_LEVEL)

				"<gradient:gray:yellow>This is the sound of the '<gold>${level.name}</gold>' Transmission!"
					.notification(level, executor)
					.display()

			}

		}

	}

	branch {

		addContent("canvas")

		branch {

			addContent("deferredContent")

			concludedExecution {

				executor.asPlayerOrNull?.let { canvasDeferred.display(it) }

			}

		}

		branch {

			addContent("scrollableContent")

			concludedExecution {

				executor.asPlayerOrNull?.let { canvasScrollable.display(it) }

			}

		}


	}

}) {

	private companion object {

		val canvasDeferred = buildCanvas {
			label("<i>Tech-Demo")
			base(9 * 6)

			border(IRON_DOOR)
			setInner(0, BARREL.item {
				label("Magic!")
				onItemClick {
					it.player.playSoundEffect(soundOf(ENTITY_CAT_BEG_FOR_FOOD))
				}
			})
			setInnerDeferred(1, SuspendComposable {
				skull("CoasterFreakDE")
			})

		}

		val canvasScrollable = @OptIn(CanvasPrototypeAPI::class)
		buildCanvas {
			label("<i>Hello!")
			pagination(PaginationType.scroll())
			base(9 * 6)

			repeat(217) {
				this[it] = BANNER.withColor(ColorType.values().random()).item {
					label("$it")
				}
			}

		}

	}

}