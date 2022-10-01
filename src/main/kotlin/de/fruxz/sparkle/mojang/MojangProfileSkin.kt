package de.fruxz.sparkle.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MojangProfileSkin")
data class MojangProfileSkin(
    val data: String,
    val url: String
)