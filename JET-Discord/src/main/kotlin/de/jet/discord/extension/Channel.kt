package de.jet.discord.extension

import de.jet.library.extension.java.getOrNull
import org.javacord.api.DiscordApi
import org.javacord.api.entity.server.Server

fun DiscordApi.channel(id: Long) = getChannelById(id).getOrNull()

fun DiscordApi.channel(name: String) = getChannelsByName(name).firstOrNull()

fun DiscordApi.channels(name: String) = getChannelsByName(name).toList()

fun DiscordApi.channels() = channels.toList()

fun Server.channel(id: Long) = getChannelById(id).getOrNull()

fun Server.channel(name: String) = getChannelsByName(name).firstOrNull()

fun Server.channels(name: String) = getChannelsByName(name).toList()

fun Server.channels() = channels.toList()

fun DiscordApi.textChannel(id: Long) = getTextChannelById(id).getOrNull()

fun DiscordApi.textChannel(name: String) = getTextChannelsByName(name).firstOrNull()

fun DiscordApi.textChannels(name: String) = getTextChannelsByName(name).toList()

fun DiscordApi.textChannels(id: Long) = textChannels.toList()

fun Server.textChannel(id: Long) = getTextChannelById(id).getOrNull()

fun Server.textChannel(name: String) = getTextChannelsByName(name).firstOrNull()

fun Server.textChannels(name: String) = getTextChannelsByName(name).toList()

fun Server.textChannels(id: Long) = textChannels.toList()

// both

fun DiscordApi.serverChannel(serverId: Long, channelId: Long) = server(serverId)?.channel(channelId)?.asServerChannel()?.getOrNull()

fun DiscordApi.serverChannel(serverId: Long, name: String) = server(serverId)?.channel(name)?.asServerChannel()?.getOrNull()

fun DiscordApi.serverChannels(serverId: Long) = server(serverId)?.channels()

fun DiscordApi.serverTextChannel(serverId: Long, channelId: Long) = server(serverId)?.textChannel(channelId)?.asServerTextChannel()?.getOrNull()

fun DiscordApi.serverTextChannel(serverId: Long, name: String) = server(serverId)?.textChannel(name)?.asServerTextChannel()?.getOrNull()

fun DiscordApi.serverTextChannels(serverId: Long) = server(serverId)?.textChannels?.toList()
