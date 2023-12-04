package dev.fruxz.sparkle.server

import com.destroystokyo.paper.ParticleBuilder
import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.extension.data.kotlinVersion
import dev.fruxz.ascend.json.property
import dev.fruxz.ascend.tool.JsonManager
import dev.fruxz.sparkle.framework.SparklePlugin
import dev.fruxz.sparkle.framework.coroutine.task.doSync
import dev.fruxz.sparkle.framework.event.dsl.ActiveJITEvent
import dev.fruxz.sparkle.framework.event.dsl.listen
import dev.fruxz.sparkle.framework.event.dsl.listenOnPlayer
import dev.fruxz.sparkle.framework.event.player
import dev.fruxz.sparkle.framework.system.pluginsFolder
import dev.fruxz.sparkle.framework.util.json.serializer.*
import dev.fruxz.sparkle.framework.ux.inventory.item.Item
import dev.fruxz.sparkle.framework.ux.inventory.item.item
import dev.fruxz.sparkle.framework.ux.panel.buildPanel
import dev.fruxz.sparkle.server.command.DemoCommand
import dev.fruxz.sparkle.server.component.demo.DemoListener
import dev.fruxz.sparkle.server.component.events.DamageListener
import dev.fruxz.sparkle.server.component.events.InteractionListener
import dev.fruxz.sparkle.server.component.sandox.SandBoxCommand
import dev.fruxz.sparkle.server.component.sandox.SandBoxManager
import dev.fruxz.sparkle.server.panel.PanelListener
import dev.fruxz.stacked.extension.asStyledComponent
import kotlinx.coroutines.delay
import org.bukkit.*
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import java.nio.file.Path
import java.util.*
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import kotlin.time.Duration.Companion.seconds

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

        val panel = buildPanel {
            label = "Test-Panel".asStyledComponent

            content += 4 to Material.STONE_BUTTON.item

            onClick(4) { e ->
                repeat(2_000) { 1.0 / 2 }
                e.player.sendMessage("Clicked!")
            }

            deferred(2) {
              Material.ACACIA_BUTTON.item
            }

            this[2] = {
                delay(15.seconds)
                Material.ACACIA_BUTTON.item
            }

        }

        listen<PlayerJoinEvent> {
            var test: ActiveJITEvent<*, *>? = null

            test = it.player.listenOnPlayer<PlayerToggleSneakEvent> { event, player ->
                if (event.isSneaking) {
                    doSync {
                        panel.display(player)
                        delay(2.seconds)
                        panel.triggerRefresh(player)
                    }
                } else {
                    test?.unregister()
                }
            }
        }

    }

    SandBoxManager += SandBoxManager.SandBox("hello") {
        it.source.sendMessage("Hello World! Received '${it.input.asParameters()}'")
    }

    command<DemoCommand>()
    command<SandBoxCommand>()

    listener(DemoListener())
    listener(DamageListener())
    listener(InteractionListener())
    listener(PanelListener())

}) {

    companion object {

        const val SYSTEM_IDENTITY = "Sparkle"

        private val sparkleFolder = (pluginsFolder / "Sparkle").also(Path::createDirectories)

        private val configFile: Path = (sparkleFolder / "config.json").also(Path::createFileAndDirectories)

        var debugMode by property(configFile, "debugMode") { false }
            internal set

    }

}