package de.jet.javacord.extension

import de.jet.jvm.extension.javaUtil.getOrNull
import org.javacord.api.DiscordApi
import org.javacord.api.entity.channel.Channel
import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.entity.server.Server

/**
 * Gets the [Channel] with the given [id] searched discord-api wide.
 * @param id The id of the [Channel] to get.
 * @return The [Channel] with the given [id] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.channel(id: Long) = getChannelById(id).getOrNull()

/**
 * Gets the first [Channel] with the given [name] searched discord-api wide.
 * @param name The name of the [Channel] to get.
 * @return The [Channel] with the given [name] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.channel(name: String) = getChannelsByName(name).firstOrNull()

/**
 * Gets every [Channel] with the given [name] searched discord-api wide as a list.
 * @param name The name of the [Channel] to get.
 * @return Every [Channel] with the given [name] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.channels(name: String) = getChannelsByName(name).toList()

/**
 * Gets every [Channel] searched discord-api wide as a list.
 * @return Every [Channel] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.channels() = channels.toList()

/**
 * Gets the [Channel] with the given [id] searched in the given server [this].
 * @param id The id of the [Channel] to get.
 * @return The [Channel] with the given [id] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun Server.channel(id: Long) = getChannelById(id).getOrNull()

/**
 * Gets the first [Channel] with the given [name] searched in the given server [this].
 * @param name The name of the [Channel] to get.
 * @return The [Channel] with the given [name] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun Server.channel(name: String) = getChannelsByName(name).firstOrNull()

/**
 * Gets every [Channel] with the given [name] searched in the given server [this] as a list.
 * @param name The name of the [Channel] to get.
 * @return Every [Channel] with the given [name] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun Server.channels(name: String) = getChannelsByName(name).toList()

/**
 * Gets every [Channel] searched in the given server [this] as a list.
 * @return Every [Channel] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun Server.channels() = channels.toList()

/**
 * Gets the [TextChannel] with the given [id] searched discord-api wide.
 * @param id The id of the [TextChannel] to get.
 * @return The [TextChannel] with the given [id] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.textChannel(id: Long) = getTextChannelById(id).getOrNull()

/**
 * Gets the first [TextChannel] with the given [name] searched discord-api wide.
 * @param name The name of the [TextChannel] to get.
 * @return The [TextChannel] with the given [name] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.textChannel(name: String) = getTextChannelsByName(name).firstOrNull()

/**
 * Gets every [TextChannel] with the given [name] searched discord-api wide as a list.
 * @param name The name of the [TextChannel] to get.
 * @return Every [TextChannel] with the given [name] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.textChannels(name: String) = getTextChannelsByName(name).toList()

/**
 * Gets every [TextChannel] searched discord-api wide as a list.
 * @return Every [TextChannel] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.textChannels() = textChannels.toList()

/**
 * Gets the [TextChannel] with the given [id] searched in the given server [this].
 * @param id The id of the [TextChannel] to get.
 * @return The [TextChannel] with the given [id] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun Server.textChannel(id: Long) = getTextChannelById(id).getOrNull()

/**
 * Gets the first [TextChannel] with the given [name] searched in the given server [this].
 * @param name The name of the [TextChannel] to get.
 * @return The [TextChannel] with the given [name] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun Server.textChannel(name: String) = getTextChannelsByName(name).firstOrNull()

/**
 * Gets every [TextChannel] with the given [name] searched in the given server [this] as a list.
 * @param name The name of the [TextChannel] to get.
 * @return Every [TextChannel] with the given [name] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun Server.textChannels(name: String) = getTextChannelsByName(name).toList()

/**
 * Gets every [TextChannel] searched in the given server [this] as a list.
 * @return Every [TextChannel] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun Server.textChannels() = textChannels.toList()

// Starting with (server + channel) extensions

/**
 * Gets the [Channel] with the given [channelId] on the server with the given [serverId] searched discord-api wide.
 * @param serverId The id of the server to get the [Channel] from.
 * @param channelId The id of the [Channel] to get.
 * @return The [Channel] with the given [channelId] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.serverChannel(serverId: Long, channelId: Long) = server(serverId)?.channel(channelId)?.asServerChannel()?.getOrNull()

/**
 * Gets the first [Channel] with the given [name] on the server with the given [serverId] searched discord-api wide.
 * @param serverId The id of the server to get the [Channel] from.
 * @param name The name of the [Channel] to get.
 * @return The [Channel] with the given [name] or null if not found.
 * @author Fruxz
 */
fun DiscordApi.serverChannel(serverId: Long, name: String) = server(serverId)?.channel(name)?.asServerChannel()?.getOrNull()

/**
 * Gets every [Channel] on the server with the given [serverId] searched discord-api wide as a list.
 * @param serverId The id of the server to get the [Channel] from.
 * @return Every [Channel] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.serverChannels(serverId: Long) = server(serverId)?.channels()

/**
 * Gets the [TextChannel] with the given [channelId] on the server with the given [serverId] searched discord-api wide.
 * @param serverId The id of the server to get the [TextChannel] from.
 * @param channelId The id of the [TextChannel] to get.
 * @return The [TextChannel] with the given [channelId] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.serverTextChannel(serverId: Long, channelId: Long) = server(serverId)?.textChannel(channelId)?.asServerTextChannel()?.getOrNull()

/**
 * Gets the first [TextChannel] with the given [name] on the server with the given [serverId] searched discord-api wide.
 * @param serverId The id of the server to get the [TextChannel] from.
 * @param name The name of the [TextChannel] to get.
 * @return The [TextChannel] with the given [name] or null if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.serverTextChannel(serverId: Long, name: String) = server(serverId)?.textChannel(name)?.asServerTextChannel()?.getOrNull()

/**
 * Gets every [TextChannel] on the server with the given [serverId] searched discord-api wide as a list.
 * @param serverId The id of the server to get the [TextChannel] from.
 * @return Every [TextChannel] or an empty list if not found.
 * @author Fruxz
 * @since 1.0
 */
fun DiscordApi.serverTextChannels(serverId: Long) = server(serverId)?.textChannels?.toList()
