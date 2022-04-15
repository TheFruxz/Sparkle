package de.moltenKt.javacord.extension

import org.javacord.api.DiscordApi

/**
 * Creates a new Discord connection via the [buildDiscordApi] function and its login & join methods.
 * @param token the token to use for the bot connection to discord
 * @return the created discord api instance
 * @author Fruxz
 * @since 1.0
 */
fun connectDiscord(token: String): DiscordApi = buildDiscordApi {
    setToken(token)
}.login().join()