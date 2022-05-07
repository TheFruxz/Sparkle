package de.moltenKt.paper.tool.display.ui.canvas

/**
 * This enum defines multiple possible reactions of a canvas.
 * These enum values can be attached to a [MutableCanvas].
 * @author Fruxz
 * @since 1.0
 */
enum class CanvasFlag {

	/**
	 * This flag defines, that no onClick-based action is triggered.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_CLICK_ACTIONS,

	/**
	 * This flag defines, that every item grab is cancelled.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_GRAB,

	/**
	 * This flag defines, that the canvas cannot be closed.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_CLOSE,

	/**
	 * This flag defines, that the canvas cannot be opened.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_OPEN;

}