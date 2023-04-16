package dev.fruxz.sparkle.framework.ux.effect.sound

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

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