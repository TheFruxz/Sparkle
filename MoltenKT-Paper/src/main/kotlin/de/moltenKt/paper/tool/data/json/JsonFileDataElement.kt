package de.moltenKt.paper.tool.data.json

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("config-element")
data class JsonFileDataElement(
    val path: String,
    @Polymorphic val value: Any,
)

@Serializable
@SerialName("config-tree")
data class JsonConfiguration(
    val version: String = "1.0",
    val elements: List<JsonFileDataElement>,
)

