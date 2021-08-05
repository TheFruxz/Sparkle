package de.jet.library.extension.effect

import de.jet.library.tool.effect.sound.SoundData
import de.jet.library.tool.effect.sound.SoundMelody
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
	Sound.sound(type, category, volume, pitch)
}

fun Entity.playSoundEffect(
	soundData: SoundData,
) = playSound(generateRAWSoundEffect(soundData))

fun World.playSoundEffect(
	soundData: SoundData,
) = playSound(generateRAWSoundEffect(soundData))

fun Location.playSoundEffect(
	soundData: SoundData,
) = world.playSound(generateRAWSoundEffect(soundData))

fun buildMelody(
	insideDelay: Long,
	wholeRepeats: Int,
	sync: Boolean = true,
	block: SoundMelody.Builder.() -> Unit,
) = SoundMelody(insideDelay, wholeRepeats, sync).builder.apply(block)
