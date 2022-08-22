package de.moltenKt.paper.app.component.chat

import de.moltenKt.core.extension.data.fromJson
import de.moltenKt.core.extension.data.toJson
import de.moltenKt.core.extension.tryOrNull
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.paper.pluginOrNull
import de.moltenKt.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.moltenKt.paper.structure.component.SmartComponent
import de.moltenKt.paper.tool.data.file.MoltenFileSystem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import org.bukkit.Bukkit
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import kotlin.io.path.readText
import kotlin.io.path.writeText

class ChatComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Chat"

	override suspend fun component() {

		listener(ChatListener())

		additionalRegister {
			instance = this
		}

		additionalStart {

			setupPath.parent.createDirectories()

			setup = tryOrNull { setupPath.readText().fromJson() } ?: ChatSetup().also {
				setupPath.writeText(it.toJson())
			}

			pluginOrNull("PlaceholderAPI")?.let {
				usePlaceholderAPI = true
				debugLog { "PlaceholderAPI ${it.description.version} found by ChatComponent!" }
			} ?: debugLog { "ChatComponent unable to find PlaceholderAPI, skipping custom placeholders!" }
			
		}

	}

	companion object {

		private lateinit var instance: ChatComponent

		private val setupPath by lazy { MoltenFileSystem.componentPath(instance.identityObject) / "setup.json" }

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