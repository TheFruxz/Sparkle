package de.jet.minecraft.general.api.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MojangProfileRaw")
data class MojangProfileRaw(
    val signature: String,
    val value: String
)