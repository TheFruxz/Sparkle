package de.moltenKt.paper.tool.effect.sound

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.tool.effect.Effect
import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source
import net.kyori.adventure.sound.Sound.Source.MASTER
import org.bukkit.Sound as BukkitSound

@Serializable
data class SoundData(
	var sound: Key,
	var volume: Float,
	var pitch: Float,
	var category: Source,
) : Identifiable<SoundData>, Effect {

	constructor(type: Sound.Type, volume: Number, pitch: Number, soundSource: Source = MASTER) : this(type.key(), volume.toFloat(), pitch.toFloat(), soundSource)

	constructor(type: Key, volume: Number, pitch: Number, soundSource: Source = MASTER) : this(type, volume.toFloat(), pitch.toFloat(), soundSource)

	val bukkitSound: BukkitSound
		get() = BukkitSound.valueOf(sound.value())

	fun computeRaw() = Sound.sound(sound, category, volume, pitch)

	override val identity: String
		get() = "${sound.asString()}:${category.name}:($pitch/$volume)"

}
