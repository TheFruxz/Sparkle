package de.jet.javacord.structure

import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder

/**
 * This data class represents the configuration state of a discord-api bot.
 * @param data the credentials of the bot to log in with
 * @param appearance the public appearance of the bot
 * @param processPreProcess the process, before the bot-builder will be created
 * @param processPreSetup the process, before the bot-builder will be setup
 * @param processPreLogin the process, before the bot-builder will be logged in with the [data]
 * @param processPostLogin the process, after the bot-builder has been logged in with the [data]
 * @param processPostProcess the process, after every internal operation finished
 * @author Fruxz
 * @since 1.0
 */
data class Bot(
	var data: BotCredentials,
	var appearance: BotAppearance,
	var processPreProcess: Bot.() -> Unit = { },
	var processPreSetup: DiscordApiBuilder.() -> Unit = { },
	var processPreLogin: DiscordApiBuilder.() -> Unit = { },
	var processPostLogin: DiscordApi.() -> Unit = { },
	var processPostProcess: DiscordApi.() -> Unit = { },
) {

	companion object Running {

		/**
		 * The currently running [DiscordApi] of the discord-bot instance.
		 * @author Fruxz
		 * @since 1.0
		 */
		lateinit var instance: DiscordApi
			internal set

	}

}
