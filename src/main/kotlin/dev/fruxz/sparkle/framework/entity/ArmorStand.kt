package dev.fruxz.sparkle.framework.entity

import dev.fruxz.sparkle.framework.ux.inventory.item.ItemLike
import dev.fruxz.sparkle.framework.ux.inventory.item.itemStack
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

var ArmorStand.arms: Boolean
    get() = hasArms()
    set(value) = setArms(value)

operator fun ArmorStand.set(equipmentSlot: EquipmentSlot, itemStack: ItemStack) =
    setItem(equipmentSlot, itemStack)

operator fun ArmorStand.set(equipmentSlot: EquipmentSlot, material: Material) =
    setItem(equipmentSlot, material.itemStack)

operator fun ArmorStand.set(equipmentSlot: EquipmentSlot, itemLike: ItemLike) =
    setItem(equipmentSlot, itemLike.produce())

operator fun ArmorStand.get(equipmentSlot: EquipmentSlot): ItemStack =
    getItem(equipmentSlot)