package de.moltenKt.paper.runtime.app

import de.moltenKt.core.extension.container.replace
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import org.bukkit.ChatColor

class LanguageSpeaker(
	val baseLang: String
) {

	@Serializable
	@SerialName("AppLanguageContainer")
	data class LanguageContainer(
		val languageId: String,
		val languageVersion: String,
		val languageVendor: String,
		val languageVendorWebsite: String,
		val moltenVersion: String,
		val languageData: Map<String, JsonElement> = mapOf("generator" to JsonPrimitive("v${de.moltenKt.paper.app.MoltenApp.instance.description.version}")),
	)

	val languageContainer: LanguageContainer by lazy {
		de.moltenKt.paper.app.MoltenApp.instance.getResourceFile("lang/$baseLang.json").let { data ->

			if (data != null) {
				Json.decodeFromString(data)
			} else
				LanguageContainer(
					languageId = "backup",
					languageVersion = "0",
					languageVendor = "system",
					languageVendorWebsite = "/",
					moltenVersion = "/",
					languageData = mapOf("error" to JsonPrimitive("yes"))
				)

		}
	}

	val cachedLanguageData: Map<String, String> by lazy {
		languageContainer.languageData.map { it.key to it.value.jsonPrimitive.content }.toMap()
	}

	fun dataToJson() = JsonObject(cachedLanguageData.map { it.key to JsonPrimitive(it.value) }.toMap())

	val error: Boolean
		get() = cachedLanguageData["error"] == "yes"

	private val basicHTMLmap = mapOf(
		"<br>" to "\n"
	)

	fun message(id: String, smartColor: Boolean = true, basicHTML: Boolean = true): String {
		return if (!error) {

			(cachedLanguageData[id] ?: "LANGUAGE-DATA '$id' UNKNOWN ($baseLang)").let { output ->
				var outputState = output

				if (smartColor) outputState = outputState.replace(smartColors)

				if (basicHTML) outputState = outputState.replace(basicHTMLmap)

				return outputState
			}

		} else "LANGUAGE FILE $baseLang NOT CORRECT"
	}

	companion object {

		@JvmStatic
		val smartColors = ChatColor.values().associateBy {
			"[${it.name}]"
		} + mapOf(
			"[LIME]" to ChatColor.GREEN,
			"[ORANGE]" to ChatColor.GOLD,
			"[NONE]" to ChatColor.RESET,
		)

		@JvmStatic
		val smartColorReplace = smartColors.mapValues { "§${it.value.char}" }


		// TODO rework this whole stuff, because of the MiniMessage legacy stuff

	}

}