package de.jet.paper.tool.display.ui.panel

/**
 * This enum defines multiple possible reactions of a panel.
 * These enum values can be attached to a [Panel].
 * @author Fruxz
 * @since 1.0
 */
enum class PanelFlag {

	/**
	 * This flag defines, that no item is movable.
	 * @author Fruxz
	 * @since 1.0
	 */
	NOT_MOVE_ABLE,

	/**
	 * This flag defines, that no item can be dragged.
	 * @author Fruxz
	 * @since 1.0
	 */
	NOT_DRAG_ABLE,

	/**
	 * This flag defines, that no item can be clicked.
	 * @author Fruxz
	 * @since 1.0
	 */
	NOT_CLICK_ABLE,

	/**
	 * This flag defines, that the panel cannot be closed.
	 * @author Fruxz
	 * @since 1.0
	 */
	NOT_CLOSE_ABLE,

	/**
	 * This flag defines, that the panel cannot be opened.
	 * @author Fruxz
	 * @since 1.0
	 */
	NOT_OPEN_ABLE;
}