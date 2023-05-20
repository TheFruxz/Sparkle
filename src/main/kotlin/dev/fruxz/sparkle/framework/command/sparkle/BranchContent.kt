package dev.fruxz.sparkle.framework.command.sparkle

import dev.fruxz.sparkle.framework.command.context.BranchExecutionContext
import dev.fruxz.sparkle.framework.modularity.component.Component
import dev.fruxz.sparkle.framework.modularity.component.ComponentManager.registered
import dev.fruxz.sparkle.framework.modularity.component.ComponentManager.running
import dev.fruxz.sparkle.framework.system.sparkle
import dev.fruxz.sparkle.framework.util.cache.CachedProperty
import dev.fruxz.sparkle.framework.util.cache.cached
import dev.fruxz.sparkle.framework.ux.effect.sound.SoundEffect
import dev.fruxz.sparkle.framework.ux.effect.sound.SoundLibrary
import dev.fruxz.sparkle.framework.ux.messaging.Transmission
import dev.fruxz.stacked.extension.subKey
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import kotlin.time.Duration

data class BranchContent<T>(
    val key: Key,
    val cacheDuration: Duration,
    private val tabGenerator: () -> List<String>, // TODO maybe set or collection is better?
    private val contentGenerator: BranchExecutionContext.(List<String>) -> T?,
    private val displayGenerator: () -> String = { key.value() },
) : Keyed {

    private var cachedTab: List<String>? by cached(keep = cacheDuration) { null }

    override fun key() = this.key

    fun generateTab(): List<String> = when {
        cacheDuration.isPositive() -> cachedTab ?: tabGenerator().also { cachedTab = it }
        else -> tabGenerator()
    }

    fun generateContent(executionContext: BranchExecutionContext, input: List<String>): T? = contentGenerator(executionContext, input)

    fun generateDisplay(): String = displayGenerator()

    companion object {

        fun <T> of(
            key: Key,
            tabGenerator: () -> List<String>,
            contentGenerator: BranchExecutionContext.(List<String>) -> T?,
            displayGenerator: () -> String = { "<${key.value()}>" },
            cacheDuration: Duration = Duration.ZERO,
        ): BranchContent<T> = BranchContent(key, cacheDuration, tabGenerator, contentGenerator, displayGenerator)

        fun <T> of(
            key: Key,
            tabGenerator: () -> List<String>,
            contentGenerator: BranchExecutionContext.(List<String>) -> T?,
            displayGenerator: () -> String = { "<${key.value()}>" },
            cacheDuration: CachedProperty.CacheDuration,
        ): BranchContent<T> = BranchContent(key, cacheDuration.duration, tabGenerator, contentGenerator, displayGenerator)

        // JVM

        fun static(vararg options: String): BranchContent<String> = of(
            key = sparkle.key().subKey("static"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { options.toList() },
            contentGenerator = { it.joinToString(" ").takeIf { it in options } },
            displayGenerator = { options.joinToString("|") },
        )

        fun string(examples: List<String> = emptyList()): BranchContent<String> = of(
            key = sparkle.key().subKey("string"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { examples },
            contentGenerator = { it.joinToString(" ") },
            displayGenerator = { examples.joinToString("|") },
        )

        fun int(examples: Iterable<Int> = 0..99): BranchContent<Int> = of(
            key = sparkle.key().subKey("int"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { examples.map(Int::toString) },
            contentGenerator = { it.joinToString(" ").toIntOrNull() },
            displayGenerator = { when {
                examples is IntRange -> "${examples.first}..${examples.last}"
                examples.count() <= 5 -> examples.joinToString("|")
                else -> with(examples.sorted()) { "${this[0]}|${this[1]}|...|${this[2]}|${this[3]}" }
            } },
        )

        fun long(examples: Iterable<Long> = 0L..99L): BranchContent<Long> = of(
            key = sparkle.key().subKey("long"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { examples.map(Long::toString) },
            contentGenerator = { it.joinToString(" ").toLongOrNull() },
            displayGenerator = { when {
                examples is LongRange -> "${examples.first}..${examples.last}"
                examples.count() <= 5 -> examples.joinToString("|")
                else -> with(examples.sorted()) { "${this[0]}|${this[1]}|...|${this[2]}|${this[3]}" }
            } },
        )

        fun double(examples: ClosedRange<Double> = 0.0..1.0): BranchContent<Double> = of(
            key = sparkle.key().subKey("double"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { listOf(examples.start, examples.endInclusive).map(Double::toString) },
            contentGenerator = { it.joinToString(" ").toDoubleOrNull() },
            displayGenerator = { "${examples.start}..${examples.endInclusive}" },
        )

        fun float(examples: ClosedRange<Float> = 0F..1F): BranchContent<Float> = of(
            key = sparkle.key().subKey("float"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { listOf(examples.start, examples.endInclusive).map(Float::toString) },
            contentGenerator = { it.joinToString(" ").toFloatOrNull() },
            displayGenerator = { "${examples.start}..${examples.endInclusive}" },
        )

        // Bukkit

        fun onlinePlayer(): BranchContent<Player> = of(
            key = Key.key("player"),
            cacheDuration = Duration.ZERO,
            tabGenerator = { Bukkit.getOnlinePlayers().map { it.name } },
            contentGenerator = { performer.server.getPlayer(it.joinToString(" ")) }
        )

        fun offlinePlayer(onlyCached: Boolean = false): BranchContent<OfflinePlayer> = of(
            key = Key.key("offline_player"),
            cacheDuration = Duration.ZERO,
            tabGenerator = { Bukkit.getOnlinePlayers().map { player -> player.name } },
            contentGenerator = {
                when (onlyCached) {
                    true -> performer.server.getOfflinePlayerIfCached(it.joinToString(" "))
                    else -> Bukkit.getOfflinePlayer(it.joinToString(" "))
                }
            }
        )

        fun material(filter: ((Material) -> Boolean)? = null): BranchContent<Material> = of(
            key = Key.key("material"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = {
                when (filter) {
                    null -> Material.values().map { it.name }
                    else -> Material.values().mapNotNull { it.name.takeIf { _ -> filter(it) } }
                }
            },
            contentGenerator = { Material.getMaterial(it.joinToString(" ").uppercase()) }
        )

        fun entityType(): BranchContent<EntityType> = of(
            key = Key.key("entity"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { EntityType.values().map { it.name } },
            contentGenerator = { EntityType.valueOf(it.joinToString(" ").uppercase()) }
        )

        // Sparkle

        fun librarySound(): BranchContent<SoundEffect> = of(
            key = sparkle.key().subKey("librarySound"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { SoundLibrary.values().map { it.name } },
            contentGenerator = { SoundLibrary.valueOf(it.joinToString(" ").uppercase()).sound }
        )

        fun transmissionTheme(): BranchContent<Transmission.Theme> = of(
            key = sparkle.key().subKey("transmissionTheme"),
            cacheDuration = Duration.INFINITE,
            tabGenerator = { Transmission.Theme.Default.values().map { it.name } },
            contentGenerator = { Transmission.Theme.Default.valueOf(it.joinToString(" ").uppercase()) }
        )

        fun registeredComponent(): BranchContent<Component> = of(
            key = sparkle.key().subKey("component"),
            cacheDuration = Duration.ZERO,
            tabGenerator = { registered.map { it.identity.asString() } },
            contentGenerator = { registered.find { element -> element.identity.asString() == it.joinToString(" ") } }
        )

        fun runningComponent(): BranchContent<Component> = of(
            key = sparkle.key().subKey("runningComponent"),
            cacheDuration = Duration.ZERO,
            tabGenerator = { registered.mapNotNull { component -> component.identity.takeIf { it in running }?.asString() } },
            contentGenerator = { registered.find { element -> element.identity in running && element.identity.asString() == it.joinToString(" ") } }
        )

        fun offlineComponent(): BranchContent<Component> = of(
            key = sparkle.key().subKey("offlineComponent"),
            cacheDuration = Duration.ZERO,
            tabGenerator = { registered.mapNotNull { component -> component.identity.takeIf { it !in running }?.asString() } },
            contentGenerator = { registered.find { element -> element.identity !in running && element.identity.asString() == it.joinToString(" ") } }
        )

    }

}