package de.jet.paper.tool.display.ui.panel

/**
 * This enum defines multiple possible reactions of a panel.
 * These enum values can be attached to a [Panel].
 * @author Fruxz
 * @since 1.0
 */
enum class PanelFlag {

	/**
	 * This flag defines, that the panel border is not click-protected.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_BORDER_PROTECTION,

	/**
	 * This flag defines, that no panel interactions are possible.
	 * (only panel interactions, not item interactions)
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_INTERACT,

	/**
	 * This flag defines, that every item grab is cancelled.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_GRAB,

	/**
	 * This flag defines, that the panel cannot be closed.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_CLOSE,

	/**
	 * This flag defines, that the panel cannot be opened.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_OPEN;
}