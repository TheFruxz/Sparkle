package de.moltenKt.core.tool.collection

/**
 * This class represents a part of an iterable, which represents itself a page (for e.g. page 3 of 10).
 * @param pageNumber The number of the page (can be 1, 2, ...)
 * @param availablePages The pages, which are available in total. (starting with 1)
 * @param content The content of this exact page
 * @author Fruxz
 * @since 1.0
 */
data class IterablePage<T>(
	val pageNumber: Int,
	val availablePages: IntRange,
	val content: List<T>,
) {

	/**
	 * This property represents the index of this current page.
	 * @author Fruxz
	 * @since 1.0
	 */
	val pageIndex: Int = pageNumber - 1

	/**
	 * This property represents, if this page is the last available page.
	 * @author Fruxz
	 * @since 1.0
	 */
	val isLastPage: Boolean = pageNumber == availablePages.last

	/**
	 * This property represents, if this page is the first available page.
	 * @author Fruxz
	 * @since 1.0
	 */
	val isFirstPage: Boolean = pageNumber == availablePages.first

	/**
	 * This property represents, if this page has a following page.
	 * @author Fruxz
	 * @since 1.0
	 */
	val hasNextPage: Boolean = !isLastPage

	/**
	 * This property represents, if this page has a previous page.
	 * @author Fruxz
	 * @since 1.0
	 */
	val hasPreviousPage: Boolean = !isFirstPage

	/**
	 * This property represents, if this page is the only available page.
	 * @author Fruxz
	 * @since 1.0
	 */
	val isSinglePage: Boolean = availablePages.count() == 1

	/**
	 * This property represents, if other pages are available.
	 * @author Fruxz
	 * @since 1.0
	 */
	val isMultiPage: Boolean = !isSinglePage

}
