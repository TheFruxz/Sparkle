package de.moltenKt.paper.app.component.ui.actionbar

import de.fruxz.ascend.tool.timing.calendar.TimeState
import de.moltenKt.paper.app.component.ui.actionbar.AdaptiveActionBarComponent.LayerPosition
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
