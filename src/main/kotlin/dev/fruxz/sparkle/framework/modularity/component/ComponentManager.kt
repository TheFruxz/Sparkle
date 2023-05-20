package dev.fruxz.sparkle.framework.modularity.component

import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.json.readJsonOrDefault
import dev.fruxz.ascend.json.writeJson
import dev.fruxz.sparkle.framework.coroutine.task.asSync
import dev.fruxz.sparkle.framework.coroutine.task.doAsync
import dev.fruxz.sparkle.framework.coroutine.task.doSync
import dev.fruxz.sparkle.server.LocalSparklePlugin
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key
import java.nio.file.Path
import kotlin.io.path.div
import kotlin.reflect.KClass

object ComponentManager {

    val registered = mutableSetOf<Component>()
    val running = mutableSetOf<Key>()
    val associations = mutableMapOf<KClass<out Component>, Key>()

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
        clazz: KClass<out Component>,
        isAutoStart: Boolean = component.startup.defaultIsAutoStart,
        isBlocked: Boolean = false,
    ) {
        println("Registering component ${component.identity} with class ${clazz.simpleName} and autoStart=$isAutoStart")

        val componentSetup = configuration.getOrElse(component.identity) {
            ComponentConfiguration(isAutoStart, isBlocked)
        }

        registered += component
        associations.putIfAbsent(clazz, component.identity)

        configuration = configuration + (component.identity to componentSetup)
        if (!componentSetup.isBlocked && (componentSetup.isAutoStart || component.startup.forcedAutoStart)) {
            doAsync { component.start() }
        }

    }

    fun <T : Component> register(
        component: T,
        isAutoStart: Boolean = component.startup.defaultIsAutoStart,
        isBlocked: Boolean = false,
    ) = register(component, component::class, isAutoStart, isBlocked)

    // running

    fun isRunning(componentKey: Key): Boolean = componentKey in running

    inline fun <reified T : Component> isRunning(): Boolean = associations[T::class]?.let { isRunning(it) } == true

    // registered

    fun isRegistered(componentKey: Key): Boolean = registered.any { it.identity == componentKey }

    inline fun <reified T : Component> isRegistered(): Boolean = associations[T::class]?.let { isRegistered(it) } == true

    // autoStart

    fun isAutoStart(componentKey: Key): Boolean = configuration[componentKey]?.isAutoStart ?: false

    inline fun <reified T : Component> isAutoStart(): Boolean = associations[T::class]?.let { isAutoStart(it) } == true

    fun isAutoStart(componentKey: Key, isAutoStart: Boolean): Boolean {
        val component = registered.firstOrNull { it.identity == componentKey } ?: return false

        if (isAutoStart(componentKey) == isAutoStart) return false

        configuration = configuration + (componentKey to configuration[componentKey]!!.copy(isAutoStart = isAutoStart))

        return true
    }

    inline fun <reified T : Component> isAutoStart(isAutoStart: Boolean) = associations[T::class]?.let { isAutoStart(it, isAutoStart) } ?: false

    // blocked

    fun isBlocked(componentKey: Key): Boolean = configuration[componentKey]?.isBlocked ?: false

    inline fun <reified T : Component> isBlocked(): Boolean = associations[T::class]?.let { isBlocked(it) } == true

    // start

    fun registerStart(componentKey: Key) = running.add(componentKey)

    inline fun <reified T : Component> registerStart() = associations[T::class]?.let { registerStart(it) }

    // stop

    fun registerStop(componentKey: Key) = running.remove(componentKey)

    inline fun <reified T : Component> registerStop() = associations[T::class]?.let { registerStop(it) }

    // unregister

    fun unregister(componentKey: Key): Boolean {
        val component = registered.firstOrNull { it.identity == componentKey } ?: return false

        doAsync { component.stop() }
        registered -= component

        return true
    }

    inline fun <reified T : Component> unregister(): Boolean = associations[T::class]?.let { unregister(it) } == true

    // power options

    fun enable(componentKey: Key): Boolean {
        if (isRunning(componentKey)) return false
        val component = registered.firstOrNull { it.identity == componentKey } ?: return false

        doAsync { component.start() }

        return true
    }

    inline fun <reified T : Component> enable() = associations[T::class]?.let { enable(it) }

    fun disable(componentKey: Key): Boolean {
        if (!isRunning(componentKey)) return false
        val component = registered.firstOrNull { it.identity == componentKey } ?: return false

        doAsync { component.stop() }

        return true
    }

    inline fun <reified T : Component> disable() = associations[T::class]?.let { disable(it) } ?: false

    fun restart(componentKey: Key) {
        val component = registered.firstOrNull { it.identity == componentKey } ?: return

        doAsync { component.stop() }
        doAsync { component.start() }
    }

    inline fun <reified T : Component> restart() = associations[T::class]?.let { restart(it) }

    @Serializable
    data class ComponentConfiguration(
        val isAutoStart: Boolean,
        val isBlocked: Boolean,
    )

}