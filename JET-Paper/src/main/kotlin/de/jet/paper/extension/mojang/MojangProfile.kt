package de.jet.paper.extension.mojang

import org.bukkit.OfflinePlayer

suspend fun OfflinePlayer.mojangProfile() = getMojangProfile(uniqueId)