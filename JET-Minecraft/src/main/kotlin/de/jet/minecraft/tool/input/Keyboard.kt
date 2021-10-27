package de.jet.minecraft.tool.input

import de.jet.library.extension.collection.skip
import de.jet.library.extension.paper.getOfflinePlayer
import de.jet.library.extension.tag.PromisingData
import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.type.Breakable
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.app.JetData
import de.jet.minecraft.extension.display.ui.buildPanel
import de.jet.minecraft.extension.display.ui.item
import de.jet.minecraft.extension.special.texturedSkull
import de.jet.minecraft.tool.display.item.Item
import de.jet.minecraft.tool.display.item.quirk.Quirk.Companion.skullQuirk
import de.jet.minecraft.tool.display.ui.panel.Panel
import de.jet.minecraft.tool.input.Keyboard.Extension
import de.jet.minecraft.tool.input.Keyboard.Type
import de.jet.minecraft.tool.input.Keyboard.Type.ANY
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
fun awaitKeyboardInput(target: HumanEntity, keyboardType: Type = ANY, message: String = "", vararg extensions: Extension = emptyArray()): Breakable<Keyboard, String>? {
	// TODO: 27.10.2021 register keyboard request (use UUID for multiple uses of the keyboard at a time)
	return null // placeholder
}

fun <T : HumanEntity> T.requestKeyboardInput(keyboardType: Type = ANY, message: String = "", vararg extensions: Extension = emptyArray()) =
	awaitKeyboardInput(this, keyboardType, message, *extensions)

fun demo() {
	/*awaitKeyboardInput()
		.atSuccess {

		}
		.atBreak {

		}*/
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

	object RunningEngine {

		// todo @Serializable, no real reason, only for the convenience
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
		data class Key(
			override val identity: String,
			val textureIdentity: Int,
			val displayTitle: String,
			val displayInline: String,
			val write: String = displayInline,
		) : Identifiable<Key>

		@Serializable
		data class KeyConfiguration(
			val keys: List<Key>
		) : PromisingData

		private val renderKeys = JetData.keyConfig.content.keys

		fun renderKeyboard(holder: HumanEntity, keyboardType: Type = ANY, message: String = "", vararg extensions: Extension = emptyArray()): RenderingState {

			fun generateKey(key: String): Item {
				val keyObject = renderKeys.firstOrNull { it.identity == key.uppercase() }

				if (keyObject != null) {

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

				} else
					throw NoSuchElementException("No key '$key' registered!")

			}

			fun renderBasePlate() = buildPanel(lines = 6) {

				this[0..8] = Material.YELLOW_STAINED_GLASS_PANE.item.blankLabel() // current input visualizer

				placeInner(innerSlots, Material.GRAY_STAINED_GLASS_PANE.item.blankLabel()) // inner key-marking

				this[48..50] = generateKey("_BLANK").apply {
					label = "SPACE"
					skullQuirk {
						owningPlayer = getOfflinePlayer("MHF_Wood")
					}
				}

				this[46] = generateKey("_ARROW-DOUBLE-LEFT").skullQuirk { owningPlayer = getOfflinePlayer("MHF_ArrowLeft") }
				this[47] = generateKey("_ARROW-LEFT").skullQuirk { owningPlayer = getOfflinePlayer("MHF_ArrowLeft") }

				this[51] = generateKey("_ARROW-RIGHT").skullQuirk { owningPlayer = getOfflinePlayer("MHF_ArrowRight") }
				this[52] = generateKey("_ARROW-DOUBLE-RIGHT").skullQuirk { owningPlayer = getOfflinePlayer("MHF_ArrowRight") }

			}

			// main

			val main = renderBasePlate().apply {

				// main keyboard exclusive primary keys

				val primaryKeys = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

				innerSlots.skip(21, 27).withIndex().forEach { (index, value) ->
					placeInner(value, generateKey(primaryKeys[index].toString()))
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