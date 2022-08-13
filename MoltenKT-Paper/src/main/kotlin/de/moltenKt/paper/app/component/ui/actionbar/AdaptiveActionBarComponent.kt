package de.moltenKt.paper.app.component.ui.actionbar

import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.component.ui.actionbar.AdaptiveActionBarComponent.LayerPosition.BACKGROUND
import de.moltenKt.paper.app.component.ui.actionbar.AdaptiveActionBarComponent.LayerPosition.FOREGROUND
import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import kotlin.time.Duration

/**
 * This component adaptively decides, whenever it should show an actionbar message,
 * or not.
 * If a layer is present, that is not outdated, then it will display the layer.
 */
class AdaptiveActionBarComponent : SmartComponent(AUTOSTART_MUTABLE, true) {

	override val label = "AdaptiveActionBar"

	override suspend fun component() {

		service(AdaptiveActionBarService())

	}

	companion object {

		@JvmStatic
		var globalLayers: List<ActionBarLayerSchematic> = listOf()

		@JvmStatic
		var playerLayers: Map<Player, List<ActionBarLayerSchematic>> = mapOf()

		// # Global layers

		@JvmStatic
		fun addGlobalLayer(layer: ActionBarLayerSchematic) {
			when (layer.level) {
				BACKGROUND -> globalLayers = globalLayers.partition { it.level == BACKGROUND }.let { it.first + layer + it.second }
				FOREGROUND -> globalLayers += layer
			}
		}

		@JvmStatic
		fun addGlobalLayer(positionIndex: Int, layer: ActionBarLayerSchematic) {
			globalLayers = globalLayers.toMutableList().apply {
				add(positionIndex, layer)
			}
		}

		// ## Advanced creation

		@JvmStatic
		fun addGlobalLayer(position: LayerPosition, stayDuration: Duration, content: (Player) -> Component) =
			addGlobalLayer(ActionBarLayer(content, Calendar.now() + stayDuration, position))

		@JvmStatic
		fun addGlobalLayer(position: LayerPosition, stayDuration: Duration, staticContent: Component) =
			addGlobalLayer(position, stayDuration) { staticContent }


		// # Player layers

		@JvmStatic
		fun getPlayerLayers(player: Player): List<ActionBarLayerSchematic> = playerLayers[player] ?: listOf()

		@JvmStatic
		fun addPlayerLayer(player: Player, position: LayerPosition, layer: ActionBarLayerSchematic) {
			playerLayers += when (position) {
				BACKGROUND -> player to getPlayerLayers(player).partition { it.level == BACKGROUND }.let { it.first + layer + it.second }
				FOREGROUND -> player to getPlayerLayers(player) + layer
			}
		}

		@JvmStatic
		fun addPlayerLayer(player: Player, positionIndex: Int, layer: ActionBarLayerSchematic) {
			playerLayers += player to getPlayerLayers(player).toMutableList().apply {
				add(positionIndex, layer)
			}
		}

		// ## Advanced creation

		@JvmStatic
		fun addPlayerLayer(player: Player, position: LayerPosition, stayDuration: Duration, content: (Player) -> Component) =
			addPlayerLayer(player, position, ActionBarLayer(content, Calendar.now() + stayDuration, position))

		@JvmStatic
		fun addPlayerLayer(player: Player, position: LayerPosition, stayDuration: Duration, staticContent: Component) =
			addPlayerLayer(player, position, stayDuration) { staticContent }

	}

	enum class LayerPosition {
		BACKGROUND,
		FOREGROUND,
	}

}