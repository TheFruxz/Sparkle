package dev.fruxz.sparkle.framework.entity

import org.bukkit.block.Biome
import org.bukkit.entity.Entity

var Entity.gravity: Boolean
    get() = hasGravity()
    set(value) = setGravity(value)

val Entity.biome: Biome
    get() = location.block.biome