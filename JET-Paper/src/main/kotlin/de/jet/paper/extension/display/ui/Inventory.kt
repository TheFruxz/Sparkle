package de.jet.paper.extension.display.ui

import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.ui.inventory.Container
import de.jet.paper.tool.display.ui.panel.Panel
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun buildContainer(lines: Int = 3, action: Container.() -> Unit) = Container(size = lines*9).apply(action)

fun emptyContainer(lines: Int = 3) = Container(size = lines*9)

fun buildPanel(lines: Int = 3, action: Panel.() -> Unit) = Panel(lines = lines).apply(action)

fun emptyPanel(lines: Int = 3) = Panel(lines = lines)

operator fun <T : Inventory> T.get(slot: Int) = getItem(slot)

operator fun <T : Inventory> T.set(slot: Int, itemStack: ItemStack) = setItem(slot, itemStack)

operator fun <T : Inventory> T.set(slot: Int, item: Item) = setItem(slot, item.produce())
