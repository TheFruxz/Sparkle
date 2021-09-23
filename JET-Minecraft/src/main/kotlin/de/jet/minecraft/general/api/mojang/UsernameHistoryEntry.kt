package de.jet.minecraft.general.api.mojang

import kotlinx.serialization.Serializable

@Serializable
data class UsernameHistoryEntry(
    val changed_at: String = "empty",
    val username: String
)