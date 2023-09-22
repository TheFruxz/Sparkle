package dev.fruxz.sparkle.framework.coroutine.dispatcher

import dev.fruxz.ascend.extension.time.inWholeMinecraftTicks
import dev.fruxz.sparkle.framework.system.debugLog
import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.milliseconds

@OptIn(InternalCoroutinesApi::class)
class PluginCoroutineDispatcher(
    private val bukkitPlugin: Plugin,
    private val isAsync: Boolean = true,
) : CoroutineDispatcher(), Delay {

    init {
        debugLog { "Created new PluginCoroutineDispatcher for ${bukkitPlugin.name} with isAsync=$isAsync" }
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!context.isActive) return

        when {
            !isAsync && Bukkit.isPrimaryThread() -> block.run()
            !isAsync -> bukkitPlugin.server.globalRegionScheduler.run(bukkitPlugin) { block.run() }
            else -> bukkitPlugin.server.asyncScheduler.runNow(bukkitPlugin) { block.run() }
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        when {
            !isAsync -> bukkitPlugin.server.globalRegionScheduler.runDelayed(
                bukkitPlugin,
                { continuation.apply { resumeUndispatched(Unit) } },
                timeMillis.milliseconds.inWholeMinecraftTicks
            )
            else -> bukkitPlugin.server.asyncScheduler.runDelayed(
                bukkitPlugin,
                { continuation.apply { resumeUndispatched(Unit) } },
                timeMillis.milliseconds.inWholeMilliseconds,
                TimeUnit.MILLISECONDS,
            )
        }.let { task ->
            continuation.invokeOnCancellation { task.cancel() }
        }
    }

}