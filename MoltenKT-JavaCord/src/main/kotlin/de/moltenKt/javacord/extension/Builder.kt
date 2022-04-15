package de.moltenKt.javacord.extension

import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.embed.EmbedBuilder

/**
 * Builder for [DiscordApiBuilder]
 * @param process modification of the [DiscordApiBuilder]
 * @return the modified [DiscordApiBuilder]
 * @author Fruxz
 * @since 1.0
 */
fun buildDiscordApi(process: DiscordApiBuilder.() -> Unit) = DiscordApiBuilder().apply(process)

/**
 * Builder for [EmbedBuilder]
 * @param process modification of the [EmbedBuilder]
 * @return the modified [EmbedBuilder]
 * @author Fruxz
 * @since 1.0
 */
fun buildEmbed(process: EmbedBuilder.() -> Unit) = EmbedBuilder().apply(process)

/**
 * Builder for [MessageBuilder]
 * @param process modification of the [MessageBuilder]
 * @return the modified [MessageBuilder]
 * @author Fruxz
 * @since 1.0
 */
fun buildMessage(process: MessageBuilder.() -> Unit) = MessageBuilder().apply(process)
