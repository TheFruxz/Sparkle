package de.jet.discord.extension

import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.entity.message.embed.EmbedBuilder

fun buildEmbed(process: EmbedBuilder.() -> Unit) = EmbedBuilder().apply(process)

fun buildMessage(process: MessageBuilder.() -> Unit) = MessageBuilder().apply(process)
