package de.fruxz.sparkle.framework.util.extension.mojang

import org.bukkit.OfflinePlayer

suspend fun OfflinePlayer.mojangProfile() = getMojangProfile(uniqueId)