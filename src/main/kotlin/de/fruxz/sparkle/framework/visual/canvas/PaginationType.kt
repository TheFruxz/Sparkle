package de.fruxz.sparkle.framework.visual.canvas

import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.math.floorToInt
import de.fruxz.ascend.extension.math.limitTo
import de.fruxz.sparkle.framework.extension.subNamespacedKey
import de.fruxz.sparkle.framework.extension.system
import de.fruxz.sparkle.framework.extension.visual.ui.item
import de.fruxz.sparkle.framework.extension.visual.ui.skull
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

	fun contentRendering(scrollState: Int = 0, lines: Int, contents: Map<Int, ItemLike>): Map<Int, ItemLike>

	companion object {

		fun none() =
			object : PaginationType<Unit> {
				override val base: PaginationBase? = null
				override val configuration = Unit
				override fun computeRealSlot(virtualSlot: Int) = virtualSlot
				override fun contentRendering(scrollState: Int, lines: Int, contents: Map<Int, ItemLike>) = contents
			}

		fun scroll(configuration: ScrollControlSetup = ScrollControlSetup()) =
			object : PaginationType<ScrollControlSetup> {
				override val base: PaginationBase = SCROLL
				override val configuration = configuration
				override fun computeRealSlot(virtualSlot: Int) = virtualSlot + (floorToInt(virtualSlot.toDouble() / 8))
				override fun contentRendering(scrollState: Int, lines: Int, contents: Map<Int, ItemLike>) = buildMap {

					for ((index, slotRequest) in ((8 * scrollState)..(((8 * lines) - 1) + (8 * scrollState))).withIndex()) {
						contents[slotRequest]?.let { this[computeRealSlot(index)] = it }
					}

					if (lines > 1) {
						val linesOfContent = ceilToInt((contents.keys.max().toDouble() + 1) / 9)
						val isEndOfScroll = (scrollState + lines) > linesOfContent+1
						this[8] = when (scrollState) {
							0 -> configuration.emptyButton
							else -> configuration.backButton
						}.copy().dataPut(CANVAS_BUTTON_SCROLL, 0)
						this[(lines * 9) - 1] = when {
							isEndOfScroll -> configuration.emptyButton
							else -> configuration.nextButton
						}.copy().dataPut(CANVAS_BUTTON_SCROLL, +1)

						if (lines > 2) {
							val startSlot = ceilToInt((lines-2) * (scrollState.toDouble() / linesOfContent))+2
							val endSlot = ceilToInt((lines-2) * (scrollState.toDouble() / linesOfContent))+2

							for (currentLine in 2 until lines) {
								this[(currentLine * 9) - 1] = when (currentLine) {
									in startSlot..endSlot -> configuration.barButton
									else -> configuration.barBackground
								}.copy()
							}
						}

					}

				}
			}

		fun paged(configuration: PageControlSetup = PageControlSetup()) =
			object : PaginationType<PageControlSetup> {
				override val base: PaginationBase = PAGED
				override val configuration = configuration
				override fun computeRealSlot(virtualSlot: Int) = virtualSlot
				override fun contentRendering(
					scrollState: Int,
					lines: Int,
					contents: Map<Int, ItemLike>
				): Map<Int, ItemLike> = buildMap {
					for ((index, value) in (0+((9*(lines-1))*scrollState)..(8+(9*(lines-2))+((9*(lines-1))*scrollState))).withIndex()) {
						contents[value]?.let { put(index, it) }
					}
					this[(lines*9)-6] = when (scrollState) {
						0 -> configuration.emptyButton
						else -> configuration.backButton
					}.copy().dataPut(CANVAS_BUTTON_SCROLL, 0)
					this[(lines*9)-5] = configuration.pageIcon.copy().size((scrollState+1).limitTo(1..64))
					this[(lines*9)-4] = when {
						(scrollState > (contents.keys.max().toDouble() / 9 / (lines-1)) - 1) -> configuration.emptyButton
						else -> configuration.nextButton
					}.copy().dataPut(CANVAS_BUTTON_SCROLL, 1)
				}
			}

		val CANVAS_BUTTON_SCROLL = system.subNamespacedKey("canvas.scroll")
		val CANVAS_SCROLL_STATE = system.subKey("state.scroll")

		data class ScrollControlSetup(
			val backButton: Item = skull("MHF_ArrowUp", false).blankLabel(),
			val nextButton: Item = skull("MHF_ArrowDown", false).blankLabel(),
			val emptyButton: Item = skull("MHF_Wood", false).blankLabel(),
			val barBackground: Item = Material.BLACK_STAINED_GLASS_PANE.item.blankLabel(),
			val barButton: Item = Material.WHITE_STAINED_GLASS_PANE.item.blankLabel(),
		)

		data class PageControlSetup(
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