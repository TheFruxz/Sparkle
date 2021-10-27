package de.jet.minecraft.tool.input

import de.jet.library.extension.collection.skip
import de.jet.library.extension.paper.getOfflinePlayer
import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.type.Breakable
import de.jet.minecraft.app.JetCache
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

		private val renderKeys = listOf(
			Key("A", 44004, "A", "A"),
			Key("B", 44003, "B", "B"),
			Key("C", 44002, "C", "C"),
			Key("D", 44001, "D", "D"),
			Key("E", 44000, "E", "E"),
			Key("F", 43999, "F", "F"),
			Key("G", 43998, "G", "G"),
			Key("H", 43997, "H", "H"),
			Key("I", 43996, "I", "I"),
			Key("J", 43995, "J", "J"),
			Key("K", 43994, "K", "K"),
			Key("L", 43993, "L", "L"),
			Key("M", 43992, "M", "M"),
			Key("N", 43991, "N", "N"),
			Key("O", 43990, "O", "O"),
			Key("P", 43989, "P", "P"),
			Key("Q", 43988, "Q", "Q"),
			Key("R", 43987, "R", "R"),
			Key("S", 43986, "S", "S"),
			Key("T", 43985, "T", "T"),
			Key("U", 43984, "U", "U"),
			Key("V", 43983, "V", "V"),
			Key("W", 43982, "W", "W"),
			Key("X", 43981, "X", "X"),
			Key("Y", 43980, "Y", "Y"),
			Key("Z", 43979, "Z", "Z"),

			// German (here, until extension exists for this)
			Key("Ü", 44006, "Ü", "Ü"),
			Key("Ö", 44007, "Ö", "Ö"),
			Key("Ä", 44008, "Ä", "Ä"),

			// Numbers
			Key("0", 44061, "0", "0"),
			Key("1", 44060, "1", "1"),
			Key("2", 44059, "2", "2"),
			Key("3", 44058, "3", "3"),
			Key("4", 44057, "4", "4"),
			Key("5", 44056, "5", "5"),
			Key("6", 44055, "6", "6"),
			Key("7", 44054, "7", "7"),
			Key("8", 44053, "8", "8"),
			Key("9", 44052, "9", "9"),

			// Symbols
			Key("@", 44085, "@", "@"),
			Key("#", 44088, "#", "#"),
			Key("_", 44089, "_", "_"),
			Key("=", 44090, "=", "="),
			Key("°", 44091, "°", "°"),
			Key("+", 44092, "+", "+"),
			Key("%", 44093, "%", "%"),
			Key("-", 44094, "-", "-"),
			Key("...", 44095, "...", "..."),
			Key("\"", 44099, "\n", "\n"),
			Key("?", 44101, "?", "?"),
			Key("!", 44103, "!", "!"),
			Key("&", 44104, "&", "&"),
			Key(":", 44105, ":", ":"),
			Key(";", 44106, ";", ";"),
			Key(",", 44107, ",", ","),
			Key(".", 44108, ".", "."),
			Key("}", 44022, "}", "}"),
			Key("{", 44023, "{", "{"),
			Key("]", 44024, "]", "]"),
			Key("[", 44025, "[", "["),
			Key(")", 44026, ")", ")"),
			Key("(", 44027, "(", "("),
			Key("/", 44029, "/", "/"),
			Key("\\", 44028, "\\", "\\"),

			// Special Symbols
			Key("_REFRESH", 44086, "Refresh", "↺"),
			Key("_CHECK", 44087, "Check", "✔︎"),
			Key("_BLANK", 44005, "Blank", " "),

			// Special Symbols without direct use
			Key("_ARROW-FORWARD", 44011, ">", ">"),
			Key("_ARROW-BACKWARD", 44013, "<", "<"),
			Key("_ARROW-RIGHT-UP", 44014, "?>", "?>"),
			Key("_ARROW-RIGHT-DOWN", 44015, "?>", "?>"),
			Key("_ARROW-DOUBLE-RIGHT", 44009, ">>", ">>"),
			Key("_ARROW-RIGHT", 44018, "▶", "▶"),
			Key("_ARROW-LEFT-UP", 44016, "?<", "?<"),
			Key("_ARROW-LEFT-DOWN", 44017, "?<", "?<"),
			Key("_ARROW-DOUBLE-LEFT", 44012, "<<", "<<"),
			Key("_ARROW-LEFT", 44019, "◀", "◀"),
			Key("_ARROW-UP", 44020, "▲", "▲"),
			Key("_ARROW-DOWN", 40021, "▼", "▼"),

		)

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