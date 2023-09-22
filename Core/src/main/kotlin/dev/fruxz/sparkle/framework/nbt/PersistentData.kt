package dev.fruxz.sparkle.framework.nbt

import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import kotlin.reflect.KType
import kotlin.reflect.typeOf

object PersistentData {

    val persistentDataTypes: Map<PersistentDataType<*, *>, KType> = mapOf(
        PersistentDataType.BYTE to typeOf<Byte>(),
        PersistentDataType.SHORT to typeOf<Short>(),
        PersistentDataType.INTEGER to typeOf<Int>(),
        PersistentDataType.LONG to typeOf<Long>(),
        PersistentDataType.FLOAT to typeOf<Float>(),
        PersistentDataType.DOUBLE to typeOf<Double>(),
        PersistentDataType.STRING to typeOf<String>(),
        PersistentDataType.BYTE_ARRAY to typeOf<ByteArray>(),
        PersistentDataType.INTEGER_ARRAY to typeOf<IntArray>(),
        PersistentDataType.LONG_ARRAY to typeOf<LongArray>(),
        PersistentDataType.TAG_CONTAINER to typeOf<PersistentDataContainer>(),
        PersistentDataType.TAG_CONTAINER_ARRAY to typeOf<Array<PersistentDataContainer>>(),
    )

    val <T> T.persistentDataType: PersistentDataType<*, *>
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

}