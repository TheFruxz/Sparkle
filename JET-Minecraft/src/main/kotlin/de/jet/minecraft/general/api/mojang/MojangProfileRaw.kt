package de.jet.minecraft.general.api.mojang

import kotlinx.serialization.Serializable

@Serializable
data class MojangProfileRaw(
    val signature: String,
    val value: String
)