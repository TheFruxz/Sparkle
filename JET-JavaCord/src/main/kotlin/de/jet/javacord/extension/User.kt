package de.jet.javacord.extension

import de.jet.javacord.user.DiscordUser
import de.jet.jvm.extension.javaUtilUnknown.getOrNull
import org.javacord.api.DiscordApi
import org.javacord.api.entity.user.User

/**
 * Get the user with the matching [id] from the discord api as a [DiscordUser]
 * @param id The id of the user
 * @return The user with the matching [id] as a [DiscordUser] or null if not exists
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.getDiscordUserObjectByID(id: Long) = getUserById(id).getOrNull()?.let { user ->
    return@let DiscordUser(user.name, user.id)
}

/**
 * Get the first user matching [name] from the discord api as a [DiscordUser]
 * @param name The name of the user
 * @return The first user matching [name] as a [DiscordUser] or null if not exists
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.getDiscordUserObjectByName(name: String) = getCachedUsersByName(name).firstOrNull()?.let { user ->
    return@let DiscordUser(user.name, user.id)
}

/**
 * Get the user with the matching [id] from the discord api as a [DiscordUser]
 * @param id The id of the user
 * @return The user with the matching [id] as a [DiscordUser] or null if not exists
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.user(id: Long) = getUserById(id).getOrNull()

/**
 * Returns the user as a [DiscordUser] object
 * @return the user as a [DiscordUser]
 * @author Fruxz
 * @since 1.0
 */
fun User.discordUserObject() = DiscordUser(this.name, this.id)
