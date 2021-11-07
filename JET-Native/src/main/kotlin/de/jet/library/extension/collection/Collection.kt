package de.jet.library.extension.collection

/**
 * # `C.toArrayList()`
 * ## Info
 * This function creates a new [ArrayList]<[T]> object, which
 * contains the elements of the Collection<[T]> [C] (**this**-object)
 *
 * ## Use
 * This function can be easily used to get a set or list to an ArrayList,
 * but you can also use `ArrayList(yourList)`, that is the same, what is also
 * used in this function!
 *
 * ## Base
 * This function is globally available through the whole JET-Native API and beyond!
 *
 * This function creates a new [ArrayList] object of containing-type [T], obtained
 * from the Collection [C], which has also the containing-type [T]. This [toArrayList]
 * function is attached as an extension function to the [C] object, which is based
 * on all [Collection] type [T]!
 *
 * @author Fruxz (@TheFruxz)
 * @since 1.0-BETA-5 (preview)
 * @param T the inner containing data type
 * @param C the actual base collection, which will be transformed
 */
fun <T, C : Collection<T>> C.toArrayList() = ArrayList(this)

/**
 * # `Array<out T>.toArrayList()`
 * ## Info
 * This function creates a new [ArrayList]<[T]> object, which
 * contains the elements of the Array<out [T]> (**this**-object)
 *
 * ## Use
 * This function can be easily used tzo get an array to an ArrayList,
 * but you can also use `ArrayList(yourArray.toList())`, that is the same, what is also
 * used in this function!
 *
 * ## Base
 * This function is globally available through the whole JET-Native API and beyond!
 *
 * This function creates a new [ArrayList] object of containing-type [T], obtained from the Array<out [T]>.
 * This [toArrayList] function is attached as an extension function to the Array<out [T]> object.
 *
 * @author Fruxz (@TheFruxz)
 * @since 1.0-BETA-5 (preview)
 * @param T the inner containing data type of both, input [Array] and output [ArrayList]
 */
fun <T> Array<out T>.toArrayList() = ArrayList(this.toList())

fun <T, C : Collection<T>> C.stackRandom(times: Int) = buildString {
	repeat(times) { append(random()) }
}

fun <T> Array<out T>.stackRandom(times: Int) = buildString {
	repeat(times) { append(random()) }
}

fun <T, C : Collection<T>> C.stackUniqueRandom(times: Int) =
	shuffled().take(times).joinToString(separator = "")

fun <T> Array<out T>.stackUniqueRandom(times: Int) =
	toList().shuffled().take(times).joinToString(separator = "")

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

