package de.fruxz.sparkle.framework.extension

import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.forceCastOrNull
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.framework.infrastructure.app.App
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType

object PersistentData {

	val persistentDataLogger = App.createLog(system.identity, "PersistentData")

	val persistentDataTypes = setOf(
		PersistentDataType.BYTE,
		PersistentDataType.SHORT,
		PersistentDataType.INTEGER,
		PersistentDataType.LONG,
		PersistentDataType.FLOAT,
		PersistentDataType.DOUBLE,
		PersistentDataType.STRING,
		PersistentDataType.BYTE_ARRAY,
		PersistentDataType.INTEGER_ARRAY,
		PersistentDataType.LONG_ARRAY,
		PersistentDataType.TAG_CONTAINER_ARRAY,
		PersistentDataType.TAG_CONTAINER,
	)

}

private val Any.persistentDataType: PersistentDataType<*, *>
	get() = when (this) {
		is Byte -> PersistentDataType.BYTE
		is Short -> PersistentDataType.SHORT
		is Int -> PersistentDataType.INTEGER
		is Long -> PersistentDataType.LONG
		is Float -> PersistentDataType.FLOAT
		is Double -> PersistentDataType.DOUBLE
		is String -> PersistentDataType.STRING
		is ByteArray -> PersistentDataType.BYTE_ARRAY
		is IntArray -> PersistentDataType.INTEGER_ARRAY
		is LongArray -> PersistentDataType.LONG_ARRAY
		is PersistentDataContainer -> PersistentDataType.TAG_CONTAINER
		is Array<*> -> PersistentDataType.TAG_CONTAINER_ARRAY
		else -> throw IllegalArgumentException("The data '$this' is not compatible with the data-cache")
	}

fun <T : Any> PersistentDataHolder.setPersistentData(key: Key, value: T) = tryOrNull {
	fun <T> transform(type: PersistentDataType<T, T>, data: Any) = type.forceCast<PersistentDataType<T, T>>() to data.forceCast<T>()
	val container = persistentDataContainer
	val (type, smart) = transform(value.persistentDataType.forceCast<PersistentDataType<T, T>>(), value)

	container.set(NamespacedKey.fromString(key.asString())!!, type, smart)

} ?: PersistentData.persistentDataLogger.warning("Transformation for '$key' failed at '$value' transform")

fun <T : Any> PersistentDataHolder.setPersistentData(name: String, value: T) =
	setPersistentData(key = Key.key(name), value)

fun <T : Any> PersistentDataHolder.getPersistentData(key: Key): T? {
	PersistentData.persistentDataTypes.forEach { type ->
		persistentDataContainer.get(NamespacedKey.fromString(key.asString())!!, type)?.let {
			return it.forceCastOrNull()
		}
	}
	return null
}

fun <T : Any> PersistentDataHolder.getPersistentData(name: String): T? =
	getPersistentData(key = Key.key(name))

var PersistentDataHolder.persistentData: Map<Key, Any>
	set(value) = value.forEach(this::setPersistentData)
	get() = buildMap {
		persistentDataContainer.keys.forEach { key ->
			get(key)?.let { value ->
				put(key, value)
			}
		}
	}