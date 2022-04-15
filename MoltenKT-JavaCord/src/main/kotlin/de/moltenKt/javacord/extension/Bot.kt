package de.moltenKt.javacord.extension

import de.moltenKt.javacord.app.DiscordBotExtension
import de.moltenKt.javacord.structure.Bot
import de.moltenKt.javacord.structure.BotActivity
import de.moltenKt.javacord.structure.BotAppearance
import de.moltenKt.javacord.structure.BotCredentials
import org.javacord.api.AccountType.BOT
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder

/**
 * Creates & registers a new running discord bot instance.
 * Uses the following functions, to define the properties of the bot:
 * - [Bot.credentials] to define the credentials of the bot
 * - [Bot.appearance] to define the visuals of the bot
 * - [Bot.appearance] > [BotAppearance.activity] to define the visible activity of the bot
 *
 * And uses the following functions to modify the different process during the runtime & setup of the bot:
 * [preProcess]; [preSetup]; [preLogin]; [postLogin]; [postProcess]!
 *
 * **Note:** The [preProcess] function is called before the [preSetup] function.
 *
 * **Register:** With this function, the bot is registered to the [Bot.Running.instance]!
 *
 * **Access:** Access the bot state with the following functions:
 * - [talkDiscord] to access the bot in the *it* context
 * - [withTalkDiscord] to access the bot in the *this* context
 *
 * @param process the modification process called directly after the [Bot] instance is created (first modification)
 * @author Fruxz
 * @since 1.0
 */
fun runBot(process: Bot.() -> Unit) {
	Bot(BotCredentials(), BotAppearance()).apply(process).let { preData ->
		val data = preData.apply(preData.processPreProcess)

		Bot.instance = DiscordApiBuilder()
			.apply(data.processPreSetup)
			.setToken(data.data.token)
			.setAccountType(BOT)
			.apply(data.processPreLogin)
			.login()
			.join()

		Bot.instance.apply {
			apply(data.processPostLogin)
			apply(data.appearance.avatar.application)
			updateStatus(data.appearance.status)
			addServerBecomesAvailableListener {
				it.api.yourself.updateNickname(it.server, data.appearance.displayName)
			}
			data.appearance.activity.applyToBot(this)
			apply(data.processPostProcess)
			apply {
				servers().forEach {
					yourself.updateNickname(it, data.appearance.displayName)
				}
			}
		}

	}
}

/**
 * Communicates with the current running bot instance.
 * @param instance the current running bot instance, default: [Bot.Running.instance]
 * @param process the call
 * @author Fruxz
 * @since 1.0
 */
fun <T, I : T> talkDiscord(instance: DiscordApi = Bot.instance, process: (DiscordApi) -> I): T = process(instance)

/**
 * Communicates with the current running bot instance.
 * @param instance the current running bot instance, default: [Bot.Running.instance]
 * @param process the call
 * @author Fruxz
 * @since 1.0
 */
fun <T, I : T> withTalkDiscord(instance: DiscordApi = Bot.instance, process: DiscordApi.() -> I): T = talkDiscord<T, I>(instance, process)

/**
 * Returning the current registered running [Bot.instance],
 * which get used by the [DiscordBotExtension] and so the
 * [talkDiscord] and [withTalkDiscord] as variable default
 * state parameters too!
 *
 * It uses a lateinit variable, so if the extension is not
 * properly registered, the bot instance will not exist!
 * @author Fruxz
 * @since 1.0
 */
val talkDiscord: DiscordApi
	get() = Bot.instance

/**
 * The credential setup of the discord bot
 * @param process the modification process
 * @author Fruxz
 * @since 1.0
 */
fun Bot.credentials(process: BotCredentials.() -> Unit) = apply {
	data = data.apply(process)
}

/**
 * The appearance setup of the discord bot
 * @param process the modification process
 * @author Fruxz
 * @since 1.0
 */
fun Bot.appearance(process: BotAppearance.() -> Unit) = apply {
	appearance = appearance.apply(process)
}

/**
 * The activity setup of the discord bot
 * @param process the modification process
 * @author Fruxz
 * @since 1.0
 */
fun BotAppearance.activity(process: BotActivity.() -> Unit) = apply {
	activity = activity.apply(process)
}

/**
 * Replaces the preProcess-function of the [Bot] with the given [process]
 */
fun Bot.preProcess(process: Bot.() -> Unit) = apply {
	processPreProcess = process
}

/**
 * Replaces the preSetup-function of the [Bot] with the given [process]
 */
fun Bot.preSetup(process: DiscordApiBuilder.() -> Unit) = apply {
	processPreSetup = process
}

/**
 * Replaces the preLogin-function of the [Bot] with the given [process]
 */
fun Bot.preLogin(process: DiscordApiBuilder.() -> Unit) = apply {
	processPreLogin = process
}

/**
 * Replaces the postLogin-function of the [Bot] with the given [process]
 */
fun Bot.postLogin(process: DiscordApi.() -> Unit) = apply {
	processPostLogin = process
}

/**
 * Replaces the postProcess-function of the [Bot] with the given [process]
 */
fun Bot.postProcess(process: DiscordApi.() -> Unit) = apply {
	processPostProcess = process
}