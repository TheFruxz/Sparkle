package de.jet.discord.extension

import de.jet.discord.user.DiscordUser
import org.javacord.api.DiscordApi

fun DiscordApi.getDiscordUserObjectByID(id: Long) = getUserById(id).get().let { user ->
    return@let DiscordUser(user.name, user.id)
}

fun DiscordApi.getDiscordUserObjectByName(name: String) = getCachedUsersByName(name).firstOrNull()?.let { user ->
    return@let DiscordUser(user.name, user.id)
}