package de.jet.library.extension.collection

fun <T> MutableCollection<T>.mutableReplaceWith(collection: Collection<T>) {
	removeAll { !collection.contains(it) }
	collection.forEach { c ->
		if (!contains(c))
			add(c)
	}
}

fun <T> MutableCollection<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}

fun <T> MutableSet<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}

fun <T> MutableList<T>.toggle(o: T) {
	if (contains(o))
		remove(o)
	else
		add(o)
}
