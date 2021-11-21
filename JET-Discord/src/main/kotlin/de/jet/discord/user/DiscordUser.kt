package de.jet.discord.user

import kotlinx.serialization.Serializable

@Serializable
data class DiscordUser(
    val userName: String,
    val userId: Long,
)
