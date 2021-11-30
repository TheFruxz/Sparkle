package de.jet.javacord.structure

import org.javacord.api.DiscordApi
import org.javacord.api.entity.activity.ActivityType
import java.net.URL

/**
 * This class represents a bot activity.
 * If a streamingURL is set, the activity will be forced to be a streaming activity.
 * @author Fruxz
 * @since 1.0
 */
class BotActivity {

	var name: String? = null
	var activityType: ActivityType? = null
	var streamingUrl: URL? = null

	constructor()

	constructor(name: String) {
		this.name = name
	}

	constructor(activityType: ActivityType, name: String) {
		this.activityType = activityType
		this.name = name
	}

	constructor(name: String, streamingUrl: URL) {
		this.name = name
		this.streamingUrl = streamingUrl
	}

	fun applyToBot(bot: DiscordApi) = when {
		streamingUrl != null -> bot.updateActivity(name, streamingUrl?.path)
		activityType != null -> bot.updateActivity(activityType, name)
		else -> bot.updateActivity(name)
	}

}