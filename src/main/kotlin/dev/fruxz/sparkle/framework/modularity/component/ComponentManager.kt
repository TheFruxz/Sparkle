package dev.fruxz.sparkle.framework.modularity.component

import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.json.readJsonOrDefault
import dev.fruxz.ascend.json.writeJson
import dev.fruxz.sparkle.framework.coroutine.task.asSync
import dev.fruxz.sparkle.framework.coroutine.task.doSync
import dev.fruxz.sparkle.server.LocalSparklePlugin
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key
import java.nio.file.Path
import kotlin.io.path.div

object ComponentManager {

    private val registered = mutableSetOf<Component>()
    private val running = mutableSetOf<Component>()

    private val configurationPath = (LocalSparklePlugin.sparkleFolder / "components.json").also(Path::createFileAndDirectories)

    private var _configuration: Map<Key, ComponentConfiguration>? = null

    var configuration: Map<Key, ComponentConfiguration>
        get() {

            return when (val cachedConfiguration = _configuration) {
                null -> {
                    runBlocking {
                        asSync {
                            configurationPath
                                .readJsonOrDefault<Map<String, ComponentConfiguration>>(emptyMap(), true)
                                .mapKeys { Key.key(it.key) }
                                .also { _configuration = it }
                        }
                    }
                }

                else -> cachedConfiguration
            }

        }
        set(value) {
            _configuration = value

            doSync {
                configurationPath.writeJson(
                    value.mapKeys { it.key.asString() }
                )
            }

        }

    fun register(
        component: Component,
        isAutoStart: Boolean = component.startup.defaultIsAutoStart,
        isBlocked: Boolean = false,
    ) {
        registered += component
        configuration = configuration + (component.identity to ComponentConfiguration(isAutoStart, isBlocked))
    }

    @Serializable
    data class ComponentConfiguration(
        val isAutoStart: Boolean,
        val isBlocked: Boolean,
    )

}