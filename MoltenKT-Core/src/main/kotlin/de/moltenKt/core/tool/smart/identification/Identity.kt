package de.moltenKt.core.tool.smart.identification

import de.moltenKt.core.tool.smart.identification.Identity.IdentityColumnType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect

/**
 * This data class represents an identity, which
 * should be (in the most cases) unique inside
 * the [T] environment.
 *
 * This data class helps to easily identify and
 * compare objects.
 *
 * @param T The owner type of the identity.
 * @param identity The identity itself.
 * @author Fruxz
 * @since 1.0
 */
data class Identity<T> constructor(
	override val identity: String,
) : Identifiable<T> {

	/**
	 * Returns the [identity] (in the form of a [String])
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun toString() = identity

	/**
	 * This function returns this but changes the [T] type
	 * from the [Identity] to a [O] type, without changing
	 * the [identity] itself.
	 * @return The [Identity] with the [O] type instead of [T].
	 * @author Fruxz
	 * @since 1.0
	 */
	fun <O> change() = Identity<O>(identity)

	override fun equals(other: Any?): Boolean {
		if (other !is Identity<*>) return false
		
		return identity == other.identity
	}

	override fun hashCode(): Int {
		return identity.hashCode()
	}

	class IdentityColumnType<T> : ColumnType() {

		override fun sqlType() = currentDialect.dataTypeProvider.textType()

		override fun valueFromDB(value: Any) = Identity<T>("$value")

		override fun valueToDB(value: Any?) = when (value) {
			is String -> value
			is Identity<*> -> value.identity
			is Identifiable<*> -> value.identity
			else -> "$value"
		}

		override fun notNullValueToDB(value: Any) = valueToDB(value)

	}

}

fun <T> Table.identity(name: String): Column<Identity<out T>> = registerColumn(name, IdentityColumnType<T>())
