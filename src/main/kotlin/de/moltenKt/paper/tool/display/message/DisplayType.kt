package de.moltenKt.paper.tool.display.message

/**
 * This enum defines the display position of the [Transmission].
 * @author Fruxz
 * @since 1.0
 */
enum class DisplayType {

	/**
	 * Displays the [Transmission] in the chat of the receivers
	 * @author Fruxz
	 * @since 1.0
	 */
	DISPLAY_CHAT,

	/**
	 * Displays the [Transmission] in the action bar of the receivers
	 * Usually music-information is displayed here, if a music disc is played
	 * @author Fruxz
	 * @since 1.0
	 */
	DISPLAY_ACTIONBAR,

	/**
	 * Displays the [Transmission] in the upper row of the Title-Area
	 * @author Fruxz
	 * @since 1.0
	 */
	DISPLAY_TITLE,

	/**
	 * Displays the [Transmission] in the lower row of the Title-Area
	 * @author Fruxz
	 * @since 1.0
	 */
	DISPLAY_SUBTITLE;

}