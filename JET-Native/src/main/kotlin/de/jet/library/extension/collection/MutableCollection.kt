package de.jet.library.extension.collection

fun <T> MutableCollection<T>.mutableReplaceWith(collection: Collection<T>) {
	removeAll { !collection.contains(it) }
	collection.forEach { c ->
		if (!contains(c))
			add(c)
	}
}

/**
 * If contained, remove; or if not contained add element!
 */
fun <T> MutableCollection<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}

/**
 * If contained, remove; or if not contained add element!
 */
fun <T> MutableSet<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}

/**
 * If contained, remove; or if not contained add element!
 */
fun <T> MutableList<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}
