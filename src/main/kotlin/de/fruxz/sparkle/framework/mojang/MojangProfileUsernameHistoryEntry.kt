package de.fruxz.sparkle.framework.mojang

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@SerialName("MojangProfileUsernameHistoryEntry")
@OptIn(ExperimentalSerializationApi::class)
data class MojangProfileUsernameHistoryEntry constructor(
    @JsonNames("changed_at") val changedAt: String = "empty",
    val username: String
)