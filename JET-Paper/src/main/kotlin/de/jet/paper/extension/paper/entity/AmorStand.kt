package de.jet.paper.extension.paper.entity

import org.bukkit.entity.ArmorStand

/**
 * This var represents the [ArmorStand.hasGravity] and
 * the [ArmorStand.setGravity] functions.
 * @author Fruxz
 * @since 1.0
 */
var ArmorStand.gravity: Boolean
    get() = hasGravity()
    set(value) {
        setGravity(value)
    }