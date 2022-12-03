package de.fruxz.sparkle.framework.data.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

@Serializable
@SerialName("config-tree")
data class JsonConfiguration(
	val version: String = "1.0",
	val elements: Map<String, JsonPrimitive> = emptyMap(),
)

