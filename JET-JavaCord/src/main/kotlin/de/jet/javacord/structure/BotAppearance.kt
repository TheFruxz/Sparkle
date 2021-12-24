package de.jet.javacord.structure

import org.javacord.api.entity.user.UserStatus

/**
 * This data class represents the public appearance of the bot.
 * @param displayName The display name of the bot, which will be used in the public user list of every server.
 * @param status The status of the bot, which will be displayed under the display name.
 * @param activity The current activity of the bot.
 * @param avatar The avatar of the bot.
 * @author Fruxz
 * @since 1.0
 */
data class BotAppearance(
	var displayName: String? = null,
	var status: UserStatus? = null,
	var activity: BotActivity = BotActivity(),
	var avatar: BotAvatar = BotAvatar(),
)