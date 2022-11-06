package de.fruxz.sparkle.framework.visual.canvas

import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.math.floorToInt
import de.fruxz.ascend.extension.math.limitTo
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.extension.subNamespacedKey
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.extension.visual.ui.skull
import de.fruxz.sparkle.framework.visual.canvas.Canvas.ExperimentalCanvasApi
import de.fruxz.sparkle.framework.visual.canvas.PaginationType.Companion.PaginationBase.PAGED
import de.fruxz.sparkle.framework.visual.canvas.PaginationType.Companion.PaginationBase.SCROLL
import de.fruxz.sparkle.framework.visual.item.Item
import de.fruxz.sparkle.framework.visual.item.ItemLike
import de.fruxz.stacked.extension.subKey
import org.bukkit.Material


interface PaginationType<C> {

	val base: PaginationBase?

	val configuration: C

	fun computeRealSlot(virtualSlot: Int): Int

	fun contentRendering(scrollState: Int = 0, canvas: Canvas): Map<Int, ItemLike>

	companion object {

		fun none() =
			object : PaginationType<Unit> {
				override val base: PaginationBase? = null
				override val configuration = Unit
				override fun computeRealSlot(virtualSlot: Int) = virtualSlot
				override fun contentRendering(scrollState: Int, canvas: Canvas) = canvas.content
			}

		@ExperimentalCanvasApi
		fun scroll(configuration: ScrollControlSetup = ScrollControlSetup()) =
			object : PaginationType<ScrollControlSetup> {
				override val base: PaginationBase = SCROLL
				override val configuration = configuration
				override fun computeRealSlot(virtualSlot: Int) = virtualSlot + (floorToInt(virtualSlot.toDouble() / 8))
				override fun contentRendering(scrollState: Int, canvas: Canvas) = buildMap {
					val lines = ((canvas.base.virtualSize + 1) / 9)
					val contents = canvas.content
					val maxVirtualSlot = canvas.virtualSlots.last

					for ((index, slotRequest) in ((8 * scrollState)..(((8 * lines) - 1) + (8 * scrollState))).withIndex()) {
						contents[slotRequest]?.let { this[computeRealSlot(index)] = it }
					}

					if (lines > 1) {
						val linesOfContent = ceilToInt((maxVirtualSlot.toDouble() + 1) / 8)
						val isEndOfScroll = (scrollState + 1) > linesOfContent

						if (configuration.renderButtons) {
							this[8] = when (scrollState) {
								0 -> configuration.emptyButton
								else -> configuration.backButton
							}.copy().setPersistent(CANVAS_BUTTON_SCROLL, 0)

							this[(lines * 9) - 1] = when {
								isEndOfScroll -> configuration.emptyButton.label("<red>end")
								else -> configuration.nextButton
							}.copy().setPersistent(CANVAS_BUTTON_SCROLL, +1)
						}

						if (configuration.renderBar) {
							if (lines > 2) {
								val startPos = 1 + ceilToInt((lines - 3) * (scrollState.toDouble() / (linesOfContent)))
								val endPos = 1 + ceilToInt((lines - 3) * (scrollState.toDouble() / (linesOfContent)))

								for (currentLine in 2 until lines) {
									this[(currentLine * 9) - 1] = when (currentLine - 1) {
										in startPos..endPos -> configuration.barButton
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
							(scrollState > (maxVirtualSlot.toDouble() / 9 / (lines-1)) - 1) -> configuration.emptyButton
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
			val emptyButton: Item = skull("MHF_Wood", false).blankLabel(),
			val barBackground: Item = Material.BLACK_STAINED_GLASS_PANE.item.blankLabel(),
			val barButton: Item = Material.WHITE_STAINED_GLASS_PANE.item.blankLabel(),
		)

		data class PageControlSetup(
			val renderButtons: Boolean = true,
			val renderIndicator: Boolean = true,
			val backButton: Item = skull("MHF_ArrowLeft", false).blankLabel(),
			val nextButton: Item = skull("MHF_ArrowRight", false).blankLabel(),
			val emptyButton: Item = skull("MHF_Wood", false).blankLabel(),
			val pageIcon: Item = Material.BOOK.item.blankLabel()
		)

		enum class PaginationBase {
			SCROLL, PAGED;
		}

	}

}