package de.jet.discord.extension

import de.jet.discord.user.DiscordUser
import de.jet.library.extension.java.getOrNull
import org.javacord.api.DiscordApi

/**
 * Get the user with the matching [id]
 */
fun DiscordApi.getDiscordUserObjectByID(id: Long) = getUserById(id).getOrNull()?.let { user ->
    return@let DiscordUser(user.name, user.id)
}

fun DiscordApi.getDiscordUserObjectByName(name: String) = getCachedUsersByName(name).firstOrNull()?.let { user ->
    return@let DiscordUser(user.name, user.id)
}

fun DiscordApi.user(id: Long) = getUserById(id).getOrNull()
