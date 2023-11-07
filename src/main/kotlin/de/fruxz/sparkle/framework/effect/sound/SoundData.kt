package de.fruxz.sparkle.framework.effect.sound

import dev.fruxz.ascend.tool.smart.identification.Identifiable
import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Emitter
import net.kyori.adventure.sound.Sound.Source
import net.kyori.adventure.sound.Sound.Source.MASTER
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

@Serializable
data class SoundData(
	private val _sound: String,
	val volume: Float,
	val pitch: Float,
	private val _category: String,
) : Identifiable<SoundData>, SoundEffect {

	constructor(type: Sound.Type, volume: Number, pitch: Number, soundSource: Source = MASTER) : this(type.key(), volume.toFloat(), pitch.toFloat(), soundSource)

	constructor(type: Key, volume: Number, pitch: Number, soundSource: Source = MASTER) : this(type.asString(), volume.toFloat(), pitch.toFloat(), soundSource.name)

	val sound: Key
		get() = Key.key(_sound)

	val category: Source
		get() = Source.valueOf(_category)

	val raw: Sound
		get() = Sound.sound(sound, category, volume, pitch)

	override val identity = "${sound.asString()}:${category.name}:($pitch/$volume)"

	override fun play(vararg locations: Location?): Unit = locations.forEach { location ->
		if (location != null) {
			with(location) {
				world.playSound(raw, x, y, z)
			}
		}
	}

	override fun play(vararg worlds: World?, sticky: Boolean): Unit = worlds.forEach { world ->
		if (world != null) {
			if (sticky) {
				world.playSound(raw, Emitter.self())
			} else
				world.playSound(raw)
		}
	}

	override fun play(vararg entities: Entity?, sticky: Boolean): Unit = entities.forEach { entity ->
		if (entity != null) {
			if (sticky) {
				entity.playSound(raw, Emitter.self())
			} else
				entity.playSound(raw)
		}
	}

	override fun play(locations: Set<Location>, entities: Set<Entity>): Unit = locations.forEach { location ->
		entities.forEach { entity ->
			with(location) { entity.playSound(raw, x, y, z) }
		}
	}

}
