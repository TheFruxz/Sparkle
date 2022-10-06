package de.fruxz.sparkle.framework.extension.mojang

import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

suspend fun Player.applySkin(from: String) = getMojangProfile(from).applySkinToPlayer(this)

suspend fun Player.applySkin(from: UUID) = applySkin("$from")

suspend fun Player.resetSkin() = getMojangProfile(name).applySkinToPlayer(this)

suspend fun SkullMeta.applySkin(from: String) = getMojangProfile(from).applySkinToSkullMeta(this)

suspend fun SkullMeta.applySkin(from: UUID) = applySkin("$from")
