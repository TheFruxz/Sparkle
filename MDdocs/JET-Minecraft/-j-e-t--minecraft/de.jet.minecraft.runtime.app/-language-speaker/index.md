//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.runtime.app](../index.md)/[LanguageSpeaker](index.md)

# LanguageSpeaker

[jvm]\
class [LanguageSpeaker](index.md)(baseLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

## Types

| Name | Summary |
|---|---|
| [LanguageContainer](-language-container/index.md) | [jvm]<br>data class [LanguageContainer](-language-container/index.md)(languageId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), languageVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), languageVendor: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), languageVendorWebsite: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), jetVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), languageData: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), JsonElement&gt;) |

## Functions

| Name | Summary |
|---|---|
| [dataToJson](data-to-json.md) | [jvm]<br>fun [dataToJson](data-to-json.md)(): JsonObject |
| [message](message.md) | [jvm]<br>fun [message](message.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), smartColor: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [baseLang](base-lang.md) | [jvm]<br>val [baseLang](base-lang.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [cachedLanguageData](cached-language-data.md) | [jvm]<br>val [cachedLanguageData](cached-language-data.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [error](error.md) | [jvm]<br>val [error](error.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [languageContainer](language-container.md) | [jvm]<br>val [languageContainer](language-container.md): [LanguageSpeaker.LanguageContainer](-language-container/index.md) |

## Extensions

| Name | Summary |
|---|---|
| [get](../../de.jet.minecraft.extension/get.md) | [jvm]<br>operator fun [LanguageSpeaker](index.md).[get](../../de.jet.minecraft.extension/get.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
