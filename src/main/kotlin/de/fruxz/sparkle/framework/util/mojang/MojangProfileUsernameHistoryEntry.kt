package de.fruxz.sparkle.framework.util.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MojangProfileUsernameHistoryEntry")
data class MojangProfileUsernameHistoryEntry(
    val changed_at: String = "empty",
    val username: String
)