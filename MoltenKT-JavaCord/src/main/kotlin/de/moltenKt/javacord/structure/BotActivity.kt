package de.moltenKt.javacord.structure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.javacord.api.DiscordApi
import org.javacord.api.entity.activity.ActivityType
import java.net.URL

/**
 * This class represents a bot activity.
 * If a streamingURL is set, the activity will be forced to be a streaming activity.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("BotActivity")
class BotActivity internal constructor(
	var name: String? = null,
	var activityType: ActivityType? = null,
	var streamingUrl: String? = null,
) {

	constructor() : this(null, null, null)

	constructor(name: String) : this(name, null, null)

	constructor(activityType: ActivityType, name: String) : this(name, activityType, null)

	constructor(name: String, streamingUrl: URL) : this(name, null, streamingUrl.path)

	/**
	 * This function applies the activity data to the [bot].
	 * *if [streamingUrl] is not null* -> stream-activity
	 * *if [activityType] is not null* -> [activityType] + [name]
	 * *else* -> [name]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun applyToBot(bot: DiscordApi) = when {
		streamingUrl != null -> bot.updateActivity(name, streamingUrl)
		activityType != null -> bot.updateActivity(activityType, name)
		else -> bot.updateActivity(name)
	}

}