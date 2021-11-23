package de.jet.discord.user

import de.jet.library.tool.smart.identification.Identifiable
import kotlinx.serialization.Serializable

@Serializable
data class DiscordUser(
    val userName: String,
    val userId: Long,
) : Identifiable<DiscordUser> {

    override val identity = "$userId"

}
