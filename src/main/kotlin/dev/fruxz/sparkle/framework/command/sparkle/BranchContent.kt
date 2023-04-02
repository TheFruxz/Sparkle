package dev.fruxz.sparkle.framework.command.sparkle

import de.fruxz.stacked.extension.subKey
import dev.fruxz.sparkle.framework.command.context.BranchExecutionContext
import dev.fruxz.sparkle.framework.sparkle
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

data class BranchContent<T>(
    val key: Key,
    val tabGenerator: BranchExecutionContext.() -> List<String>,
    val contentGenerator: BranchExecutionContext.() -> T?,
) : Keyed {

    override fun key() = this.key

    companion object {

        fun <T> of(
            key: Key,
            tabGenerator: BranchExecutionContext.() -> List<String>,
            contentGenerator: BranchExecutionContext.() -> T?,
        ): BranchContent<T> = BranchContent(key, tabGenerator, contentGenerator)

        fun static(vararg options: String): BranchContent<String> = of(
            key = sparkle.key().subKey("static"),
            tabGenerator = { options.toList() },
            contentGenerator = { parameters.joinToString(" ").takeIf { it in options } }
        )

        fun string(examples: List<String> = emptyList()): BranchContent<String> = of(
            key = sparkle.key().subKey("string"),
            tabGenerator = { examples },
            contentGenerator = { parameters.joinToString(" ") }
        )

        fun int(examples: Iterable<Int> = 0..99): BranchContent<Int> = of(
            key = sparkle.key().subKey("int"),
            tabGenerator = { examples.map(Int::toString) },
            contentGenerator = { parameters.joinToString(" ").toIntOrNull() },
        )

        fun long(examples: Iterable<Long> = 0L..99L): BranchContent<Long> = of(
            key = sparkle.key().subKey("long"),
            tabGenerator = { examples.map(Long::toString) },
            contentGenerator = { parameters.joinToString(" ").toLongOrNull() },
        )

        fun double(examples: ClosedRange<Double> = 0.0..1.0): BranchContent<Double> = of(
            key = sparkle.key().subKey("double"),
            tabGenerator = { listOf(examples.start, examples.endInclusive).map(Double::toString) },
            contentGenerator = { parameters.joinToString(" ").toDoubleOrNull() },
        )

        fun float(examples: ClosedRange<Float> = 0F..1F): BranchContent<Float> = of(
            key = sparkle.key().subKey("float"),
            tabGenerator = { listOf(examples.start, examples.endInclusive).map(Float::toString) },
            contentGenerator = { parameters.joinToString(" ").toFloatOrNull() },
        )

        fun onlinePlayer(): BranchContent<Player> = of(
            key = sparkle.key().subKey("onlinePlayer"),
            tabGenerator = { executor.server.onlinePlayers.map { it.name } },
            contentGenerator = { executor.server.getPlayer(parameters.joinToString(" ")) }
        )

        fun offlinePlayer(onlyCached: Boolean = false): BranchContent<OfflinePlayer> = of(
            key = sparkle.key().subKey("offlinePlayer"),
            tabGenerator = { executor.server.offlinePlayers.map { it.name ?: "unknown" } },
            contentGenerator = {
                when (onlyCached) {
                    true -> executor.server.getOfflinePlayerIfCached(parameters.joinToString(" "))
                    else -> Bukkit.getOfflinePlayer(parameters.joinToString(" "))
                }
            }
        )

        fun material(filter: ((Material) -> Boolean)? = null): BranchContent<Material> = of(
            key = sparkle.key().subKey("material"),
            tabGenerator = {
                when (filter) {
                    null -> Material.values().map { it.name }
                    else -> Material.values().mapNotNull { it.name.takeIf { _ -> filter(it) } }
                }
            },
            contentGenerator = { Material.getMaterial(parameters.joinToString(" ").uppercase()) }
        )

        fun location(): BranchContent<Location> = of(
            key = sparkle.key().subKey("location"),
            tabGenerator = {

                buildList {

                    add("[world:world, @spawn]")

                    Bukkit.getOnlinePlayers().forEach { player ->
                        add("[@${player.name}]")
                    }

                    val locationData: List<Any>

                    when (executor) {
                        is Player -> {

                            add("[@spawn]")

                            locationData = with(executor) {
                                listOf(
                                    location.world.name,
                                    location.x,
                                    location.y,
                                    location.z,
                                    location.yaw,
                                    location.pitch,
                                )
                            }
                        }
                        else -> {
                            locationData = listOf(Bukkit.getWorlds().first().name, 0, 0, 0, 0, 0)
                        }
                    }

                    add("[world:${locationData[0]}, x:${locationData[1]}, y:${locationData[2]}, z:${locationData[3]}]")
                    add("[world:${locationData[0]}, x:${locationData[1]}, y:${locationData[2]}, z:${locationData[3]}, yaw:${locationData[4]}, pitch:${locationData[5]}]")

                }.sortedBy { it.length }

            },
            contentGenerator = {

                    val locationData: List<Any>

                    when (executor) {
                        is Player -> {
                            locationData = with(executor) {
                                listOf(
                                    location.world.name,
                                    location.x,
                                    location.y,
                                    location.z,
                                    location.yaw,
                                    location.pitch,
                                )
                            }
                        }
                        else -> {
                            locationData = listOf(Bukkit.getWorlds().first().name, 0, 0, 0, 0, 0)
                        }
                    }

                    val world = parameters.joinToString(" ").substringAfter("world:").substringBefore(",").trim()
                    val x = parameters.joinToString(" ").substringAfter("x:").substringBefore(",").trim()
                    val y = parameters.joinToString(" ").substringAfter("y:").substringBefore(",").trim()
                    val z = parameters.joinToString(" ").substringAfter("z:").substringBefore(",").trim()
                    val yaw = parameters.joinToString(" ").substringAfter("yaw:").substringBefore(",").trim()
                    val pitch = parameters.joinToString(" ").substringAfter("pitch:").substringBefore(",").trim()

                    when {
                        parameters.joinToString(" ").startsWith("[@spawn]") -> {
                            Bukkit.getWorlds().first().spawnLocation
                        }
                        parameters.joinToString(" ").startsWith("[@") -> {
                            Bukkit.getPlayer(parameters.joinToString(" ").substringAfter("[@").substringBefore("]"))?.location
                        }
                        parameters.joinToString(" ").startsWith("[world:") -> {
                            Location(
                                Bukkit.getWorld(world) ?: Bukkit.getWorlds().first(),
                                x.toDoubleOrNull() ?: locationData[1] as Double,
                                y.toDoubleOrNull() ?: locationData[2] as Double,
                                z.toDoubleOrNull() ?: locationData[3] as Double,
                                yaw.toFloatOrNull() ?: locationData[4] as Float,
                                pitch.toFloatOrNull() ?: locationData[5] as Float,
                            )
                        }
                        else -> {
                            Location(
                                Bukkit.getWorlds().first(),
                                x.toDoubleOrNull() ?: locationData[1] as Double,
                                y.toDoubleOrNull() ?: locationData[2] as Double,
                                z.toDoubleOrNull() ?: locationData[3] as Double,
                                yaw.toFloatOrNull() ?: locationData[4] as Float,
                                pitch.toFloatOrNull() ?: locationData[5] as Float,
                            )
                        }
                    }
            }
        )

    }

}