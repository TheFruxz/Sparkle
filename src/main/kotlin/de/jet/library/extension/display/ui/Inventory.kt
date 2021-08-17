package de.jet.library.extension.display.ui

import de.jet.library.tool.display.item.Item
import de.jet.library.tool.display.ui.inventory.Container
import de.jet.library.tool.display.ui.panel.Panel
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

fun buildContainer(lines: Int = 3, action: Container.() -> Unit) = Container(size = lines*9).apply(action)

fun buildPanel(lines: Int = 3, action: Panel.() -> Unit) = Panel(lines = lines).apply(action)

fun Inventory.copyRaw(title: Component) = Bukkit.createInventory(this.holder, this.size).apply {
	contents = this.contents
	storageContents = this.storageContents
}

operator fun Inventory.get(slot: Int) = getItem(slot)

operator fun Inventory.set(slot: Int, itemStack: ItemStack) = setItem(slot, itemStack)

operator fun Inventory.set(slot: Int, item: Item) = setItem(slot, item.produce())

operator fun PlayerInventory.get(slot: Int) = getItem(slot)

operator fun PlayerInventory.set(slot: Int, itemStack: ItemStack) = setItem(slot, itemStack)

operator fun PlayerInventory.set(slot: Int, item: Item) = setItem(slot, item.produce())