package de.moltenKt.paper.tool.effect.sound

import de.moltenKt.paper.extension.effect.soundOf
import de.moltenKt.paper.extension.paper.onlinePlayers
import de.moltenKt.paper.tool.effect.CrossBasedEffect
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source.MASTER
import org.bukkit.World
import org.bukkit.entity.Entity

/**
 * This interface defines that it is a sound effect, which
 * can be played to multiple targets.
 * ***To use api as easy as possible, use the soundOf() function!***
 * @author Fruxz
 * @since 1.0
 */
interface SoundEffect : CrossBasedEffect {

	/**
	 * This function plays the given [SoundEffect] to every player
	 * in the given [worlds]. If [sticky] is true (default = true),
	 * the sound follows the player, if false, the sound stays at
	 * the location played, even if the player moves away.
	 * @param worlds The worlds to play the sound in.
	 * @param sticky Whether the sound should follow the player.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun play(vararg worlds: World?, sticky: Boolean = true)

	/**
	 * This function plays the given [SoundEffect] to every player
	 * specified in [entities]. If [sticky] is true (default = true),
	 * the sound follows the player, if false, the sound stays at
	 * the location played, even if the player moves away.
	 * @param entities The receivers of the sound played.
	 * @param sticky Whether the sound should follow the player.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun play(vararg entities: Entity?, sticky: Boolean)

	override fun play(vararg entities: Entity?): Unit =
		play(*entities, sticky = true)

	/**
	 * This function plays the given [SoundEffect] to every player
	 * online on the server. If [sticky] is true (default = true),
	 * the sound follows the player, if false, the sound stays at
	 * the location played, even if the player moves away.
	 * @param sticky Whether the sound should follow the player.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun broadcast(sticky: Boolean = true): Unit =
		play(*onlinePlayers.toTypedArray(), sticky = sticky)

	companion object {

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
		@JvmStatic
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
		@JvmStatic
		fun soundOf(
			key: Key, volume: Number = 1, pitch: Number = 1, soundSource: Sound.Source = MASTER,
		) = SoundData(key, volume, pitch, soundSource)

		/**
		 * This function creates a [ComplexSoundEffect], containing every [SoundEffect] specified
		 * inside the [soundEffects] [Set].
		 * This [ComplexSoundEffect] can be used, to play multiple sounds at once, without having
		 * to play them line by line.
		 * @param soundEffects The [Set] of [SoundEffect]s to play.
		 * @return A [ComplexSoundEffect] object, that can be used to play the sound.
		 * @see [ComplexSoundEffect]
		 * @see [SoundEffect]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun soundOf(
			soundEffects: Set<SoundEffect>,
		) = ComplexSoundEffect(soundEffects.toSet())

		/**
		 * This function creates a [ComplexSoundEffect], containing every [SoundEffect] specified
		 * inside the [soundEffects] vararg [Array].
		 * This [ComplexSoundEffect] can be used, to play multiple sounds at once, without having
		 * to play them line by line.
		 * @param soundEffects The [Set] of [SoundEffect]s to play.
		 * @return A [ComplexSoundEffect] object, that can be used to play the sound.
		 * @see [ComplexSoundEffect]
		 * @see [SoundEffect]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun soundOf(
			vararg soundEffects: SoundEffect,
		) = soundOf(soundEffects.toSet())

	}

}