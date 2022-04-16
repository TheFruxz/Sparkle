package de.moltenKt.javacord.app

import de.moltenKt.javacord.app.DiscordBotExtension.identity
import de.moltenKt.javacord.app.DiscordBotExtension.parallelRunAllowed
import de.moltenKt.javacord.app.DiscordBotExtension.runtimeAccessor
import de.moltenKt.javacord.extension.runBot
import de.moltenKt.javacord.structure.Bot
import de.moltenKt.core.application.extension.AppExtension
import kotlin.reflect.KFunction1

/**
 * This is a [AppExtension], that runs a DiscordBot using the
 * [runBot] function. It can be attached to an App-Runtime using
 * the *attach* or *attachWith* function.
 * @property identity is "discordBot"
 * @property parallelRunAllowed is false - parallel runs disallowed
 * @property runtimeAccessor is [runBot] - also a builder for the bot instance
 * @author Fruxz
 * @since 1.0
 */
object DiscordBotExtension : AppExtension<Bot, Unit, Unit> {

	override val identity = "discordBot"

	override val parallelRunAllowed = false

	override val runtimeAccessor: KFunction1<Bot.() -> Unit, Unit> = ::runBot

}