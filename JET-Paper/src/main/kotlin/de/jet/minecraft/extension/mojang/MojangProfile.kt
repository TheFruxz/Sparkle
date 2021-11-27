package de.jet.minecraft.extension.mojang

import de.jet.minecraft.general.api.mojang.MojangProfile
import org.bukkit.entity.Player

val Player.mojangProfile: MojangProfile
	get() = getMojangProfile(name)