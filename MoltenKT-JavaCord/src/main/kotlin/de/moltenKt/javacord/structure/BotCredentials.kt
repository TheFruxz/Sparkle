package de.moltenKt.javacord.structure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The credentials of the discord bot, currently only the token is required.
 * @param token The token of the bot, which is required to connect to the discord api.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("BotCredentials")
data class BotCredentials(
	var token: String? = null,
)
