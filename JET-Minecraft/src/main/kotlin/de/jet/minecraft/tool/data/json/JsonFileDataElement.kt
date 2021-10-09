package de.jet.minecraft.tool.data.json

import kotlinx.serialization.Serializable

@Serializable
data class JsonFileDataElement<T : Any>(
    val address: String,
    val value: T,
)