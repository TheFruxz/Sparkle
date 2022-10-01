package de.moltenKt.paper.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MojangProfileCape")
data class MojangProfileCape(
    val data: String,
    val url: String
)