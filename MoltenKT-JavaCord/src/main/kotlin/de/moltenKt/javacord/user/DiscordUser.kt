package de.moltenKt.javacord.user

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import kotlinx.serialization.Serializable

/**
 * This class represents the writeable small data of a discord user.
 * It only contains the id and the username of the discord user to get
 * the complete data use the DiscordAPI.
 *
 * This object is a [Identifiable] to get an easy way to identify
 * This object is [Serializable] to easily use it for JSON files.
 *
 * @param userName The username of the discord user.
 * @param userId The id of the discord user.
 * @property identity The identity of the discord user as a string.
 * @property identityObject The identity of the discord user as an Identity<[DiscordUser]> object.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
data class DiscordUser(
    val userName: String,
    val userId: Long,
) : Identifiable<DiscordUser> {

    override val identity = "$userId"

}
