package de.jet.javacord.structure

import org.javacord.api.DiscordApi
import org.javacord.api.entity.Icon
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import java.net.URL

/**
 * This class helps to easily create/manage an avatar for the discord-api bot,
 * using the different constructors.
 * @author Fruxz
 * @since 1.0
 */
class BotAvatar {

	internal val application: DiscordApi.() -> Unit

	constructor() {
		application = { }
	}

	constructor(bufferedImageAvatar: BufferedImage) {
		application = {
			updateAvatar(bufferedImageAvatar)
		}
	}

	constructor(bufferedImageAvatar: BufferedImage, fileType: String) {
		application = {
			updateAvatar(bufferedImageAvatar, fileType)
		}
	}

	constructor(fileAvatar: File) {
		application = {
			updateAvatar(fileAvatar)
		}
	}

	constructor(iconAvatar: Icon) {
		application = {
			updateAvatar(iconAvatar)
		}
	}

	constructor(urlAvatar: URL) {
		application = {
			updateAvatar(urlAvatar)
		}
	}

	constructor(byteArrayAvatar: ByteArray) {
		application = {
			updateAvatar(byteArrayAvatar)
		}
	}

	constructor(byteArrayAvatar: ByteArray, fileType: String) {
		application = {
			updateAvatar(byteArrayAvatar, fileType)
		}
	}

	constructor(inputStreamAvatar: InputStream) {
		application = {
			updateAvatar(inputStreamAvatar)
		}
	}


	constructor(inputStreamAvatar: InputStream, fileType: String) {
		application = {
			updateAvatar(inputStreamAvatar, fileType)
		}
	}

}