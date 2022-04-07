package de.jet.paper.app.component.buildMode.config

import kotlinx.serialization.Serializable
import org.bukkit.Material

@Serializable
data class BuildModeConfiguration(
	val enabled: Boolean = true,
	val protectedActions: List<ProtectedAction> = ProtectedAction.values().toList(),
	val ignoredMaterials: List<String> = listOf(
		Material.AIR,
		Material.CAVE_AIR,
		Material.VOID_AIR,
	).map { it.name }
) {

	enum class ProtectedAction {

		DESTROY, PLACE, DROP_ITEM, PICKUP_ITEM, BUCKET, RIGHT_CLICK_INTERACT, LEFT_CLICK_INTERACT, PHYSICAL;

	}

}