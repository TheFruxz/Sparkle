package de.jet.paper.extension.data

import de.jet.paper.structure.classes.JSON
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

inline fun <reified T : Any> T.toJson() = JSON.jsonFormat.encodeToString(this)

inline fun <reified T : Any> String.fromJson() = JSON.jsonFormat.decodeFromString<T>(this)