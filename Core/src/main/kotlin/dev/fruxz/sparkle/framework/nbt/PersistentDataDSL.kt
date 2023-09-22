package dev.fruxz.sparkle.framework.nbt

import dev.fruxz.ascend.extension.forceCast
import dev.fruxz.ascend.extension.forceCastOrNull
import dev.fruxz.sparkle.framework.adventure.namespacedKey
import dev.fruxz.sparkle.framework.marker.SparkleDSL
import dev.fruxz.sparkle.framework.nbt.PersistentData.persistentDataType
import net.kyori.adventure.key.Key
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

// manual type

fun <T : Any> PersistentDataContainer.setPersistentData(key: Key, type: PersistentDataType<T, T>, value: T) {
    this.set(key.namespacedKey, type, value)
}

fun <T> PersistentDataContainer.getPersistentDataOrNull(key: Key, type: PersistentDataType<out T, out T>): T? =
    this.get(key.namespacedKey, type)

fun <T> PersistentDataContainer.getPersistentData(key: Key, type: PersistentDataType<T, T>): T =
    getPersistentDataOrNull(key, type) ?: throw NoSuchElementException("No value for key '$key' found")

// auto type

fun <T : Any> PersistentDataContainer.setPersistentData(key: Key, value: T) {
    this.set(key.namespacedKey, value.persistentDataType.forceCast<PersistentDataType<T, T>>(), value)
}

fun <T> PersistentDataContainer.getPersistentDataOrNull(key: Key): T? {
    PersistentData.persistentDataTypes.keys.forEach { availableType ->
        if (this.has(key.namespacedKey, availableType)) {
            return this.getPersistentDataOrNull(
                key = key,
                type = availableType.forceCastOrNull<PersistentDataType<out T, out T>>() ?: return@forEach,
            )?.let { return it }
        }
    }
    return null
}

fun <T> PersistentDataContainer.getPersistentData(key: Key): T =
    getPersistentDataOrNull(key) ?: throw NoSuchElementException("No value for key '$key' found")

@SparkleDSL
operator fun <T : Any> PersistentDataContainer.set(key: Key, value: T) = setPersistentData(key, value)

@SparkleDSL
operator fun <T> PersistentDataContainer.get(key: Key): T = getPersistentData(key)

@SparkleDSL
operator fun <T : Any> PersistentDataContainer.plusAssign(data: Pair<Key, T>) = setPersistentData(data.first, data.second)

@SparkleDSL
operator fun <T> PersistentDataContainer.minusAssign(key: Key) = this.remove(key.namespacedKey)

// auto type properties

@SparkleDSL
var PersistentDataContainer.persistentData: Map<Key, Any>
    get() = this.keys.mapNotNull { dataKey -> getPersistentDataOrNull<Any>(dataKey)?.let { dataKey to it } }.toMap()
    set(value) = value.forEach(this::setPersistentData)

// edit functions

@SparkleDSL
fun PersistentDataContainer.edit(builder: MutableMap<Key, Any>.() -> Unit) {
    persistentData = persistentData.toMutableMap().apply(builder)
}