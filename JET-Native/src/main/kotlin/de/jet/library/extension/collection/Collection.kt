package de.jet.library.extension.collection

fun <T, C : Collection<T>> C.toArrayList() = ArrayList(this)

fun <T> Array<out T>.toArrayList() = ArrayList(this.toList())

val <T, C : Collection<T>> C.first: T
	get() = first()

val <T> Array<out T>.first: T
	get() = first()

val <T, C : Collection<T>> C.second: T
	get() = elementAt(1)

val <T> Array<out T>.second: T
	get() = elementAt(1)

val <T, C : Collection<T>> C.third: T
	get() = elementAt(2)

val <T> Array<out T>.third: T
	get() = elementAt(2)

val <T, C : Collection<T>> C.last: T
	get() = last()

val <T> Array<out T>.last: T
	get() = last()

