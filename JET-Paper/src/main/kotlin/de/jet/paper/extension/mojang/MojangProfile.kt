package de.jet.paper.extension.mojang

import de.jet.paper.general.api.mojang.MojangProfile
import org.bukkit.entity.Player

val Player.mojangProfile: MojangProfile
	get() = getMojangProfile(name)