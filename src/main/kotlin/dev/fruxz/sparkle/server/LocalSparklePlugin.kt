package dev.fruxz.sparkle.server

import com.destroystokyo.paper.ParticleBuilder
import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.extension.data.kotlinVersion
import dev.fruxz.ascend.json.appendGlobalJsonContextual
import dev.fruxz.ascend.json.property
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.system.pluginsFolder
import dev.fruxz.sparkle.framework.util.json.serializer.*
import dev.fruxz.sparkle.framework.ux.inventory.item.Item
import dev.fruxz.sparkle.server.command.SparkleCommand
import dev.fruxz.sparkle.server.component.ComponentComponent
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import java.nio.file.Path
import java.util.*
import kotlin.io.path.createDirectories
import kotlin.io.path.div

class LocalSparklePlugin : SparklePlugin({

    onLoad {
        logger.info("Loaded ${LocalSparkleLoader.dependencies.size} dependencies!")

        appendGlobalJsonContextual(Key::class, AdventureKeySerializer)
        appendGlobalJsonContextual(NamespacedKey::class, NamespacedKeySerializer)
        appendGlobalJsonContextual(BoundingBox::class, BoundingBoxSerializer)
        appendGlobalJsonContextual(Item::class, ItemSerializer)
        appendGlobalJsonContextual(ItemStack::class, ItemStackSerializer)
        appendGlobalJsonContextual(Location::class, LocationSerializer)
        appendGlobalJsonContextual(ParticleBuilder::class, ParticleBuilderSerializer)
        appendGlobalJsonContextual(Particle::class, ParticleSerializer)
        appendGlobalJsonContextual(UUID::class, UUIDSerializer)
        appendGlobalJsonContextual(Vector::class, VectorSerializer)
        appendGlobalJsonContextual(World::class, WorldSerializer)

    }

    onEnable {
        println("Hey! Sparkle ${this.pluginMeta.version} is online! Running Kotlin $kotlinVersion")
    }

    command<SparkleCommand>()
    component<ComponentComponent>()


}) {

    companion object {

        const val SYSTEM_IDENTITY = "Sparkle"

        internal val sparkleFolder = (pluginsFolder / "Sparkle").also(Path::createDirectories)

        private val configFile = (sparkleFolder / "config.json").also(Path::createFileAndDirectories)

        var debugMode by property(configFile, "debugMode") { false }
            internal set

    }

}