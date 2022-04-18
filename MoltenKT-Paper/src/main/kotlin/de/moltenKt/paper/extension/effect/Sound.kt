package de.moltenKt.paper.extension.effect

import de.moltenKt.paper.tool.effect.sound.SoundData
import de.moltenKt.paper.tool.effect.sound.SoundMelody
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Emitter
import net.kyori.adventure.sound.Sound.Source.MASTER
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

fun soundOf(
	type: Sound.Type, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
) = SoundData(type, volume, pitch, soundSource)

fun soundOf(
	key: Key, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
) = SoundData(key, volume, pitch, soundSource)

@JvmName("playSoundEffectSmartly")
fun playSoundEffect(
	location: Location,
	vararg soundData: SoundData,
) = soundData.forEach { element ->
	location.playSoundEffect(element)
}

fun Entity.playSoundEffect(
	vararg soundData: SoundData,
) = soundData.forEach { element ->
	this@playSoundEffect.playSound(element.computeRaw(), Emitter.self())
}

fun World.playSoundEffect(
	vararg soundData: SoundData,
) = soundData.forEach { element ->
	this.playSound(element.computeRaw(), Emitter.self())
}

fun Location.playSoundEffect(
	vararg soundData: SoundData,
) = soundData.forEach { element ->
	world.playSound(element.computeRaw(), x, y, z)
}

fun buildMelody(
	insideDelay: Long,
	wholeRepeats: Int,
	sync: Boolean = true,
	block: SoundMelody.Builder.() -> Unit,
) = SoundMelody(insideDelay, wholeRepeats, sync).builder.apply(block).produce()
