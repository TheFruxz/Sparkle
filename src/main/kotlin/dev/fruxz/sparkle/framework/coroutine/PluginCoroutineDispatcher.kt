package dev.fruxz.sparkle.framework.coroutine

import de.fruxz.ascend.extension.time.inWholeMinecraftTicks
import dev.fruxz.sparkle.framework.extension.debugLog
import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
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
            !isAsync -> bukkitPlugin.server.scheduler.runTask(bukkitPlugin, block)
            else -> bukkitPlugin.server.scheduler.runTaskAsynchronously(bukkitPlugin, block)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        when {
            !isAsync -> bukkitPlugin.server.scheduler.runTaskLater(bukkitPlugin, Runnable { continuation.apply { resumeUndispatched(Unit) } }, timeMillis.milliseconds.inWholeMinecraftTicks)
            else -> bukkitPlugin.server.scheduler.runTaskLaterAsynchronously(bukkitPlugin, Runnable { continuation.apply { resumeUndispatched(Unit) } }, timeMillis.milliseconds.inWholeMinecraftTicks)
        }.let { task ->
            continuation.invokeOnCancellation { task.cancel() }
        }
    }

}