package de.jet.paper.extension.mojang

import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

fun Player.applySkin(from: String) = getMojangProfile(from).applySkinToPlayer(this)

fun Player.applySkin(from: UUID) = applySkin("$from")

fun Player.resetSkin() = getMojangProfile(name).applySkinToPlayer(this)

fun SkullMeta.applySkin(from: String, replaceName: Boolean = false) =
    getMojangProfile(from).applySkinToSkullMeta(this, replaceName)

fun SkullMeta.applySkin(from: UUID, replaceName: Boolean = false) =
    applySkin("$from", replaceName)
