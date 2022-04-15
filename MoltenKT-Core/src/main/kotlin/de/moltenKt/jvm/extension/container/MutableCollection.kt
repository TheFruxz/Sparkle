package de.moltenKt.jvm.extension.container

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

/**
 * This function takes a nullable [C] (which is a collection-based object) and
 * returns an empty list, if it is actually null, or itself, if itself was not
 * null. This function makes it easy to create new collections, if there is a
 * nullable collection.
 * @param C The type of the collection-based object
 * @param T The type of the elements of the collection-based object
 * @return An empty list, if [C] was null, or [C] itself, if it was not null
 * @author Fruxz
 * @since 1.0
 */
fun <C : Collection<T>, T> C?.orEmptyList() = this?.toList() ?: listOf()

/**
 * This function takes a nullable [C] (which is a mutable-collection-based object) and
 * returns an empty mutable list, if it is actually null, or itself, if itself was not
 * null. This function makes it easy to create new mutable collections, if there is a
 * nullable mutable collection.
 * @param C The type of the mutable-collection-based object
 * @param T The type of the elements of the mutable-collection-based object
 * @return An empty mutable list, if [C] was null, or [C] itself, if it was not null
 * @author Fruxz
 * @since 1.0
 */
fun <C : MutableCollection<T>, T> C?.orEmptyMutableList() = this?.toMutableList() ?: mutableListOf()

/**
 * This function allows to get a mutable list of a collection-based object, that
 * got a new [element] added to it. This function is a little helper besides the
 * [MutableCollection.plus] function, that only returns an immutable list, instead
 * of its real type. This function returns a mutable list, that contains the
 * [element] added to the collection-based object.
 * @param C The type of the collection-based object
 * @param T The type of the elements of the collection-based object
 * @param element The element to be added to the collection-based object
 * @return A mutable list, that contains the [element] added to the collection-based object
 * @author Fruxz
 * @since 1.0
 */
infix fun <C : MutableCollection<T>, T> C.and(element: T) = apply { add(element) }

fun <C : MutableCollection<T>, T> C.addIf(element: T, check: (element: T, currentState: C) -> Boolean) {
	if (check(element, this)) add(element)
}

fun <C : MutableCollection<T>, T> C.addIfNot(element: T, check: (element: T, currentState: C) -> Boolean) =
	addIf(element) { it, c -> !check(it, c) }

fun <C : MutableCollection<T>, T> C.addIfContained(element: T) =
	addIf(element) { _, currentState -> currentState.contains(element) }

fun <C : MutableCollection<T>, T> C.addIfNotContained(element: T) =
	addIfNot(element) { _, currentState -> currentState.contains(element) }

