package de.moltenKt.paper.tool.effect

import org.bukkit.Location

interface LocationBasedEffect : Effect {

	fun play(vararg locations: Location?)

}