package dev.fruxz.sparkle.framework.ux.panel

import dev.fruxz.ascend.extension.forceCast
import dev.fruxz.ascend.extension.objects.takeIfInstance
import dev.fruxz.ascend.tool.map.list.MutableListMap
import dev.fruxz.ascend.tool.smart.identity.RelatedIdentity
import dev.fruxz.ascend.tool.smart.identity.RelatedUniq
import dev.fruxz.sparkle.framework.coroutine.dispatcher.asyncDispatcher
import dev.fruxz.sparkle.framework.system.onlinePlayers
import dev.fruxz.sparkle.framework.system.sparkle
import dev.fruxz.sparkle.framework.ux.inventory.container.InventoryUI
import dev.fruxz.sparkle.framework.ux.inventory.container.buildInventory
import dev.fruxz.sparkle.framework.ux.inventory.container.set
import dev.fruxz.sparkle.framework.ux.inventory.item.ItemLike
import dev.fruxz.stacked.extension.asPlainString
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import java.util.*
import kotlin.coroutines.CoroutineContext

open class Panel(
    open val label: ComponentLike = Component.empty(),
    open val format: PanelFormat = PanelFormat.ofLines(PanelFormat.InventoryLines(3)),
    open val flags: Set<PanelFlag> = emptySet(),
    open val sound: PanelSound = PanelSound(onOpen = null, onClose = null),
    open val content: Map<Int, ItemLike> = emptyMap(),
    open val lazyContent: Map<Int, Deferred<ItemLike>> = emptyMap(),
    open val displayContext: CoroutineContext = sparkle.asyncDispatcher,
    open val updateContext: CoroutineContext = sparkle.asyncDispatcher,
    open val uuid: UUID = UUID.randomUUID(),
) : InventoryUI, RelatedUniq<Panel, UUID>, Keyed {

    @JvmInline
    value class ClickAction(val action: suspend (InventoryClickEvent) -> Unit)

    internal open val clickActions = MutableListMap<Int, ClickAction>()

    override fun key(): Key = Key.key("sparkle", uuid.toString())

    /**
     * Generates a string, which can be used to identify this panel.
     */
    fun identifier(): String = "'${label.asPlainString}'@${key()}"

    override val identity: RelatedIdentity<Panel, UUID> by lazy {
        RelatedIdentity(uuid)
    }

    internal open val compileAddIns = MutableListMap<CompilePhase, (Inventory, Panel) -> Unit>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun produce(): Inventory {
        val holder = PanelHolder(this)
        val inventory = when (format) {
            is PanelFormat.SizePanelFormat -> buildInventory(format.forceCast<PanelFormat.SizePanelFormat>().size.size, label, holder)
            is PanelFormat.LinesPanelFormat -> buildInventory(format.forceCast<PanelFormat.LinesPanelFormat>().lines.lines * 9, label, holder)
            is PanelFormat.TypePanelFormat -> buildInventory(format.forceCast<PanelFormat.TypePanelFormat>().type, label, holder)
        }

        // bootstrap
        holder.inventoryState = inventory
        compileAddIns[CompilePhase.BOOTSTRAP]?.forEach { it(inventory, this) }

        // decorate
        content.forEach(inventory::set)
        compileAddIns[CompilePhase.DECORATED]?.forEach { it(inventory, this) }

        // prepare
        lazyContent.forEach { (slot, item) ->
            item.invokeOnCompletion { inventory[slot] = item.getCompleted() }
        }
        compileAddIns[CompilePhase.PREPARED]?.forEach { it(inventory, this) }

        return inventory
    }

    fun triggerRefresh(target: Player): Boolean {
        val open = target.openInventory
        val holder = open.topInventory.holder?.takeIfInstance<PanelHolder>() ?: return false

        holder.refresh()

        return true
    }

    fun triggerRefresh(targets: Iterable<Player> = onlinePlayers): Boolean {
        return targets.count(::triggerRefresh) > 0
    }

    fun toMutablePanel() = MutablePanel(
        label = label,
        format = format,
        flags = flags.toMutableSet(),
        sound = sound,
        content = content.toMutableMap(),
        lazyContent = lazyContent.toMutableMap(),
        displayContext = displayContext,
        updateContext = updateContext,
    ).apply {
        this.clickActions.putAll(this@Panel.clickActions)
        this.compileAddIns.putAll(this@Panel.compileAddIns)
    }

    enum class CompilePhase {

        /**
         * After the inventory of the panel is created.
         */
        BOOTSTRAP,

        /**
         * After the static content is placed inside the inventory
         */
        DECORATED,

        /**
         * After the lazy content is scheduled to be placed inside the inventory
         */
        PREPARED;

    }

}