package de.jet.discord.extension

import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.embed.EmbedBuilder

/**
 * Builder for [DiscordApiBuilder]
 * @param process modification of the [DiscordApiBuilder]
 * @author Fruxz
 * @since 1.0
 */
fun buildDiscordApi(process: DiscordApiBuilder.() -> Unit) = DiscordApiBuilder().apply(process)

/**
 * Builder for [EmbedBuilder]
 * @param process modification of the [EmbedBuilder]
 * @author Fruxz
 * @since 1.0
 */
fun buildEmbed(process: EmbedBuilder.() -> Unit) = EmbedBuilder().apply(process)

/**
 * Builder for [MessageBuilder]
 * @param process modification of the [MessageBuilder]
 * @author Fruxz
 * @since 1.0
 */
fun buildMessage(process: MessageBuilder.() -> Unit) = MessageBuilder().apply(process)
