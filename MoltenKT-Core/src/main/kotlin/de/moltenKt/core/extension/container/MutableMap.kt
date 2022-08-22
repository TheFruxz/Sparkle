package de.moltenKt.core.extension.container

/**
 * This function creates a new [MutableMap], utilizing the [mutableMapOf] function and puts
 * all content of [this] into the new one.
 * @author Fruxz
 * @since 1.0
 */
fun <T, E, M : MutableMap<T, E>> M.copy() = mutableMapOf<T, E>().apply {
	putAll(this@copy)
}

/**
 * This function removes every element from this [MutableMap], which matches the [condition].
 * @author Fruxz
 * @since 1.0
 */
fun <T, E, M : MutableMap<T, E>> M.removeAll(condition: (key: T, value: E) -> Boolean) = copy().forEach { (key, value) ->
	if (condition(key, value)) {
		this.remove(key)
	}
}