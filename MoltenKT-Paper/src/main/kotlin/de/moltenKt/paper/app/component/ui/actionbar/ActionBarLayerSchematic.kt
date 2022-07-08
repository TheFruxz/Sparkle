package de.moltenKt.paper.app.component.ui.actionbar

import de.moltenKt.core.tool.timing.calendar.TimeState
import net.kyori.adventure.text.Component

interface ActionBarLayerSchematic {

	/**
	 * The content, which will be displayed to the receiver
	 * @author Fruxz
	 * @since 1.0
	 */
	val content: () -> Component

	/**
	 * The time, when this layer is not valid anymore and
	 * will get removed from the list of current layers
	 * @author Fruxz
	 * @since 1.0
	 */
	val expiration: TimeState

	/**
	 * The render-layer of the layer (higher -> displayed, even with background, lower -> not displayed, if higher is placed)
	 * @author Fruxz
	 * @since 1.0
	 */
	val level: AdaptiveActionBarComponent.LayerPosition

}