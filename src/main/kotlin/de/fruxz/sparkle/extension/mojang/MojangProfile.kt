package de.fruxz.sparkle.extension.mojang

import org.bukkit.OfflinePlayer

suspend fun OfflinePlayer.mojangProfile() = getMojangProfile(uniqueId)