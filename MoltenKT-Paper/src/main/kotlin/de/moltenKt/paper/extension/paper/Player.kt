package de.moltenKt.paper.extension.paper

import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.app.component.buildMode.BuildModeComponent
import de.moltenKt.paper.tool.annotation.RequiresComponent
import de.moltenKt.paper.tool.position.CubicalShape
import org.bukkit.OfflinePlayer
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

val OfflinePlayer.identityObject: Identity<OfflinePlayer>
	get() = Identity("$uniqueId")

val Player.identityObject : Identity<Player>
	get() = Identity("$uniqueId")

@Suppress("DEPRECATION")
var LivingEntity.quickMaxHealth: Double
	get() = getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue ?: maxHealth
	set(value) {
		getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = value
	}

fun LivingEntity.maxOutHealth() {
	health = quickMaxHealth
}

@RequiresComponent(BuildModeComponent::class)
var OfflinePlayer.buildMode: Boolean
	get() = MoltenCache.buildModePlayers.contains(identityObject)
	set(value) {
		if (value) {
			MoltenCache.buildModePlayers.add(identityObject)
		} else
			MoltenCache.buildModePlayers.remove(identityObject)
	}

/**
 * @throws IllegalArgumentException if the marker is not set for this player
 */
var Player.marker: CubicalShape
	get() = MoltenCache.playerMarkerBoxes[identityObject] ?: throw IllegalArgumentException("Player marker is not set!")
	set(value) {
		MoltenCache.playerMarkerBoxes[identityObject] = value
	}