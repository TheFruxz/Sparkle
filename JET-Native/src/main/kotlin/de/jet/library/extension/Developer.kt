package de.jet.library.extension

val Any.asString: String
	get() = toString()

fun <T> checkAllObjects(vararg objects: T, check: T.() -> Boolean): Boolean {
	return objects.all(check)
}

fun <T> T.modifiedIf(modifyIf: Boolean, modification: T.() -> T) = if (!modifyIf) { this } else modification(this)

fun <T> T.modifyIf(modifyIf: Boolean, modification: T.() -> Unit) = if (!modifyIf) { this } else apply(modification)

val Any?.isNull: Boolean
	get() = this != null

val Any?.isNotNull: Boolean
	get() = !isNull