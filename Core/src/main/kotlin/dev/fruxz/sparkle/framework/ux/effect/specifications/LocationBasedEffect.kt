package dev.fruxz.sparkle.framework.ux.effect.specifications

import dev.fruxz.sparkle.framework.ux.effect.Effect
import org.bukkit.Location

interface LocationBasedEffect : Effect {

    fun play(vararg locations: Location?)

}