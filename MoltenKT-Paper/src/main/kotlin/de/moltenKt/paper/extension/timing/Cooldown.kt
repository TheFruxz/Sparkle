package de.moltenKt.paper.extension.timing

import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.tool.timing.cooldown.Cooldown
import de.moltenKt.paper.tool.timing.cooldown.Cooldown.Companion.CooldownSection
import de.moltenKt.paper.tool.timing.cooldown.Cooldown.Companion.sectioning
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

val Cooldown?.isOver: Boolean
	get() = this?.destination?.let { Calendar.now().isAfter(it) } ?: true

fun isCooldownDecaying(identity: String, section: String) =
	Cooldown.isCooldownRunning(identity, section)

fun setCooldown(identity: String, ticks: Int, section: String) =
	Cooldown.launchCooldown(identity, ticks, section)

fun getCooldown(identity: String, section: String) =
	MoltenCache.livingCooldowns["${sectioning(section)}:$identity"]

fun Player.isCooldownDecaying(identity: String) =
	Cooldown.isCooldownRunning(identity, CooldownSection.player(this))

fun Player.setCooldown(identity: String, ticks: Int) =
	Cooldown.launchCooldown(identity, ticks, CooldownSection.player(this))

fun Player.getCooldown(identity: String) =
	MoltenCache.livingCooldowns["${sectioning(CooldownSection.player(this))}:$identity"]

fun Entity.isCooldownDecaying(identity: String) =
	Cooldown.isCooldownRunning(identity, CooldownSection.entity(this))

fun Entity.setCooldown(identity: String, ticks: Int) =
	Cooldown.launchCooldown(identity, ticks, CooldownSection.entity(this))

fun Entity.getCooldown(identity: String) =
	MoltenCache.livingCooldowns["${sectioning(CooldownSection.entity(this))}:$identity"]