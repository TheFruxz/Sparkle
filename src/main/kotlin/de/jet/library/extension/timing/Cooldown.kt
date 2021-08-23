package de.jet.library.extension.timing

import de.jet.library.tool.timing.cooldown.Cooldown
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

fun isCooldownDecaying(identity: String, section: String) =
	Cooldown.isCooldownRunning(identity, section)

fun setCooldown(identity: String, ticks: Int, section: String) =
	Cooldown.launchCooldown(identity, ticks, section)

fun Player.isCooldownDecaying(identity: String, section: String) =
	Cooldown.isCooldownRunning(identity, section)

fun Player.setCooldown(identity: String, ticks: Int, section: String) =
	Cooldown.launchCooldown(identity, ticks, section)

fun Entity.isCooldownDecaying(identity: String, section: String) =
	Cooldown.isCooldownRunning(identity, section)

fun Entity.setCooldown(identity: String, ticks: Int, section: String) =
	Cooldown.launchCooldown(identity, ticks, section)