package de.fruxz.sparkle.framework.visual.canvas.pagination

import dev.fruxz.ascend.extension.math.ceilToInt
import dev.fruxz.ascend.extension.objects.takeIfInstance
import de.fruxz.sparkle.framework.visual.canvas.pagination.PaginationType.Companion.PaginationBase
import de.fruxz.sparkle.framework.visual.canvas.pagination.PaginationType.Companion.PaginationBase.PAGED
import de.fruxz.sparkle.framework.visual.canvas.pagination.PaginationType.Companion.PaginationBase.SCROLL
import de.fruxz.sparkle.framework.visual.canvas.session.CanvasSessionManager.CanvasSession
import org.bukkit.inventory.Inventory

/**
 * This data class represents the computational current state of
 * a paginated canvas session.
 * @param pageState On which page or scroll position the canvas is currently.
 * @param renderedLines how many lines can be rendered (all items included, even lazy/async ones)
 * @param utilizableLines how many lines can be used to render stuff (only currently displayed ones)
 * @param isLastPosition if the canvas is currently on the last page or scroll position
 * @author Fruxz
 * @since 1.0
 */
data class CanvasPageInformation(
	val pageState: Int,
	val renderedLines: Int,
	val utilizableLines: Int,
	val isLastPosition: Boolean,
) {

	companion object {

		fun of(inventory: Inventory, session: CanvasSession): CanvasPageInformation = of(
			inventorySize = inventory.size,
			scrollState = session.parameters[PaginationType.CANVAS_SCROLL_STATE]?.takeIfInstance<Int>() ?: 0,
			virtualSize = session.canvas.virtualSlots.last,
			base = session.canvas.pagination.base
		)

		fun of(inventorySize: Int, scrollState: Int, virtualSize: Int, base: PaginationBase?): CanvasPageInformation {
			val renderedLines = ceilToInt(virtualSize.toDouble() / 9)
			val utilizableLines = ceilToInt(inventorySize.toDouble() / when (base) {
				SCROLL -> 8
				else -> 9
			})

			return CanvasPageInformation(
				pageState = scrollState,
				renderedLines = renderedLines,
				utilizableLines = utilizableLines,
				isLastPosition = when (base) {
					SCROLL -> (scrollState + utilizableLines) > renderedLines
					PAGED -> scrollState > (renderedLines / ceilToInt((inventorySize.toDouble() / 9)-1)) - 1
					else -> true
				}
			)
		}

	}

}
