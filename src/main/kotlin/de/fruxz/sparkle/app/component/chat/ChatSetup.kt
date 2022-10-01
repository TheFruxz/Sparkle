package de.fruxz.sparkle.app.component.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatSetup(
	val chatFormat: String = "<dark_gray>▶ <aqua>[displayName]<dark_gray> » [message]",
	val messageColor: String = "gray",
	val allowExtensions: Boolean = true,
	val mentions: MentionProperty = MentionProperty(),
	val hashTags: HashTagProperty = HashTagProperty(),
	val commands: CommandProperty = CommandProperty(),
	val items: ItemProperty = ItemProperty(),
) {

	@Serializable
	data class MentionProperty(
		val enabled: Boolean = true,
		val mentionColor: String = "yellow",
	)

	@Serializable
	data class HashTagProperty(
		val enabled: Boolean = true,
		val hashTagColor: String = "yellow",
	)

	@Serializable
	data class CommandProperty(
		val enabled: Boolean = true,
		val commandColor: String = "aqua",
	)

	@Serializable
	data class ItemProperty(
		val enabled: Boolean = true,
		val itemColor: String = "gray",
	)

}
