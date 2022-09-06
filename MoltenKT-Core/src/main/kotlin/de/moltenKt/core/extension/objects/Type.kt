package de.moltenKt.core.extension.objects

inline fun <reified O> Any.takeIfInstance(): O? = when (this) {
	is O -> this
	else -> null
}