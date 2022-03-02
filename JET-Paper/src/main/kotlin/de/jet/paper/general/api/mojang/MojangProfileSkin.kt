package de.jet.paper.general.api.mojang

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@ExperimentalSerializationApi
@SerialName("MojangProfileSkin")
data class MojangProfileSkin(
    @JsonNames("data") val value: String,
    val url: String
)