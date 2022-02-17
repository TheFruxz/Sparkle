@file:Suppress("UNUSED_PARAMETER")

package de.jet.paper.tool.input

import de.jet.jvm.extension.container.skip
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.jvm.tool.smart.type.Breakable
import de.jet.paper.app.JetCache
import de.jet.paper.app.JetData
import de.jet.paper.extension.display.ui.buildPanel
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.paper.getOfflinePlayer
import de.jet.paper.extension.external.texturedSkull
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.item.quirk.Quirk.Companion.skullQuirk
import de.jet.paper.tool.display.ui.panel.Panel
import de.jet.paper.tool.input.Keyboard.Extension
import de.jet.paper.tool.input.Keyboard.Type
import de.jet.paper.tool.input.Keyboard.Type.ANY
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import java.util.*

/**
 * @param target the player, which receives the keyboard UI, also the ability to send some text to this result
 * @param keyboardType the possible input type
 * @param message the title-content of the keyboard UI
 * @param extensions the extensions, that defines the UI and capabilities of the keyboard (***BETA***)
 * @return [Breakable]<[Keyboard],[String]> the keyboard object & the last input, which got entered (empty or unfinished if break is called!)
 */
fun <T : HumanEntity> requestKeyboardInput(target: T, keyboardType: Type = ANY, message: String = "", requestIdentity: Identity<Keyboard> = Identity("${UUID.randomUUID()}"), vararg extensions: Extension = emptyArray(), onReaction: (reactor: T, reaction: String) -> Unit): Breakable<Keyboard, String>? {
	// TODO: 27.10.2021 register keyboard request (use UUID for multiple uses of the keyboard at a time)
	return null // placeholder
}

@JvmName("entityRequestKeyboardInput")
fun <T : HumanEntity> T.requestKeyboardInput(keyboardType: Type = ANY, message: String = "", requestIdentity: Identity<Keyboard> = Identity("${UUID.randomUUID()}"), vararg extensions: Extension = emptyArray(), onReaction: (reactor: T, reaction: String) -> Unit) =
	requestKeyboardInput(this, keyboardType, message, requestIdentity, *extensions) { reactor, reaction ->
		onReaction(reactor, reaction)
	}

object Keyboard {

	/**
	 * @param ANY everything
	 * @param STRING text-based (no colors, etc.)
	 * @param INT integers & longs
	 * @param DOUBLE doubles & floats
	 * @param MESSAGE text-based (with colors, etc.)
	 */
	enum class Type {
		ANY, STRING, INT, DOUBLE, MESSAGE;
	}

	/**
	 * WIP
	 */
	sealed interface Extension

	data class KeyboardRequest<T : HumanEntity>(
		val holder: T,
		val keyboardType: Type,
		val message: String,
		val extensions: List<Extension>,
		val reaction: (reactor: T, reaction: String) -> Unit,
	)

	object RunningEngine {

		data class PlayerKeyboardPort(
			val player: UUID,
			val keyboard: UUID,
		) {

			var cachedInput: String
				get() = JetCache.runningKeyboards.toList().firstOrNull { it.first.keyboard == keyboard && it.first.player == player }?.second ?: ""
				set(value) {
					JetCache.runningKeyboards[this] = value
				}

		}

		fun getRemoteKeyboardState(player: UUID, keyboard: UUID) =
			PlayerKeyboardPort(player, keyboard)

	}

	object RenderEngine {
		data class RenderingState(
			val mainKeyboard: Panel,
			val symbolKeyboard: Panel,
			val numberKeyboard: Panel,
			val extensionKeyboard: Panel,
		)

		/**
		 * @param identity the unique identity of this key
		 * @param textureIdentity the skull texture id
		 * @param displayTitle the display-name (item-name) of this key
		 * @param displayInline the representative-name of this key (e.g. colored box to describe a color)
		 * @param write the text written from this key, default the [displayInline] value, but configurable if this does not match (e.g. the colored box)
		 */
		@Serializable
		@SerialName("KeyboardKey")
		data class Key(
			override val identity: String,
			val textureIdentity: Int,
			val displayTitle: String,
			val displayInline: String,
			val write: String = displayInline,
		) : Identifiable<Key>

		@Serializable
		@SerialName("KeyboardKeyConfiguration")
		data class KeyConfiguration(
			val darkModeKeys: List<Key>,
			val lightModeKeys: List<Key>,
		)

		private val renderKeys = JetData.keyConfig.content.lightModeKeys

		fun renderKey(keyObject: Key): Item {
			return texturedSkull(keyObject.textureIdentity).apply {
				label = "§7Key: §6${keyObject.displayTitle}"
				lore = buildString {

					appendLine()
					appendLine("§7The §e${keyObject.displayTitle}§7 Key inserts")
					appendLine("§7a new '§6${keyObject.displayInline}§7'!")
					appendLine()
					append("§8${keyObject.identity}")

				}
			}
		}

		fun renderKey(key: String): Item {
			val keyObject = renderKeys.firstOrNull { it.identity == key.uppercase() }

			if (keyObject != null) {
				return renderKey(keyObject)
			} else
				throw NoSuchElementException("No key '$key' registered!")
		}

		fun renderKeyboard(holder: HumanEntity, keyboardType: Type = ANY, message: String = "", vararg extensions: Extension = emptyArray()): RenderingState {

			fun renderBasePlate() = buildPanel(lines = 6) {

				this[0..8] = Material.YELLOW_STAINED_GLASS_PANE.item.blankLabel() // current input visualizer

				placeInner(innerSlots, Material.GRAY_STAINED_GLASS_PANE.item.blankLabel()) // inner key-marking

				this[48..50] = renderKey("_BLANK").putLabel("SPACE")

				this[46] = renderKey("_ARROW-DOUBLE-LEFT").skullQuirk { owningPlayer = getOfflinePlayer("MHF_ArrowLeft") }
				this[47] = renderKey("_ARROW-LEFT").skullQuirk { owningPlayer = getOfflinePlayer("MHF_ArrowLeft") }

				this[51] = renderKey("_ARROW-RIGHT").skullQuirk { owningPlayer = getOfflinePlayer("MHF_ArrowRight") }
				this[52] = renderKey("_ARROW-DOUBLE-RIGHT").skullQuirk { owningPlayer = getOfflinePlayer("MHF_ArrowRight") }

			}

			// main

			val main = renderBasePlate().apply {

				// main keyboard exclusive primary keys

				val primaryKeys = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

				innerSlots.skip(21, 27).withIndex().forEach { (index, value) ->
					placeInner(value, renderKey(primaryKeys[index].toString()))
				}

			}

			return RenderingState(
				main,
				main, // placeholder
				main, // placeholder
				main, // placeholder
			)

		}

	}

}