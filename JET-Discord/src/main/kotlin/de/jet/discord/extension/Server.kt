package de.jet.discord.extension

import org.javacord.api.DiscordApi

fun DiscordApi.server(id: Long) = getServerById(id).get()

fun DiscordApi.server(name: String) = getServersByName(name).firstOrNull()

fun DiscordApi.servers(name: String) = getServersByName(name).toList()

fun DiscordApi.servers() = servers.toList()