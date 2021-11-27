package de.jet.jvm.extension.collection

/**
 * Replaces the content values of [this] with the contents
 * of the [collection] collection (type [T]).
 * @param collection The new entries of [this] collection
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableCollection<T>.mutableReplaceWith(collection: Collection<T>) {
	removeAll { !collection.contains(it) }
	collection.forEach { c ->
		if (!contains(c))
			add(c)
	}
}

/**
 * If contained, remove; or if not contained add element!
 * @param o The element to be added or removed
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableCollection<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}

/**
 * If contained, remove; or if not contained add element!
 * @param o The element to be added or removed
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableSet<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}

/**
 * If contained, remove; or if not contained add element!
 * @param o The element to be added or removed
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableList<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}
