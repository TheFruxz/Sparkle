package de.moltenKt.paper.extension.paper.entity

import de.moltenKt.paper.extension.display.ui.itemStack
import de.moltenKt.paper.tool.display.item.Item
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

/**
 * This var represents the [Entity.hasGravity] and
 * the [Entity.setGravity] functions.
 * @author Fruxz
 * @since 1.0
 */
var Entity.gravity: Boolean
    get() = hasGravity()
    set(value) {
        setGravity(value)
    }

/**
 * This var represents the [ArmorStand.hasArms] and [ArmorStand.setArms] functions.
 * @author Fruxz
 * @since 1.0
 */
var ArmorStand.arms: Boolean
    get() = hasArms()
    set(value) {
        setArms(value)
    }

/**
 * This function sets the item of the [ArmorStand] at the given [equipmentSlot].
 * This function utilizes the [ArmorStand.setItem] function.
 * @author Fruxz
 * @since 1.0
 */
operator fun ArmorStand.set(equipmentSlot: EquipmentSlot, itemStack: ItemStack) =
    setItem(equipmentSlot, itemStack)

/**
* This function sets the item of the [ArmorStand] at the given [equipmentSlot].
* This function utilizes the [ArmorStand.setItem] function.
* @author Fruxz
* @since 1.0
*/
operator fun ArmorStand.set(equipmentSlot: EquipmentSlot, item: Item) =
    setItem(equipmentSlot, item.produce())

/**
* This function sets the item of the [ArmorStand] at the given [equipmentSlot].
* This function utilizes the [ArmorStand.setItem] function.
* @author Fruxz
* @since 1.0
*/
operator fun ArmorStand.set(equipmentSlot: EquipmentSlot, material: Material) =
    setItem(equipmentSlot, material.itemStack)

/**
 * This function gets the item of the [ArmorStand] at the given [equipmentSlot].
 * This function utilizes the [ArmorStand.getItem] function.
 * @author Fruxz
 * @since 1.0
 */
operator fun ArmorStand.get(equipmentSlot: EquipmentSlot): ItemStack =
    getItem(equipmentSlot)