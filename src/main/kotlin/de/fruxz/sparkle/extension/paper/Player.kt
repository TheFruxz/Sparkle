package de.fruxz.sparkle.extension.paper

import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.app.MoltenCache
import de.fruxz.sparkle.app.component.buildMode.BuildModeComponent
import de.fruxz.sparkle.tool.annotation.RequiresComponent
import de.fruxz.sparkle.tool.position.dependent.DependentCubicalShape
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.structure.Structure

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
			MoltenCache.buildModePlayers += identityObject
		} else
			MoltenCache.buildModePlayers -= identityObject
	}

/**
 * This computational property represents the current markings of
 * the player and throws [NoSuchElementException] on get, if no
 * marker is currently set.
 * Love null? use [Player.markerOrNull]!
 * @throws NoSuchElementException on get, if no marker is currently set.
 * @author Fruxz
 * @since 1.0
 */
var Player.marker: DependentCubicalShape
	get() = markerOrNull ?: throw NoSuchElementException("Player marker is not set!")
	set(value) {
		MoltenCache.playerMarkerBoxes += identityObject to value
	}

/**
 * This computational property represents the current markings of
 * the player and returns null, if no marker is currently set.
 * @author Fruxz
 * @since 1.0
 */
var Player.markerOrNull: DependentCubicalShape?
	get() = MoltenCache.playerMarkerBoxes[identityObject]
	set(value) {
		if (value != null) {
			MoltenCache.playerMarkerBoxes += identityObject to value
		} else
			MoltenCache.playerMarkerBoxes -= identityObject
	}

/**
 * This computational property returns the current [Player.marker]
 * of the player, but transformed into a new unsaved [Structure],
 * using the [Bukkit.getStructureManager] and the [Player.marker].
 * @throws NoSuchElementException through [Player.marker]
 * @author Fruxz
 * @since 1.0
 */
val Player.markerAsStructure: Structure
	get() = Bukkit.getStructureManager().createStructure().fill(marker, true)

/**
 * This computational property returns the current [Player.markerOrNull]
 * of the player, but transformed into a new unsaved [Structure],
 * using the [Bukkit.getStructureManager] and the [Player.markerOrNull].
 * Or returns null, if no marker is currently set.
 * @author Fruxz
 * @since 1.0
 */
val Player.markerAsStructureOrNull: Structure?
	get() = markerOrNull?.let { Bukkit.getStructureManager().createStructure().fill(it, true) }