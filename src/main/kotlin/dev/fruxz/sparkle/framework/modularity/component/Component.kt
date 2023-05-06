package dev.fruxz.sparkle.framework.modularity.component

import com.destroystokyo.paper.utils.PaperPluginLogger
import dev.fruxz.ascend.tool.smart.composition.Composable
import dev.fruxz.sparkle.framework.modularity.Attachable
import dev.fruxz.sparkle.framework.modularity.Hoster
import net.kyori.adventure.key.Key
import org.bukkit.plugin.Plugin
import java.util.logging.Logger

abstract class Component(
    val startup: StartupBehavior,
    val isExperimental: Boolean = false,
    val vendor: Composable<Plugin>? = null,
) : Attachable, Hoster {

    abstract var identity: Key
        internal set // TODO multiple components with same name? Replace the key!

    override val logger: Logger by lazy {
        (vendor?.compose()?.logger ?: PaperPluginLogger.getLogger(identity.asString()))
    }

}