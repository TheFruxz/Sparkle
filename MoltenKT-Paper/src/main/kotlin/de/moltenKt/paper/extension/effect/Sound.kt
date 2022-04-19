package de.moltenKt.paper.extension.effect

import de.moltenKt.paper.tool.effect.sound.SoundData
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import de.moltenKt.paper.tool.effect.sound.SoundMelody
import de.moltenKt.paper.tool.effect.sound.SoundMelody.SoundMelodyBeat
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source.MASTER
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

/**
 * This function creates a [SoundData], that can be used to simply play the sound,
 * or to play inside a [SoundMelody].
 * @param type The sound (to use custom, use the [soundOf] function with [Key] instead of [Sound.Type])
 * @param volume The volume of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param pitch The pitch of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param soundSource The sound source. NOTE! Players can disable specific sound sources in their settings!
 * @return A [SoundData] object, that can be used to play the sound.
 * @see [SoundData]
 * @see [SoundEffect]
 * @see [SoundMelody]
 * @author Fruxz
 * @since 1.0
 */
fun soundOf(
	type: Sound.Type, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
) = SoundData(type, volume, pitch, soundSource)

/**
 * This function creates a [SoundData], that can be used to simply play the sound,
 * or to play inside a [SoundMelody].
 * @param key The sound (to use normal sounds, use the [soundOf] function with [Sound.Type] instead of [Key])
 * @param volume The volume of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param pitch The pitch of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param soundSource The sound source. NOTE! Players can disable specific sound sources in their settings!
 * @return A [SoundData] object, that can be used to play the sound.
 * @see [SoundData]
 * @see [SoundEffect]
 * @see [SoundMelody]
 * @author Fruxz
 * @since 1.0
 */
fun soundOf(
	key: Key, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
) = SoundData(key, volume, pitch, soundSource)

/**
 * This function creates a [SoundData] and directly attaches it to the given [SoundMelodyBeat].
 * @param type The sound (to use custom, use the [sound] function with [Key] instead of [type])
 * @param volume The volume of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param pitch The pitch of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param soundSource The sound source. NOTE! Players can disable specific sound sources in their settings!
 * @return A [SoundData] object, that can be used to play the sound.
 * @see [SoundData]
 * @see [SoundEffect]
 * @see [SoundMelody]
 * @author Fruxz
 * @since 1.0
 */
fun SoundMelodyBeat.sound(
	type: Sound.Type, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
) = sound(SoundData(type, volume, pitch, soundSource))

/**
 * This function creates a [SoundData] and directly attaches it to the given [SoundMelodyBeat].
 * @param key The sound (to use custom, use the [sound] function with [Sound.Type] instead of [Key])
 * @param volume The volume of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param pitch The pitch of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param soundSource The sound source. NOTE! Players can disable specific sound sources in their settings!
 * @return A [SoundData] object, that can be used to play the sound.
 * @see [SoundData]
 * @see [SoundEffect]
 * @see [SoundMelody]
 * @author Fruxz
 * @since 1.0
 */
fun SoundMelodyBeat.sound(
	key: Key, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
) = sound(SoundData(key, volume, pitch, soundSource))

/**
 * This function creates a [SoundData] and directly attaches it to the given [SoundMelody] as
 * a separate beat. This should be used, if you want to add a beat to the [SoundMelody], that
 * only contains a single [SoundData].
 * @param type The sound (to use custom, use the [sound] function with [Key] instead of [Sound.Type])
 * @param volume The volume of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param pitch The pitch of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param soundSource The sound source. NOTE! Players can disable specific sound sources in their settings!
 * @return A [SoundData] object, that can be used to play the sound.
 * @see [SoundData]
 * @see [SoundEffect]
 * @see [SoundMelody]
 * @author Fruxz
 * @since 1.0
 */
fun SoundMelody.beat(
	type: Sound.Type, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
) = beat(SoundData(type, volume, pitch, soundSource))

/**
 * This function creates a [SoundData] and directly attaches it to the given [SoundMelody] as
 * a separate beat. This should be used, if you want to add a beat to the [SoundMelody], that
 * only contains a single [SoundData].
 * @param type The sound (to use custom, use the [sound] function with [Sound.Type] instead of [Key])
 * @param volume The volume of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param pitch The pitch of the sound, ranging from 0.0 to 2.0. This [Number] will be converted to a [Float]!
 * @param soundSource The sound source. NOTE! Players can disable specific sound sources in their settings!
 * @return A [SoundData] object, that can be used to play the sound.
 * @see [SoundData]
 * @see [SoundEffect]
 * @see [SoundMelody]
 * @author Fruxz
 * @since 1.0
 */
fun SoundMelody.beat(
	key: Key, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
) = beat(SoundData(key, volume, pitch, soundSource))

/**
 * This function creates a [SoundMelody] with the default values and applies
 * the given [process] to it. This helps to easily and fast create and build
 * [SoundMelody]s, with the DSL like syntax.
 * @author Fruxz
 * @since 1.0
 */
fun buildMelody(process: SoundMelody.() -> Unit): SoundMelody = SoundMelody().apply(process)

/**
 * This function utilizes the [Entity.playEffect] function, to play the [soundEffects] on the given [Entity].
 * @param soundEffects The [SoundEffect]s to play.
 * @see Entity.playEffect
 * @author Fruxz
 * @since 1.0
 */
fun Entity.playSoundEffect(
	vararg soundEffects: SoundEffect,
): Unit = playEffect(*soundEffects)

/**
 * This function plasy the [soundEffects] on the given [World], so it
 * broadcasts the sound to every player on the given [World].
 * @param soundEffects The [SoundEffect]s to play.
 * @author Fruxz
 * @since 1.0
 */
fun World.playSoundEffect(
	vararg soundEffects: SoundEffect,
) = soundEffects.forEach { effect ->
	effect.play(this)
}

/**
 * This function utilizes the [Location.playEffect] function, to play the [soundEffects] on the given [Location].
 * @param soundEffects The [SoundEffect]s to play.
 * @see Location.playEffect
 * @author Fruxz
 * @since 1.0
 */
fun Location.playSoundEffect(
	vararg soundEffects: SoundEffect,
) = playEffect(*soundEffects)
