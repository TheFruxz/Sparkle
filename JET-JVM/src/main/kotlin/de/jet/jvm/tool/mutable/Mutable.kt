package de.jet.jvm.tool.mutable

/**
 * This interface is used to mark a class as a [Mutable] object.
 * @param <T> The type of the object as the value.
 * @author Fruxz
 * @since 1.0
 */
interface Mutable<T> {

	/**
	 * This value represents the current state
	 * of the value of the [Mutable] as a [T].
	 * @author Fruxz
	 * @since 1.0
	 */
	var property: T

	companion object {

		/**
		 * This function generates a [FlexibleMutable] object,
		 * which is a [Mutable] object with a [T] value.
		 * @param value The first value of the [FlexibleMutable] object.
		 * @return The [FlexibleMutable] object, with [value] inside.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun <T> default(value: T) =
			FlexibleMutable(value)

		/**
		 * This function generates a [ComputationalMutable] object, which is a [Mutable] object with a [T] value.
		 * @param value The first value of the [ComputationalMutable] object.
		 * @return The [ComputationalMutable] object, with [value] inside.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun <T> computational(value: T, onSet: (T) -> Unit, onGet: () -> T) =
			ComputationalMutable(value, onSet, onGet)

	}

}