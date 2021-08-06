package de.jet.library.extension.collection

fun <T> MutableCollection<T>.mutableReplaceWith(collection: Collection<T>) {
	removeAll { !collection.contains(it) }
	collection.forEach { c ->
		if (!contains(c))
			add(c)
	}
}