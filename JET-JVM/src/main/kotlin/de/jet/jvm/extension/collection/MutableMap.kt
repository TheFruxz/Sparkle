package de.jet.jvm.extension.collection

fun <T, E, M : MutableMap<T, E>> M.clone() = mutableMapOf<T, E>().apply {
	putAll(this@clone)
}

fun <T, E, M : MutableMap<T, E>> M.removeAll(condition: (key: T, value: E) -> Boolean) = clone().forEach { (key, value) ->
	if (condition(key, value)) {
		this.remove(key)
	}
}