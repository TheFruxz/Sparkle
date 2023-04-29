package dev.fruxz.sparkle.framework.ux.effect.sound

import dev.fruxz.sparkle.framework.ux.effect.EffectDsl
import dev.fruxz.sparkle.framework.ux.effect.playEffect
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

// create single-sound

fun soundOf(
    type: Sound.Type,
    volume: Number = 1,
    pitch: Number = 1,
    source: Sound.Source = Sound.Source.MASTER,
) = SoundData(
    sound = type.key(),
    category = source,
    volume = volume.toFloat(),
    pitch = pitch.toFloat(),
)

fun soundOf(
    type: Key,
    volume: Number = 1,
    pitch: Number = 1,
    source: Sound.Source = Sound.Source.MASTER,
) = SoundData(
    sound = type.key(),
    category = source,
    volume = volume.toFloat(),
    pitch = pitch.toFloat(),
)

// create complex-sound

fun soundOf(
    soundEffects: Set<SoundEffect>,
) = ComplexSoundEffect(soundEffects)

fun soundOf(
    vararg soundEffects: SoundEffect,
) = ComplexSoundEffect(soundEffects.toSet())

// create melody

fun buildMelody(
    builder: SoundMelody.() -> Unit,
) = SoundMelody().apply(builder)

// combinations

operator fun SoundEffect.plus(other: SoundEffect) = when {
    this is ComplexSoundEffect && other !is ComplexSoundEffect -> this.copy(effects = this.effects + other)
    this !is ComplexSoundEffect && other is ComplexSoundEffect -> other.copy(effects = other.effects + this)
    this is ComplexSoundEffect && other is ComplexSoundEffect -> this.copy(effects = this.effects + other.effects)
    else -> ComplexSoundEffect(setOf(this, other))
}

operator fun ComplexSoundEffect.plus(other: SoundEffect): ComplexSoundEffect {
    return when (other) {
        is ComplexSoundEffect -> this.copy(effects = this.effects + other.effects)
        else -> this.copy(effects = this.effects + other)
    }
}

// play

@EffectDsl
fun Entity.playSound(
    vararg soundEffects: SoundEffect,
) = playEffect(*soundEffects)

@EffectDsl
fun World.playSound(
    vararg soundEffects: SoundEffect,
) = soundEffects.forEach { effect ->
    effect.play(this)
}

@EffectDsl
fun Location.playSound(
    vararg soundEffects: SoundEffect,
) = playEffect(*soundEffects)