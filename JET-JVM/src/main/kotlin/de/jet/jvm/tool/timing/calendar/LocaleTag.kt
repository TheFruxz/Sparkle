package de.jet.jvm.tool.timing.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("LocalTag")
data class LocaleTag(
	val languageTag: String,
) {

	val locale: Locale
		get() = Locale.forLanguageTag(languageTag)

	companion object {

		val Locale.localeTag: LocaleTag
			get() = LocaleTag(toLanguageTag())

	}

}
