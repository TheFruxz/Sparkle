package de.jet.library.extension.collection

fun String.replace(map: Map<out Any?, Any?>): String {
	var out = this

	map.forEach { (key, value) ->
		out = out.replace("$key", "$value")
	}

	return out
}

fun String.replace(vararg pairs: Pair<Any?, Any?>) = replace(mapOf(*pairs))

fun String.replace(pairs: Collection<Pair<Any?, Any?>>) = replace(*pairs.toTypedArray())