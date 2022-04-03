package de.jet.paper.extension.mojang

import de.jet.paper.general.api.mojang.MojangProfile
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

val OfflinePlayer.mojangProfile: MojangProfile
	get() = getMojangProfile(uniqueId)