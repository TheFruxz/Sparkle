package de.moltenKt.javacord.extension

import de.moltenKt.jvm.extension.isNotNull
import de.moltenKt.jvm.extension.tryOrNull
import org.javacord.api.entity.emoji.CustomEmojiBuilder
import org.javacord.api.entity.emoji.KnownCustomEmoji
import org.javacord.api.entity.server.Server

/**
 * Returns if an emoji with the [emojiName] exists in the [server].
 * @param emojiName The name of the emoji.
 * @param ignoreCase defines if the search is non-case-sensitive.
 * @return true if the emoji exists, false otherwise.
 * @author Fruxz
 * @since 1.0
 */
fun Server.isCustomEmojiExisting(emojiName: String, ignoreCase: Boolean = false) =
	getCustomEmoji(emojiName, ignoreCase).isNotNull

/**
 * Returns if an emoji with the [id] exists / stored at the server.
 * @param id the id of the custom emoji
 * @return true if the emoji exists, false otherwise.
 * @author Fruxz
 * @since 1.0
 */
fun Server.isCustomEmojiExisting(id: Long) =
	getCustomEmoji(id).isNotNull

/**
 * Returns the [KnownCustomEmoji] with the given name or null if it does not exist.
 * The [ignoreCase] makes the search ignoreCase or if false it is case-sensitive.
 * @param emojiName The name of the emoji.
 * @param ignoreCase if the search is non-case-sensitive.
 * @return The [KnownCustomEmoji] with the given name or null if it does not exist.
 * @author Fruxz
 * @since 1.0
 */
fun Server.getCustomEmoji(emojiName: String, ignoreCase: Boolean = false) =
	(if (ignoreCase) getCustomEmojisByNameIgnoreCase(emojiName).filterNotNull() else getCustomEmojisByName(emojiName).filterNotNull()).firstOrNull()

/**
 * Returns the [KnownCustomEmoji] with the given [emojiId] or null if no emoji with the given [emojiId] exists.
 * @param emojiId the id of the emoji stored on the server
 * @return the emoji or null if not exists
 * @author Fruxz
 * @since 1.0
 */
fun Server.getCustomEmoji(emojiId: Long) = tryOrNull { getCustomEmojiById(emojiId).get() }

/**
 * Creates a custom emoji using the [CustomEmojiBuilder] and returns the created emoji. If an emoji with the name [emojiName]
 * already exists, the property [replaceExisting] will decide, if the new one will be created besides the existing one,
 * or if the existing one will be immediately be returned without the creation of a new emoji.
 * @param emojiName the (new) name of the emoji.
 * @param resource the image file of the emoji (png) as a byte array
 * @param replaceExisting if true it will create a new emoji named with [emojiName] + some other stuff or if false it returns the already existing one
 * @param process a custom function which can be used to modify the [CustomEmojiBuilder] after all parameters set into it.
 * @return the newly created one or the already existing one if [replaceExisting] is false
 * @author Fruxz
 * @since 1.0
 */
fun Server.createCustomEmoji(
	emojiName: String,
	resource: ByteArray,
	replaceExisting: Boolean = false,
	process: CustomEmojiBuilder.() -> Unit = {}
) = if (replaceExisting || !isCustomEmojiExisting(emojiName)) {
	CustomEmojiBuilder(this)
		.setName(emojiName)
		.setImage(resource)
		.apply(process)
		.create()
		.join()
} else
	getCustomEmoji(emojiName)

/**
 * Creates a custom emoji using the [CustomEmojiBuilder] and returns the created emoji. If an emoji with the name [emojiName]
 * already exists, the existing emoji will be returned and no new emoji will be created and stored on the server.
 * @param emojiName the (new) name of the emoji
 * @param resource the image file of the emoji (png) as a byte array
 * @param process a custom function which can be used to modify the [CustomEmojiBuilder] after all parameters set into it.
 * @return the created or already existing emoji
 * @author Fruxz
 * @since 1.0
 */
fun Server.createCustomEmojiIfNotExists(
	emojiName: String,
	resource: ByteArray,
	process: CustomEmojiBuilder.() -> Unit = {}
): KnownCustomEmoji = with(getCustomEmoji(emojiName)) {
    return@with this
        ?: CustomEmojiBuilder(this@createCustomEmojiIfNotExists)
            .setName(emojiName)
            .setImage(resource)
            .apply(process)
            .create()
            .join()
}

/**
 * Removes every custom emoji from the server, where the condition is returning true.
 * Every emoji on the server get piped through a forEach and the condition is applied & checked.
 * If the condition is true, the emoji will be removed, if false the emoji will be skipped.
 * @param condition the condition, which will be applied to every single customEmoji
 * @author Fruxz
 * @since 1.0
 */
fun Server.removeCustomEmoji(condition: (KnownCustomEmoji) -> Boolean) = customEmojis.forEach {
	if (condition(it)) {
		it.delete().join()
	}
}

/**
 * Removes every custom emoji from the server, which has the name [byName].
 * @param byName the exact name of the custom emoji, which you want to remove.
 * @author Fruxz
 * @since 1.0
 */
fun Server.removeCustomEmoji(byName: String) = removeCustomEmoji { it.name == byName }

/**
 * Removes every custom emoji from the server, which has the id [byId].
 * @param byId the id long of the emoji, which you want to remove.
 * @author Fruxz
 * @since 1.0
 */
fun Server.removeCustomEmoji(byId: Long) = removeCustomEmoji { it.id == byId }
