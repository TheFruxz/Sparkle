package de.moltenKt.paper.tool.effect.sound

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

data class ComplexSoundEffect(
	val effects: Set<SoundEffect>,
) : SoundEffect {

	override fun play(vararg worlds: World?, sticky: Boolean): Unit =
		effects.forEach { effect -> effect.play(worlds = worlds, sticky) }

	override fun play(vararg entities: Entity?, sticky: Boolean): Unit =
		effects.forEach { effect -> effect.play(entities = entities, sticky) }

	override fun play(vararg locations: Location?): Unit =
		effects.forEach { effect -> effect.play(locations = locations) }

	override fun play(locations: Set<Location>, entities: Set<Entity>): Unit =
		effects.forEach { effect -> effect.play(locations, entities) }

}
