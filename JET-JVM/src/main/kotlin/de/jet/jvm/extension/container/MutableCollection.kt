package de.jet.jvm.extension.container

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
 * @param addToContainer If true, add element to container; else remove element from container
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableCollection<T>.toggle(o: T, addToContainer: Boolean = !contains(o)) {
	if (addToContainer) add(o) else remove(o)
}

/**
 * If contained, remove; or if not contained add element!
 * @param o The element to be added or removed
 * @param addToContainer If true, add element to container; else remove element from container
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableSet<T>.toggle(o: T, addToContainer: Boolean = !contains(o)) {
	if (addToContainer) add(o) else remove(o)
}

/**
 * If contained, remove; or if not contained add element!
 * @param o The element to be added or removed
 * @param addToContainer If true, add element to container; else remove element from container
 * @author Fruxz
 * @since 1.0
 */
fun <T> MutableList<T>.toggle(o: T, addToContainer: Boolean = !contains(o)) {
	if (addToContainer) add(o) else remove(o)
}
