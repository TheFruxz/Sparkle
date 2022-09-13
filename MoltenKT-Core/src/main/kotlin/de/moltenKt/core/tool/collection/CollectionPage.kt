package de.moltenKt.core.tool.collection

/**
 * This class represents a part of a list, which represents itself a page (for e.g. page 3 of 10).
 * @param content The content of this exact page
 * @param pageIndex The index of the page (remember, starting with 0)
 * @param pages The available amount of pages, like the size of a collection, that the origin container has.
 * @author Fruxz
 * @since 1.0
 */
data class CollectionPage<T>(
	val content: List<T>,
	val pageIndex: Int,
	val pages: Int,
) {

	/**
	 * The number of the page.
	 * (starting with 1 and ending with [pages])
	 * @author Fruxz
	 * @since 1.0
	 */
	val thisPage = pageIndex + 1

	/**
	 * The range of available pages.
	 * (starting with 1 and ending with [pages])
	 * @author Fruxz
	 * @since 1.0
	 */
	val pageRange = 1..pages

}
