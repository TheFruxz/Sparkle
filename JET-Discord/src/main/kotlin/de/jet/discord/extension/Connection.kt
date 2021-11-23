package de.jet.discord.extension

fun connectDiscord(token: String) = buildDiscordApi {
    setToken(token)
}.login().join()