package de.fruxz.sparkle.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MojangProfileUsernameHistoryEntry")
data class MojangProfileUsernameHistoryEntry(
    val changed_at: String = "empty",
    val username: String
)