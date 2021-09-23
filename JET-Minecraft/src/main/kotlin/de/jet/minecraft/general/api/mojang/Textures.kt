package de.jet.minecraft.general.api.mojang

import kotlinx.serialization.Serializable

@Serializable
data class Textures(
    val cape: Cape = Cape("empty", "empty"),
    val custom: Boolean,
    val raw: Raw,
    val skin: Skin,
    val slim: Boolean
)