package de.jet.library.extension.paper

import de.jet.library.tool.permission.Approval
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
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

// TODO: 22.08.2021 Cooldowns here
