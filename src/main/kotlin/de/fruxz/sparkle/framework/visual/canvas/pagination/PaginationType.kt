package de.fruxz.sparkle.framework.visual.canvas.pagination

import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.math.floorToInt
import de.fruxz.ascend.extension.math.limitTo
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.extension.subNamespacedKey
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.extension.visual.ui.skull
import de.fruxz.sparkle.framework.visual.canvas.Canvas
import de.fruxz.sparkle.framework.visual.canvas.Canvas.ExperimentalCanvasApi
import de.fruxz.sparkle.framework.visual.canvas.pagination.PaginationType.Companion.PaginationBase.PAGED
import de.fruxz.sparkle.framework.visual.canvas.pagination.PaginationType.Companion.PaginationBase.SCROLL
import de.fruxz.sparkle.framework.visual.item.Item
import de.fruxz.sparkle.framework.visual.item.ItemLike
import de.fruxz.stacked.extension.dyeDarkGray
import de.fruxz.stacked.extension.subKey
import de.fruxz.stacked.text
import org.bukkit.Material


interface PaginationType<C> {

	val base: PaginationBase?
	val lineSize: Int?

	val configuration: C

	fun computeRealSlot(virtualSlot: Int): Int

	fun contentRendering(scrollState: Int = 0, canvas: Canvas): Map<Int, ItemLike>

	companion object {

		fun none() =
			object : PaginationType<Unit> {
				override val base: PaginationBase? = null
				override val lineSize = null
				override val configuration = Unit
				override fun computeRealSlot(virtualSlot: Int) = virtualSlot
				override fun contentRendering(scrollState: Int, canvas: Canvas) = canvas.content
			}

		@ExperimentalCanvasApi
		fun scroll(configuration: ScrollControlSetup = ScrollControlSetup()) =
			object : PaginationType<ScrollControlSetup> {
				override val base: PaginationBase = SCROLL
				override val lineSize = 8
				override val configuration = configuration
				override fun computeRealSlot(virtualSlot: Int) = virtualSlot + (floorToInt(virtualSlot.toDouble() / 8))
				override fun contentRendering(scrollState: Int, canvas: Canvas) = buildMap {
					val lines = ((canvas.base.virtualSize + 1) / lineSize)
					val contents = canvas.content
					val maxVirtualSlot = canvas.virtualSlots.last

					for ((index, slotRequest) in ((8 * scrollState)..(((8 * lines) - 1) + (8 * scrollState))).withIndex()) {
						contents[slotRequest]?.let { this[computeRealSlot(index)] = it }
					}

					if (lines > 1) {
						val linesOfContent = ceilToInt((1.0 + maxVirtualSlot) / 8)
						val (_, _, _, isEndOfScroll) = CanvasPageInformation.of(canvas.base.virtualSize, scrollState, canvas.virtualSlots.last, canvas.pagination.base) // most variables wasted, but it's okay

						if (configuration.renderButtons) {
							this[8] = when (scrollState) {
								0 -> configuration.emptyButton
								else -> configuration.backButton
							}.copy().setPersistent(CANVAS_BUTTON_SCROLL, 0)

							this[(lines * 9) - 1] = when {
								isEndOfScroll -> configuration.emptyButton
								else -> configuration.nextButton
							}.copy().setPersistent(CANVAS_BUTTON_SCROLL, +1)
						}

						if (configuration.renderBar) {
							if (lines > 2) {
								val start = 1 + ((lines - 2).toDouble() * (scrollState.toDouble() / linesOfContent)).floorToInt()
								val stop = 1 + ((lines - 2).toDouble() * (scrollState.toDouble() / linesOfContent)).ceilToInt()

								for (currentLine in 2 until lines) {
									this[(currentLine * 9) - 1] = when (currentLine - 1) {
										in start..stop -> configuration.barButton
										else -> configuration.barBackground
									}.copy()
								}
							}
						}

					}

				}
			}

		@ExperimentalCanvasApi
		fun paged(configuration: PageControlSetup = PageControlSetup()) =
			object : PaginationType<PageControlSetup> {
				override val base: PaginationBase = PAGED
				override val lineSize = 9
				override val configuration = configuration
				override fun computeRealSlot(virtualSlot: Int) = virtualSlot
				override fun contentRendering(scrollState: Int, canvas: Canvas): Map<Int, ItemLike> = buildMap {
					val lines = ((canvas.base.virtualSize + 1) / 9)
					val contents = canvas.content
					val maxVirtualSlot = canvas.virtualSlots.last

					for ((index, value) in (0+((9*(lines-1))*scrollState)..(8+(9*(lines-2))+((9*(lines-1))*scrollState))).withIndex()) {
						contents[value]?.let { put(index, it) }
					}

					if (configuration.renderButtons) {

						this[(lines * 9) - 6] = when (scrollState) {
							0 -> configuration.emptyButton
							else -> configuration.backButton
						}.copy().setPersistent(CANVAS_BUTTON_SCROLL, 0)

						this[(lines*9)-4] = when {
							CanvasPageInformation.of(canvas.base.virtualSize, scrollState, maxVirtualSlot, base).isLastPosition -> configuration.emptyButton
							else -> configuration.nextButton
						}.copy().setPersistent(CANVAS_BUTTON_SCROLL, 1)

					}

					if (configuration.renderIndicator) {
						this[(lines*9)-5] = configuration.pageIcon.copy().size((scrollState+1).limitTo(1..64))
					}
				}
			}

		val CANVAS_BUTTON_SCROLL = sparkle.subNamespacedKey("canvas.scroll")
		val CANVAS_SCROLL_STATE = sparkle.subKey("state.scroll")

		data class ScrollControlSetup(
			val renderBar: Boolean = true,
			val renderButtons: Boolean = true,
			val backButton: Item = skull("MHF_ArrowUp", false).blankLabel(),
			val nextButton: Item = skull("MHF_ArrowDown", false).blankLabel(),
			val emptyButton: Item = skull("MHF_Wood", false).label(text("End of content").dyeDarkGray()),
			val barBackground: Item = Material.BLACK_STAINED_GLASS_PANE.item.blankLabel(),
			val barButton: Item = Material.WHITE_STAINED_GLASS_PANE.item.blankLabel(),
		)

		data class PageControlSetup(
			val renderButtons: Boolean = true,
			val renderIndicator: Boolean = true,
			val backButton: Item = skull("MHF_ArrowLeft", false).blankLabel(),
			val nextButton: Item = skull("MHF_ArrowRight", false).blankLabel(),
			val emptyButton: Item = skull("MHF_Wood", false).label(text("End of content").dyeDarkGray()),
			val pageIcon: Item = Material.BOOK.item.blankLabel()
		)

		enum class PaginationBase {
			SCROLL, PAGED;
		}

	}

}