package de.jet.paper.extension.paper

import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.app.JetCache
import de.jet.paper.app.component.buildmode.BuildModeComponent
import de.jet.paper.tool.annotation.RequiresComponent
import de.jet.paper.tool.permission.Approval
import de.jet.paper.tool.position.LocationBox
import org.bukkit.OfflinePlayer
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.permissions.Permissible

fun Permissible.hasApproval(approval: Approval) =
	approval.hasApproval(this)

@Suppress("DEPRECATION")
var LivingEntity.quickMaxHealth: Double
	get() = getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue ?: maxHealth
	set(value) {
		getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = value
	}

fun LivingEntity.maxOutHealth() {
	health = quickMaxHealth
}

val Player.identityObject: Identity<Player>
	get() = Identity("$uniqueId")

val OfflinePlayer.identityObject: Identity<OfflinePlayer>
	get() = Identity("$uniqueId")

@RequiresComponent(BuildModeComponent::class)
var OfflinePlayer.buildMode: Boolean
	get() = JetCache.buildModePlayers.contains(identityObject)
	set(value) {
		if (value) {
			JetCache.buildModePlayers.add(identityObject)
		} else
			JetCache.buildModePlayers.remove(identityObject)
	}

/**
 * @throws IllegalArgumentException if the marker is not set for this player
 */
var Player.marker: LocationBox
	get() = JetCache.playerMarkerBoxes[identityObject] ?: throw IllegalArgumentException("Player marker is not set!")
	set(value) {
		JetCache.playerMarkerBoxes[identityObject] = value
	}