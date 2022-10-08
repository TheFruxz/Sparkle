package de.fruxz.sparkle.framework.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@SerialName("MojangProfileUsernameHistoryEntry")
data class MojangProfileUsernameHistoryEntry(
    @JsonNames("changed_at") val changedAt: String = "empty",
    val username: String
)