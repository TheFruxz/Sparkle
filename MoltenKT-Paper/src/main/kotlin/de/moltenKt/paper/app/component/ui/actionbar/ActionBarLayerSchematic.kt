package de.moltenKt.paper.app.component.ui.actionbar

import de.moltenKt.core.tool.timing.calendar.TimeState
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

interface ActionBarLayerSchematic {

	/**
	 * The content, which will be displayed to the receiver
	 * @author Fruxz
	 * @since 1.0
	 */
	val content: (Player) -> Component

	/**
	 * The time, when this layer is not valid anymore and
	 * will get removed from the list of current layers
	 * @author Fruxz
	 * @since 1.0
	 */
	val expiration: (Player) -> TimeState

	/**
	 * This lambda will be called, when the layer is about to be displayed to the player.
	 * The boolean, that this lambda returns, will be used to determine, if the layer will
	 * be displayed, or not.
	 * @author Fruxz
	 * @since 1.0
	 */
	val displayCondition: (Player) -> Boolean

	/**
	 * The render-layer of the layer (higher -> displayed, even with background, lower -> not displayed, if higher is placed)
	 * @author Fruxz
	 * @since 1.0
	 */
	val level: AdaptiveActionBarComponent.LayerPosition

}