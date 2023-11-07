package de.fruxz.sparkle.server.component.ui.actionbar

import dev.fruxz.ascend.tool.time.TimeState
import de.fruxz.sparkle.server.component.ui.actionbar.AdaptiveActionBarComponent.LayerPosition
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class ActionBarLayer(
	override val content: (Player) -> Component,
	override val expiration: (Player) -> TimeState,
	override val level: LayerPosition,
	override val displayCondition: (Player) -> Boolean = { true },
) : ActionBarLayerSchematic {

	constructor(
		staticContent: Component,
		expiration: TimeState,
		level: LayerPosition,
		displayCondition: (Player) -> Boolean = { true }
	) : this({ staticContent }, { expiration }, level, displayCondition)

	constructor(
		staticContent: (Player) -> Component,
		expiration: TimeState,
		level: LayerPosition,
		displayCondition: (Player) -> Boolean = { true },
	) : this(staticContent, { expiration }, level, displayCondition)

}
