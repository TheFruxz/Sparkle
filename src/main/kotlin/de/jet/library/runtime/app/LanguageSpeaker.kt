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
		val map: Map<String, JsonElement> = mapOf("generator" to JsonPrimitive("v${JetApp.instance.description.version}")),
	)

	val cachedLanguageData: Map<String, String> by lazy {
		JetApp.instance.getResourceFile("lang/$languageId.json").let { data ->

			if (data != null) {
				Json.decodeFromString<LanguageContainer>(data).map.map { it.key to it.value.jsonPrimitive.content }.toMap()
			} else
				mapOf("error" to "yes")

		}
	}

	fun dataToJson() = JsonObject(cachedLanguageData.map { it.key to JsonPrimitive(it.value) }.toMap())

	val error: Boolean
		get() = cachedLanguageData["error"] == "yes"

	fun message(id: String): String {
		return if (!error) {

			cachedLanguageData[id] ?: "UNKNOWN"

		} else "LANGUAGE FILE $languageId NOT CORRECT"
	}

}