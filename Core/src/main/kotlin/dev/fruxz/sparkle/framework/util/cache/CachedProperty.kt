package dev.fruxz.sparkle.framework.util.cache

import dev.fruxz.ascend.extension.future.await
import dev.fruxz.sparkle.framework.coroutine.task.doAsync
import dev.fruxz.sparkle.framework.coroutine.task.doSync
import dev.fruxz.sparkle.framework.system.debugLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlin.reflect.KProperty
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class CachedProperty<T>(val default: () -> T, private val keep: Duration) {

    constructor(default: () -> T, keepGroup: CacheDuration) : this(default, keepGroup.duration)

    private var value: T = default()
    private var isModified = false
    private var resetJob: Job? = null
    private lateinit var property: KProperty<*>

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        debugLog { "Cached property '${property.name}' changed, estimated reset in $keep" }
        this.property = property
        this.isModified = true
        this.value = value
        reset()
    }

    private fun reset() {
        resetJob?.cancel("Property '${property.name}' was modified")
        if (keep.isFinite() && keep.isPositive()) {
            debugLog { "Cached property '${property.name}' reset" }
            resetJob = doAsync {
                delay(keep)
                if (isModified) {
                    doSync {
                        this.value = default()
                        isModified = false
                        resetJob = null
                    }.await()
                    return@doAsync
                }
            }
        }
    }

    enum class CacheDuration(val duration: Duration) {
        QUICK(1.minutes),
        DEFAULT(5.minutes),
        MEDIUM(10.minutes),
        LONG(30.minutes),
        HOUR(1.hours),
    }

}