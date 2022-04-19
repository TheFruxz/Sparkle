package de.moltenKt.paper.tool.effect

import org.bukkit.Location

interface LocationBasedEffect {

	fun play(vararg locations: Location?)

}