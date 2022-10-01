package de.fruxz.sparkle.extension.mojang

import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

suspend fun Player.applySkin(from: String) = getMojangProfile(from).applySkinToPlayer(this)

suspend fun Player.applySkin(from: UUID) = applySkin("$from")

suspend fun Player.resetSkin() = getMojangProfile(name).applySkinToPlayer(this)

suspend fun SkullMeta.applySkin(from: String, replaceName: Boolean = true) =
    getMojangProfile(from).applySkinToSkullMeta(this, replaceName)

suspend fun SkullMeta.applySkin(from: UUID, replaceName: Boolean = true) =
    applySkin("$from", replaceName)
