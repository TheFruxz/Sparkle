package dev.fruxz.sparkle.server

import com.destroystokyo.paper.ParticleBuilder
import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.extension.data.kotlinVersion
import dev.fruxz.ascend.json.property
import dev.fruxz.ascend.tool.JsonManager
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.system.pluginsFolder
import dev.fruxz.sparkle.framework.util.json.serializer.*
import dev.fruxz.sparkle.framework.ux.inventory.item.Item
import dev.fruxz.sparkle.server.command.SparkleCommand
import dev.fruxz.sparkle.server.component.component.ComponentComponent
import dev.fruxz.sparkle.server.component.demo.DemoComponent
import dev.fruxz.sparkle.server.component.events.EventsComponent
import dev.fruxz.sparkle.server.component.sandox.SandBoxComponent
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
        logger.info("Sparkle successfully loaded ${getResource("delivered.dependencies")?.reader()?.readLines().orEmpty().size} external dependencies via paper!")

        JsonManager.apply {
            addContextual(NamespacedKey::class, NamespacedKeySerializer)
            addContextual(BoundingBox::class, BoundingBoxSerializer)
            addContextual(Item::class, ItemSerializer)
            addContextual(ItemStack::class, ItemStackSerializer)
            addContextual(Location::class, LocationSerializer)
            addContextual(ParticleBuilder::class, ParticleBuilderSerializer)
            addContextual(Particle::class, ParticleSerializer)
            addContextual(UUID::class, UUIDSerializer)
            addContextual(Vector::class, VectorSerializer)
            addContextual(World::class, WorldSerializer)
        }

    }

    onEnable {
        println("Hey! Sparkle ${this.pluginMeta.version} is online! Running Kotlin $kotlinVersion")
    }

    command<SparkleCommand>()

    component<ComponentComponent>()
    component<DemoComponent>()
    component<EventsComponent>()
    component<SandBoxComponent>()

}) {

    companion object {

        const val SYSTEM_IDENTITY = "Sparkle"

        internal val sparkleFolder = (pluginsFolder / "Sparkle").also(Path::createDirectories)

        private val configFile = (sparkleFolder / "config.json").also(Path::createFileAndDirectories)

        var debugMode by property(configFile, "debugMode") { false }
            internal set

    }

}