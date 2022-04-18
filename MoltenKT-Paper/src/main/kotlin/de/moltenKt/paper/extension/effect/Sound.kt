package de.moltenKt.paper.extension.effect

import de.moltenKt.paper.tool.effect.sound.SoundData
import de.moltenKt.paper.tool.effect.sound.SoundMelody
import net.kyori.adventure.sound.Sound
import org.bukkit.Location
import org.bukkit.SoundCategory
import org.bukkit.SoundCategory.MASTER
import org.bukkit.World
import org.bukkit.entity.Entity

fun soundOf(
	type: org.bukkit.Sound, volume: Number = 1, pitch: Number = 1, soundCategory: SoundCategory = MASTER,
) = SoundData(type, volume, pitch, soundCategory)

fun generateRAWSoundEffect(soundData: SoundData) = with(soundData) {
	Sound.sound(sound, category, volume, pitch)
}

@JvmName("playSoundEffectSmartly")
fun playSoundEffect(
	location: Location,
	vararg soundData: SoundData,
) = soundData.forEach { element ->
	location.playSoundEffect(element)
}

fun Entity.playSoundEffect(
	vararg soundData: SoundData,
) = with(location) { soundData.forEach { element ->
	this@playSoundEffect.playSound(generateRAWSoundEffect(element), x, y, z) }
}

fun World.playSoundEffect(
	vararg soundData: SoundData,
) = soundData.forEach { element ->
	this.playSound(generateRAWSoundEffect(element))
}

fun Location.playSoundEffect(
	vararg soundData: SoundData,
) = soundData.forEach { element ->
	world.playSound(generateRAWSoundEffect(element), x, y, z)
}

fun buildMelody(
	insideDelay: Long,
	wholeRepeats: Int,
	sync: Boolean = true,
	block: SoundMelody.Builder.() -> Unit,
) = SoundMelody(insideDelay, wholeRepeats, sync).builder.apply(block).produce()
