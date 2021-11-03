package de.jet.minecraft.general.api.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@SerialName("MojangProfileSkin")
data class MojangProfileSkin(
    @JsonNames("data") val value: String,
    val url: String
)