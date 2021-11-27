package de.jet.minecraft.app

import de.jet.jvm.tool.conversion.Base64.decodeToString
import de.jet.minecraft.app.JetData.File.BRAIN
import de.jet.minecraft.app.JetData.File.CONFIG
import de.jet.minecraft.app.JetData.File.ESSENTIALS_CONFIG
import de.jet.minecraft.app.JetData.File.ESSENTIALS_WORLDS
import de.jet.minecraft.app.JetData.File.RAW
import de.jet.minecraft.app.component.essentials.point.PointConfig
import de.jet.minecraft.app.component.essentials.world.WorldConfig
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer
import de.jet.minecraft.extension.data.jetPath
import de.jet.minecraft.tool.data.DataTransformer
import de.jet.minecraft.tool.data.JetYamlFile
import de.jet.minecraft.tool.data.Preference
import de.jet.minecraft.tool.input.Keyboard.RenderEngine.Key
import de.jet.minecraft.tool.input.Keyboard.RenderEngine.KeyConfiguration

object JetData {

	val debugMode = Preference(
		file = CONFIG,
		useCache = true,
		path = jetPath("debugMode"),
		default = false,
	)

	val systemPrefix = Preference(
		file = CONFIG,
		useCache = true,
		path = jetPath("prefix"),
		default = "§6JET §8» ",
	).transformer(DataTransformer.simpleColorCode())

	val systemLanguage = Preference(
		file = CONFIG,
		path = jetPath("language"),
		default = "en_general",
	)

	val autoStartComponents = Preference(
		file = CONFIG,
		path = jetPath("autoStartComponents"),
		default = setOf<String>(),
	).transformer(DataTransformer.setCollection())

	val touchedComponents = Preference(
		file = BRAIN,
		path = jetPath("touchedComponents"),
		default = setOf<String>()
	).transformer(DataTransformer.setCollection())

	// SKULLS

	val skullDataURL = Preference(
		file = CONFIG,
		path = jetPath("skullData"),
		default = decodeToString(
			decodeToString(
				decodeToString(
					"WVVoU01HTklUVFpNZVRsMFlWYzFiRmt6U21oYWJsRjBZVWRXYUZwSVRYVlpNamwwVERKT2VtUnBPSGxOUkVsM1RGUkJlRXhVVFhoTVZXeFdXakZLYVZOdE9VbFZiVXBYWVVkd1RHSnJPWE5oTWpGSlREQk9NV016VW5aaVV6RkpXbGRHYTB4VlVrTk1iVTU2WkdjOVBRPT0="
				)
			)
		),
	)

	// ESSENTIALS component

	val savedPoints = Preference(
		file = ESSENTIALS_CONFIG,
		path = jetPath("savedPoints"),
		default = PointConfig(emptyList()),
	).transformer(DataTransformer.json())

	val worldConfig = Preference(
		file = ESSENTIALS_WORLDS,
		path = jetPath("worldConfig"),
		default = WorldConfig(emptyList(), emptyList()),
	).transformer(DataTransformer.json())

	val worldStructure = Preference(
		file = ESSENTIALS_WORLDS,
		path = jetPath("worldStructure"),
		default = WorldRenderer.renderBase(worldConfig.content).structure(),
	).transformer(DataTransformer.json())

	// Keyboard

	val keyConfig = Preference(
		file = RAW,
		path = jetPath("keyConfig"),
		default = KeyConfiguration(
			listOf(
				//<editor-fold desc="Description" defaultstate="collapsed">
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
				Key("_BLANK", 22757, "Blank", " "),

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
				//</editor-fold>
			),
			listOf(
				//<editor-fold desc="Description" defaultstate="collapsed">
				Key("A", 9297, "A", "A"),
				Key("B", 9296, "B", "B"),
				Key("C", 9295, "C", "C"),
				Key("D", 9294, "D", "D"),
				Key("E", 9293, "E", "E"),
				Key("F", 9292, "F", "F"),
				Key("G", 9291, "G", "G"),
				Key("H", 9290, "H", "H"),
				Key("I", 9289, "I", "I"),
				Key("J", 9288, "J", "J"),
				Key("K", 9287, "K", "K"),
				Key("L", 9286, "L", "L"),
				Key("M", 9285, "M", "M"),
				Key("N", 9284, "N", "N"),
				Key("O", 9283, "O", "O"),
				Key("P", 9282, "P", "P"),
				Key("Q", 9281, "Q", "Q"),
				Key("R", 9280, "R", "R"),
				Key("S", 9279, "S", "S"),
				Key("T", 9278, "T", "T"),
				Key("U", 9277, "U", "U"),
				Key("V", 9276, "V", "V"),
				Key("W", 9275, "W", "W"),
				Key("X", 9274, "X", "X"),
				Key("Y", 9273, "Y", "Y"),
				Key("Z", 9272, "Z", "Z"),

				// German (here, until extension exists for this)
				Key("Ü", 9213, "Ü", "Ü"),
				Key("Ö", 9214, "Ö", "Ö"),
				Key("Ä", 9215, "Ä", "Ä"),

				// Numbers
				Key("0", 9271, "0", "0"),
				Key("1", 9270, "1", "1"),
				Key("2", 9269, "2", "2"),
				Key("3", 9268, "3", "3"),
				Key("4", 9267, "4", "4"),
				Key("5", 9266, "5", "5"),
				Key("6", 9265, "6", "6"),
				Key("7", 9264, "7", "7"),
				Key("8", 9263, "8", "8"),
				Key("9", 9262, "9", "9"),

				// Symbols
				Key("@", 44085, "@", "@"), // not done
				Key("#", 9239, "#", "#"),
				Key("_", 9228, "_", "_"),
				Key("=", 9241, "=", "="),
				Key("°", 44091, "°", "°"),
				Key("+", 9237, "+", "+"),
				Key("%", 9238, "%", "%"),
				Key("-", 9243, "-", "-"),
				Key("...", 44095, "...", "..."),
				Key("\"", 9235, "\n", "\n"),
				Key("?", 9236, "?", "?"),
				Key("!", 9240, "!", "!"),
				Key("&", 13232, "&", "&"),
				Key(":", 9247, ":", ":"),
				Key(";", 9232, ";", ";"),
				Key(",", 9246, ",", ","),
				Key(".", 9242, ".", "."),
				Key("}", 9244, "}", "}"),
				Key("{", 9245, "{", "{"),
				Key("]", 9229, "]", "]"),
				Key("[", 9230, "[", "["),
				Key(")", 9233, ")", ")"),
				Key("(", 9234, "(", "("),
				Key("/", 9231, "/", "/"),
				Key("\\", 9249, "\\", "\\"),

				// Special Symbols
				Key("_REFRESH", 44086, "Refresh", "↺"),
				Key("_CHECK", 44087, "Check", "✔︎"),
				Key("_BLANK", 22757, "Blank", " "),

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
				//</editor-fold>
			)
		)
	).transformer(DataTransformer.json())

	object File {

		val CONFIG = JetYamlFile.rootFile("system-config")
		val RAW = JetYamlFile.rootFile("system-raw-data")
		val BRAIN = JetYamlFile.rootFile("system-memory")

		val ESSENTIALS_CONFIG = JetYamlFile.dummyComponentFile("Essentials", "JET", "config")
		val ESSENTIALS_WORLDS = JetYamlFile.dummyComponentFile("Essentials", "JET", "worlds")

	}

}