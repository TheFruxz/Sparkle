package de.jet.minecraft.general.api.mojang

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class MojangProfileSkin(
    @JsonNames("data") val value: String,
    val url: String
)