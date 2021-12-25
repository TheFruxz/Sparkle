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
@Deprecated("Use the `all` extension function instead.", ReplaceWith("all(*objects, check = check)"))
fun <T> checkAllObjects(vararg objects: T, check: T.() -> Boolean) =
	all(*objects, check = check)

/**
 * Executes a check, if all [objects] are passing the [check] check.
 * @param objects The objects to check.
 * @param check The check to execute.
 * @return `true` if all [objects] are passing the [check] check, `false` otherwise.
 * @author Fruxz
 * @since 1.0
 */
fun <T> all(vararg objects: T, check: T.() -> Boolean) = objects.all(check)

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

/**
 * Only an empty body function.
 * @author Fruxz
 * @since 1.0
 */
fun empty() { }

/**
 * Returns the [this]<[T]> modified with the [process] if [isNull] is true,
 * else return the unmodified state
 * @param process is the optional modification process
 * @author Fruxz
 * @since 1.0
 */
fun <T> T?.applyIfNull(process: (T?) -> Unit) = modifyIf(isNull, process)

/**
 * Returns the [this]<[T]> modified with the [process] if [isNotNull] is true,
 * else return the unmodified state
 * @param process is the optional modification process
 * @author Fruxz
 * @since 1.0
 */
fun <T> T?.applyIfNotNull(process: (T) -> Unit) = modifyIf(isNotNull) {
	process(this!!)
}

/**
 * Executes the [process], if the [T] (nullable) is null ([isNull])
 * @param process is the optional execution process
 * @author Fruxz
 * @since 1.0
 */
fun <T> T?.ifNull(process: () -> Unit) = if (isNull) process() else empty()

/**
 * Executes the [process], if the [T] (nullable) is notNull ([isNotNull])
 * @param process is the optional execution process
 * @author Fruxz
 * @since 1.0
 */
fun <T> T?.ifNotNull(process: () -> Unit) = if (isNotNull) process() else empty()

/**
 * Returns [Pair.first] if [T]? [isNotNull], else use [Pair.second]
 * @author Fruxz
 * @since 1.0
 */
fun <T, D> Pair<T?, D>.asDefaultNullDodge() = first.isNull.switchResult(first, second)
