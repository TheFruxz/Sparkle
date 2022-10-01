package de.moltenKt.paper.extension.mojang

import org.bukkit.OfflinePlayer

suspend fun OfflinePlayer.mojangProfile() = getMojangProfile(uniqueId)