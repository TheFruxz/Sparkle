package de.jet.discord.extension

import de.jet.library.extension.isNotNull
import org.javacord.api.entity.emoji.CustomEmojiBuilder
import org.javacord.api.entity.emoji.KnownCustomEmoji
import org.javacord.api.entity.server.Server

fun Server.isCustomEmojiExisting(emojiName: String, ignoreCase: Boolean = false) =
    getCustomEmoji(emojiName, ignoreCase).isNotNull

fun Server.isCustomEmojiExisting(id: Long) =
    getCustomEmoji(id).isNotNull

fun Server.getCustomEmoji(emojiName: String, ignoreCase: Boolean) =
    (if (ignoreCase) getCustomEmojisByNameIgnoreCase(emojiName) else getCustomEmojisByName(emojiName)).firstOrNull()

fun Server.getCustomEmoji(id: Long) = try {
    getCustomEmojiById(id).get()
} catch (exception: NoSuchElementException) {
    null
}

fun Server.createCustomEmoji(emojiName: String, resource: ByteArray, replaceExisting: Boolean = false, process: CustomEmojiBuilder.() -> Unit = {}) = if (replaceExisting || !isCustomEmojiExisting(emojiName)) {
    CustomEmojiBuilder(this)
        .setName(emojiName)
        .setImage(resource)
        .apply(process)
        .create()
        .join()
} else
    null

fun Server.createCustomEmojiIfNotExists(emojiName: String, resource: ByteArray, process: CustomEmojiBuilder.() -> Unit = {}): KnownCustomEmoji =
    CustomEmojiBuilder(this)
        .setName(emojiName)
        .setImage(resource)
        .apply(process)
        .create()
        .join()

fun Server.removeCustomEmoji(condition: (KnownCustomEmoji) -> Boolean) = customEmojis.forEach {
    if (condition(it)) {
        it.delete().join()
    }
}

fun Server.removeCustomEmoji(byName: String) = removeCustomEmoji { it.name == byName }

fun Server.removeCustomEmoji(byId: Long) = removeCustomEmoji { it.id == byId }
