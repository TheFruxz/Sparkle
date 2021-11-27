package de.jet.minecraft.general.api.mojang

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MojangProfileUsernameHistoryEntry")
data class MojangProfileUsernameHistoryEntry(
    val changed_at: String = "empty",
    val username: String
)