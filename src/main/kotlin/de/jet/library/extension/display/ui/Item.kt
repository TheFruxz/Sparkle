package de.jet.library.extension.display.ui

import de.jet.library.extension.paper.getOfflinePlayer
import de.jet.library.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.library.tool.display.color.ColorType
import de.jet.library.tool.display.color.DyeableMaterial
import de.jet.library.tool.display.item.Item
import de.jet.library.tool.display.item.action.ActionCooldown
import de.jet.library.tool.display.item.action.ItemClickAction
import de.jet.library.tool.display.item.action.ItemInteractAction
import de.jet.library.tool.display.item.quirk.Quirk
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

// Modification / change

val Material.colorType: ColorType?
	get() = ColorType.fromMaterial(this)

val Material.dyeable: DyeableMaterial?
	get() = DyeableMaterial.fromMaterial(this)

@Throws(IllegalArgumentException::class)
fun Material.changeColor(newColorType: ColorType): Material {
	val material = dyeable

	if (material != null) {
		return material.withColor(newColorType)
	} else
		throw IllegalArgumentException("The material '$name' is not colored!")

}

val ItemStack.materialColorType: ColorType?
	get() = type.colorType

val ItemStack.materialToDyeable: DyeableMaterial?
	get() = type.dyeable

@Throws(IllegalArgumentException::class)
fun ItemStack.changeColor(newColorType: ColorType) = type.changeColor(newColorType)

// Generation / creation

val Material.item: Item
	get() = Item(this)

val Material.itemStack: ItemStack
	get() = ItemStack(this)

val ItemStack.item: Item
	get() = Item(this)

fun Material.spawnEntity(location: Location, amount: Int = 1) = item.putSize(amount).spawn(location)

fun ItemStack.spawnEntity(location: Location, amount: Int = 1) = item.putSize(amount).spawn(location)

fun item(material: Material) = material.item

fun item(itemStack: ItemStack) = itemStack.item

fun skull(owner: String) = Material.PLAYER_HEAD.item.putQuirk(Quirk.skull { owningPlayer = getOfflinePlayer(owner) })

fun skull(owner: UUID) = Material.PLAYER_HEAD.item.putQuirk(Quirk.skull { owningPlayer = getOfflinePlayer(owner) })

fun skull(owner: OfflinePlayer) = Material.PLAYER_HEAD.item.putQuirk(Quirk.skull { owningPlayer = owner })

fun buildClickAction(async: Boolean = true, stop: Boolean = true, cooldown: ActionCooldown? = null, action: InventoryClickEvent.() -> Unit) =
	ItemClickAction(action, async, stop, cooldown)

fun buildInteractAction(async: Boolean = true, stop: Boolean = true, cooldown: ActionCooldown? = null, action: PlayerInteractAtItemEvent.() -> Unit) =
	ItemInteractAction(action, async, stop, cooldown)