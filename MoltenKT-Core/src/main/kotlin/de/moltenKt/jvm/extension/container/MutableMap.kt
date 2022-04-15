package de.moltenKt.jvm.extension.container

fun <T, E, M : MutableMap<T, E>> M.copy() = mutableMapOf<T, E>().apply {
	putAll(this@copy)
}

fun <T, E, M : MutableMap<T, E>> M.removeAll(condition: (key: T, value: E) -> Boolean) = copy().forEach { (key, value) ->
	if (condition(key, value)) {
		this.remove(key)
	}
}