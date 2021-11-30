package de.jet.javacord.extension

import de.jet.javacord.structure.Bot
import de.jet.javacord.structure.BotActivity
import de.jet.javacord.structure.BotAppearance
import de.jet.javacord.structure.BotCredentials
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.event.server.ServerBecomesAvailableEvent
import org.javacord.api.listener.server.ServerBecomesAvailableListener

lateinit var discordApiState: DiscordApi

fun runBot(process: Bot.() -> Unit) {
	Bot(BotCredentials(), BotAppearance()).apply(process).let { preData ->
		val data = preData.apply(preData.processPreProcess)

		DiscordApiBuilder()
			.apply(data.processPreSetup)
			.setToken(data.data.botToken)
			.apply(data.processPreLogin)
			.login()
			.join()
			.apply {
				apply(data.processPostLogin)
				apply(data.appearance.avatar.application)
				updateStatus(data.appearance.status)
				addServerBecomesAvailableListener {
					yourself.updateNickname(it?.server, data.appearance.displayName)
				}
				data.appearance.activity.applyToBot(this)
				apply(data.processPostProcess)
			}

	}.also {
		discordApiState = it
	}
}

fun Bot.credentials(process: BotCredentials.() -> Unit) = apply {
	data = data.apply(process)
}

fun Bot.appearance(process: BotAppearance.() -> Unit) = apply {
	appearance = appearance.apply(process)
}

fun BotAppearance.activity(process: BotActivity.() -> Unit) = apply {
	activity = activity.apply(process)
}

fun Bot.preProcess(process: Bot.() -> Unit) = apply {
	processPreProcess = process
}

fun Bot.preSetup(process: DiscordApiBuilder.() -> Unit) = apply {
	processPreSetup = process
}

fun Bot.preLogin(process: DiscordApiBuilder.() -> Unit) = apply {
	processPreLogin = process
}

fun Bot.postLogin(process: DiscordApi.() -> Unit) = apply {
	processPostLogin = process
}

fun Bot.postProcess(process: DiscordApi.() -> Unit) = apply {
	processPostProcess = process
}