package dev.fruxz.sparkle.framework.entity

import org.bukkit.attribute.Attribute
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.LivingEntity

var LivingEntity.attributiveMaxHealth: Double
    get() {
        @Suppress("DEPRECATION")
        return getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue ?: maxHealth
    }
    set(value) {
        getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = value
    }

fun LivingEntity.fillHealth() {
    health = attributiveMaxHealth
}

fun HumanEntity.fillFoodLevel(withSaturation: Boolean = true) {
    if (withSaturation) saturation = 20F
    foodLevel = 20
}