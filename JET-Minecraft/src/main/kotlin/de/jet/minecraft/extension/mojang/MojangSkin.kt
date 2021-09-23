package de.jet.minecraft.extension.mojang

import org.bukkit.entity.Player
import java.util.*

fun Player.applySkin(from: String) = getMojangProfile(from).applySkinToPlayer(this)

fun Player.applySkin(from: UUID) = getMojangProfile(from).applySkinToPlayer(this)

fun Player.resetSkin() = getMojangProfile(name).applySkinToPlayer(this)