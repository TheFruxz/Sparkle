package de.jet.library.extension.collection

fun <T, C : Collection<T>> C.toArrayList() = ArrayList(this)

fun <T> Array<out T>.toArrayList() = ArrayList(this.toList())