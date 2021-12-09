package de.jet.paper.general.api.mojang

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
@SerialName("MojangProfileCape")
data class MojangProfileCape(
    @JsonNames("data") val value: String,
    val url: String
)