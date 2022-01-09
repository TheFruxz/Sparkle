package de.jet.jvm.tool.mutable

/**
 * This class is a mutable representation of a set and a get function.
 * @param <T> the type of the contained data
 * @author Fruxz
 * @since 1.0
 */
class ComputationalMutable<T>(
    val default: T,
    private val onSet: (T) -> Unit,
    private val onGet: () -> T,
) : Mutable<T> {

    override var property: T
        get() = onGet()
        set(value) = onSet(value)

}