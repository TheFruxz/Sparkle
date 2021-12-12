package de.jet.javacord.app

import de.jet.javacord.extension.runBot
import de.jet.javacord.structure.Bot
import de.jet.javacord.structure.BotAppearance
import de.jet.javacord.structure.BotCredentials
import de.jet.jvm.application.extension.AppExtension
import kotlin.reflect.KFunction1

object DiscordBotExtension : AppExtension<Bot, Unit, Unit> {

	override val identity = "discordbot"

	override val parallelRunAllowed = true

	override val runtimeAccessor: KFunction1<Bot.() -> Unit, Unit> = ::runBot

}