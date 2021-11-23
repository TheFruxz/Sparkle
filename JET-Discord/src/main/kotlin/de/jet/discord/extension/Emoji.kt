package de.jet.discord.extension

import de.jet.library.extension.isNotNull
import org.javacord.api.entity.server.Server

fun Server.isCustomEmojiExisting(emojiName: String, ignoreCase: Boolean = false) =
    getCustomEmoji(emojiName, ignoreCase)

fun Server.isCustomEmojiExisting(id: Long) =
    getCustomEmoji(id).isNotNull

fun Server.getCustomEmoji(emojiName: String, ignoreCase: Boolean) =
    (if (ignoreCase) getCustomEmojisByNameIgnoreCase(emojiName) else getCustomEmojisByName(emojiName)).firstOrNull()

fun Server.getCustomEmoji(id: Long) = try {
    getCustomEmojiById(id).get()
} catch (exception: NoSuchElementException) {
    null
}

fun Server.createCustomEmoji(emojiName: String, resource: ByteArray, replaceExisting: Boolean = false) {
    if (replaceExisting || !isCustomEmojiExisting(emojiName)) {
        
    }
}
