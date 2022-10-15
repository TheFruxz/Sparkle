---
description: Play sounds, create sounds
---

# 🔊 SoundEffect

## SoundData

Create sounds with ease, by using SoundData!

```kotlin

val sound = soundOf(Sound.ENTITY_ILLUSIONER_CAST_SPELL, pitch = .6)

fun playMySound(player: Player) = player.playEffect(sound)

```

Things like pitch, volume, and sound source are set by default, but can still be changed quite easily.

SoundEffects may not be so fascinating, but the following stuff, that builds ontop of this, may be!

## SoundMelody

With melodies, you can create a sound effect, which plays multiple sounds, like a jingle or a spooky sound effect.

You can construct & play a sound melody like this:

```kotlin
val sound = buildMelody {
   beat {
      sound(soundOf(ENTITY_ILLUSIONER_CAST_SPELL, pitch = .6))
   }
   beat(soundOf(Sound.BLOCK_NOTE_BLOCK_BANJO, pitch = .1))
   beat(soundOf(Sound.BLOCK_NOTE_BLOCK_BANJO, pitch = .5))
   beat(soundOf(Sound.BLOCK_NOTE_BLOCK_BANJO, pitch = 1))
   beat(soundOf(Sound.BLOCK_NOTE_BLOCK_BANJO, pitch = 1.5))
   beat(soundOf(Sound.BLOCK_NOTE_BLOCK_BANJO, pitch = 2))
   repetitions = 2
   delayPerBeat = .2.seconds
   delayPerSound = Duration.ZERO
}

fun playMySound(player: Player) =
	player.playEffect(sound)

```

The `playEffect` part is the same because the melody itself is also a compatible effect!
