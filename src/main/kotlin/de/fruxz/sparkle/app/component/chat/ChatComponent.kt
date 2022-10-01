package de.fruxz.sparkle.app.component.chat

import de.fruxz.ascend.extension.data.readJson
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.extension.debugLog
import de.fruxz.sparkle.extension.paper.pluginOrNull
import de.fruxz.sparkle.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.structure.component.SmartComponent
import de.fruxz.sparkle.tool.data.file.SparklePath
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import kotlin.io.path.createDirectories
import kotlin.io.path.div

/**
 * This component manages the chat and chat extensions via the native & Placeholder APIs.
 * @author Fruxz
 * @author CoasterFreakDE
 * @since 1.0
 */
class ChatComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Chat"

	override suspend fun component() {

		listener(ChatListener())

		additionalRegister {
			instance = this
		}

		additionalStart {

			setupPath.parent.createDirectories()

			setup = tryOrNull { setupPath.readJson() } ?: ChatSetup().also {
				setupPath.writeJson(it)
			}

			pluginOrNull("PlaceholderAPI")?.let {
				usePlaceholderAPI = true
				debugLog { "PlaceholderAPI ${it.description.version} found by ChatComponent!" }
			} ?: debugLog { "ChatComponent unable to find PlaceholderAPI, skipping custom placeholders!" }

		}

	}

	companion object {

		private lateinit var instance: ChatComponent

		private val setupPath by lazy { SparklePath.componentPath(instance) / "setup.json" }

		internal lateinit var setup: ChatSetup

		/**
		 * The consumer, which will be used in a [Component.replaceText] block,
		 * directly after the Mention, HashTag, Command and Item processing.
		 * (Input is the whole message, with the stuff above applied)
		 * @author Fruxz
		 * @since 1.0
		 */
		var chatExtensions: Set<(TextReplacementConfig.Builder) -> Unit> = setOf()


		/**
		 * If true, the PlaceholderAPI is used for the Placeholders.
		 * @author CoasterFreakDE
		 * @since 1.0
		 */
		var usePlaceholderAPI: Boolean = false
	}

}