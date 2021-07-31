package de.jet.library.runtime.app

import de.jet.app.JetApp
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

class LanguageSpeaker(
	val languageId: String
) {

	@Serializable
	data class LanguageContainer(
		val languageId: String,
		val languageVersion: String,
		val languageVendor: String,
		val languageVendorWebsite: String,
		val jetVersion: String,
		val languageData: Map<String, JsonElement> = mapOf("generator" to JsonPrimitive("v${JetApp.instance.description.version}")),
	)

	val languageContainer: LanguageContainer by lazy {
		JetApp.instance.getResourceFile("lang/$languageId.json").let { data ->

			if (data != null) {
				Json.decodeFromString(data)
			} else
				LanguageContainer(
					languageId = "backup",
					languageVersion = "0",
					languageVendor = "system",
					languageVendorWebsite = "/",
					jetVersion = "/",
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

	fun message(id: String, smartColor: Boolean = true): String {
		return if (!error) {

			(cachedLanguageData[id] ?: "LANGUAGE-DATA '$id' UNKNOWN ($languageId)").let { output ->
				if (smartColor) {
					output
				} else
					output
			}

		} else "LANGUAGE FILE $languageId NOT CORRECT"
	}

}