package dev.fruxz.sparkle.framework.modularity.component

import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.json.property
import dev.fruxz.sparkle.framework.coroutine.task.doAsync
import dev.fruxz.sparkle.server.LocalSparklePlugin
import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key
import java.nio.file.Path
import kotlin.io.path.div
import kotlin.reflect.KClass

object ComponentManager {

    private val configPath = (LocalSparklePlugin.sparkleFolder / "components.json").also(Path::createFileAndDirectories)
    var configuration by property(configPath, "components") { emptyMap<String, ComponentConfiguration>() }

    val registered = mutableMapOf<Component, ComponentStatus>()

    // get

    fun getOrNull(key: Key) = registered.keys.firstOrNull { it.identity == key }

    inline fun <reified T : Component> getOrNull() = registered.keys.firstOrNull { it is T } as? T

    operator fun get(key: Key) = getOrNull(key) ?: throw NoSuchElementException("Component $key is not registered")

    inline fun <reified T : Component> get() = getOrNull<T>() ?: throw NoSuchElementException("Component ${T::class.simpleName} is not registered")

    // register

    fun register(component: Component, clazz: KClass<out Component>) {
        val componentConfiguration = configuration.getOrElse(component.identity.asString()) {
            ComponentConfiguration(
                isAutoStart = component.startup.defaultIsAutoStart,
                isBlocked = false,
            )
        }

        registered[component] = ComponentStatus(
            key = component.identity,
            isRunning = false,
            clazz = clazz,
        )

        configuration = configuration + (component.identity.asString() to componentConfiguration)

        if (!componentConfiguration.isBlocked && (componentConfiguration.isAutoStart || component.startup.forcedAutoStart)) {
            doAsync { component.start().await() }
        }

    }

    inline fun <reified T : Component> register(component: T) = register(component, T::class)

    // unregister

    fun unregister(identity: Key, deleteConfiguration: Boolean = false): Boolean {
        val component = getOrNull(identity) ?: return false

        doAsync { component.stop().await() }
        registered.remove(component)

        if (deleteConfiguration) { configuration = configuration - component.identity.asString() }

        return true
    }

    inline fun <reified T : Component> unregister(deleteConfiguration: Boolean = false) = unregister(get<T>().identity, deleteConfiguration)

    // state

    fun updateState(identity: Key, process: (ComponentStatus) -> ComponentStatus): Boolean {
        val component = getOrNull(identity) ?: return false

        registered[component] = process(registered[component] ?: return false)

        return true
    }

    // classes

    data class ComponentStatus(
        val key: Key,
        val isRunning: Boolean,
        val clazz: KClass<out Component>,
    )

    @Serializable
    data class ComponentConfiguration(
        val isAutoStart: Boolean,
        val isBlocked: Boolean,
    )

}