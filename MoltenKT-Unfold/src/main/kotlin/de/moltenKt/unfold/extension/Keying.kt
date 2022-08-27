package de.moltenKt.unfold.extension

import net.kyori.adventure.key.Key

fun Key.subKey(value: String, strategy: KeyingStrategy = KeyingStrategy.PATHING): Key = Key.key(
	when (strategy) {
		KeyingStrategy.SQUASH -> asString().replace(":", "_")
		KeyingStrategy.ORIGIN -> namespace()
		KeyingStrategy.CONTINUE -> value()
		KeyingStrategy.PATHING -> namespace()
	},
	when (strategy) {
		KeyingStrategy.PATHING -> "${value()}.$value"
		else -> value
	}
)

infix operator fun Key.div(value: String): Key = subKey(value)

/**
 * Every strategy to generate sub keys of a key has its pros and cons.
 * Decide wisely by your use case, which you want to use, for a little help,
 * look at the documentation of each strategy enum.
 */
enum class KeyingStrategy {

	/**
	 * The identity of the parent gets squashed and used inside the child namespace.
	 * Example: 'origin:parent' -> 'origin_parent:child' -> 'origin_parent_child:sub-child'
	 * Info: Stores the complete path & origin, but can quickly become quite large
	 */
	SQUASH,

	/**
	 * The namespace of the origins key is used as the namespace of every child.
	 * Example: 'origin:parent' -> 'origin:child' -> 'origin:sub-child'
	 * Info: Always keeps the origin of the keys in mind, but loses history of keys between start and end
	 */
	ORIGIN,

	/**
	 * The value of the parent is used as the namespace of the child
	 * Example: 'origin:parent' -> 'parent:child' -> 'child:sub-child'
	 * Info: Small and allows to track the history, but the back-tracking requires that you have all the keys!
	 */
	CONTINUE,

	/**
	 * The namespace is the same as the value of the parent, but the value is the
	 * parents value + a dot + the child value.
	 * 'origin:parent' -> 'origin:parent.child' -> 'origin:parent.child.sub-child'
	 * Info: Always keeps the complete source present and also contains the full
	 * history, but can be quite large!
	 */
	PATHING;

}