package de.fruxz.sparkle.framework.extension.mojang

import org.bukkit.OfflinePlayer

suspend fun OfflinePlayer.mojangProfile() = getMojangProfile(uniqueId)