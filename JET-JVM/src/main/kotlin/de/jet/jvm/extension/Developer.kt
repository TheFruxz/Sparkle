package de.jet.jvm.extension

/**
 * Using the [toString] function to convert [this] to a string.
 * @author Fruxz
 * @since 1.0
 */
val Any.asString: String
	get() = toString()

/**
 * Executes a check, if all [objects] are passing the [check] check.
 * @param objects The objects to check.
 * @param check The check to execute.
 * @return `true` if all [objects] are passing the [check] check, `false` otherwise.
 * @author Fruxz
 * @since 1.0
 */
fun <T> checkAllObjects(vararg objects: T, check: T.() -> Boolean): Boolean {
	return objects.all(check)
}

/**
 * Returning the [this]<[T]> modified with the [modification] if [modifyIf] is true,
 * otherwise returning the [this]<[T]> directly.
 * @param modifyIf if true modification is returned, else original
 * @param modification the modification to apply
 * @return the [this]<[T]> modified with the [modification] if [modifyIf] is true, otherwise returning the [this]<[T]> directly.
 * @author Fruxz
 * @since 1.0
 */
fun <T> T.modifiedIf(modifyIf: Boolean, modification: T.() -> T) = if (!modifyIf) { this } else modification(this)

/**
 * Applying the [modification] to [this] if [modifyIf] is true,
 * otherwise to nothing to [this].
 * @param modifyIf if true modification is applied, else keep original
 * @param modification the modification to apply
 * @author Fruxz
 * @since 1.0
 */
fun <T> T.modifyIf(modifyIf: Boolean, modification: T.() -> Unit) = if (!modifyIf) { this } else apply(modification)

/**
 * Returns true if [this] is null, otherwise false.
 * @author Fruxz
 * @since 1.0
 */
val Any?.isNull: Boolean
	get() = this != null

/**
 * Returns true if [this] is not null, otherwise false.
 * @author Fruxz
 * @since 1.0
 */
val Any?.isNotNull: Boolean
	get() = !isNull