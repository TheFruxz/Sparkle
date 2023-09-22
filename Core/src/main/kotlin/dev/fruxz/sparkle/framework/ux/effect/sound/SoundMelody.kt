package dev.fruxz.sparkle.framework.ux.effect.sound

import dev.fruxz.ascend.extension.dump
import dev.fruxz.sparkle.framework.coroutine.task.doAsync
import dev.fruxz.sparkle.framework.system.debugLog
import dev.fruxz.sparkle.framework.util.ticks
import dev.fruxz.sparkle.framework.ux.effect.EffectDsl
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import net.kyori.adventure.sound.Sound
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import kotlin.time.Duration

@Serializable
data class SoundMelody(
    var beatDelay: Duration = 10.ticks,
    var soundDelay: Duration = Duration.ZERO,
    var repetitionDelay: Duration = Duration.ZERO,
    var amount: Int = 1,
    private var structure: List<Beat> = listOf(),
) : SoundEffect {

    @EffectDsl
    fun beat(vararg beat: Beat) { structure += beat }

    @EffectDsl
    fun beat(builder: Beat.() -> Unit) { structure += Beat().apply(builder) }

    @EffectDsl
    fun beat(soundEffect: SoundEffect) { structure += Beat(listOf(soundEffect)) }

    @EffectDsl
    fun beat(sound: Sound.Type, pitch: Number = 1, volume: Number = 1, source: Sound.Source = Sound.Source.MASTER) =
        beat(soundOf(type = sound, pitch = pitch, volume = volume, source = source))

    private fun executePlay(process: SoundEffect.() -> Unit) = doAsync {
        when {
            amount < 1 -> throw IllegalArgumentException("Amount must be greater than 0")
            beatDelay.isNegative() -> throw IllegalArgumentException("Beat delay must be greater than 0")
            soundDelay.isNegative() -> throw IllegalArgumentException("Sound delay must be greater than 0")
            repetitionDelay.isNegative() -> throw IllegalArgumentException("Repetition delay must be greater than 0")
            beatDelay.isInfinite() -> throw IllegalArgumentException("Beat delay must be finite")
            soundDelay.isInfinite() -> throw IllegalArgumentException("Sound delay must be finite")
            repetitionDelay.isInfinite() -> throw IllegalArgumentException("Repetition delay must be finite")
            else -> {
                val lastBeat = structure.lastIndex

                repeat(amount) {
                    structure.withIndex().forEach { (index, beat) ->
                        val lastSound = beat.sounds.lastIndex

                        beat.sounds.withIndex().forEach { (index, sound) ->
                            debugLog { "Playing sound ($sound) $index of beat $index" }
                            sound.process()
                            if (index < lastSound) delay(soundDelay)
                        }

                        if (index < lastBeat) delay(beatDelay)
                    }

                    if (it < (amount - 1)) delay(repetitionDelay)
                }
            }
        }
    }.dump()

    override fun play(vararg locations: Location?) = executePlay {
        play(*locations)
    }

    override fun play(vararg worlds: World?, sticky: Boolean) = executePlay {
        play(*worlds, sticky = sticky)
    }

    override fun play(vararg entities: Entity?, sticky: Boolean) = executePlay {
        play(*entities, sticky = sticky)
    }

    override fun play(locationSet: Set<Location>, entitySet: Set<Entity>) = executePlay {
        play(locationSet, entitySet)
    }

    @Serializable
    data class Beat(
        var sounds: List<SoundEffect> = listOf(),
    ) {

        @EffectDsl
        fun sound(sound: SoundEffect) { sounds += sound }

        fun sound(sound: Sound.Type, pitch: Number = 1, volume: Number = 1, source: Sound.Source = Sound.Source.MASTER) =
            sound(soundOf(type = sound, pitch = pitch, volume = volume, source = source))

    }

}
