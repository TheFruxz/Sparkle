package de.moltenKt.paper.tool.effect.sound

import de.moltenKt.paper.extension.effect.beat
import de.moltenKt.paper.extension.effect.buildMelody
import de.moltenKt.paper.extension.effect.sound
import de.moltenKt.paper.extension.timing.minecraftTicks
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.Entity

enum class SoundLibrary(val melody: SoundMelody) : SoundEffect {

	NOTIFICATION_FAIL(buildMelody {

		delayPerBeat = 3.minecraftTicks

		beat(Sound.BLOCK_NOTE_BLOCK_BASS)
		beat(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 0)

	}),

	NOTIFICATION_ERROR(buildMelody {

		delayPerBeat = 3.minecraftTicks
		repetitions = 2

		beat {
			sound(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 0)
			sound(Sound.BLOCK_NOTE_BLOCK_BIT, pitch = 0)
		}

	}),

	NOTIFICATION_PROCESS(buildMelody {

		delayPerBeat = 3.minecraftTicks

		beat(Sound.BLOCK_NOTE_BLOCK_PLING)
		beat(Sound.BLOCK_NOTE_BLOCK_PLING, pitch = 2)

	}),

	NOTIFICATION_GENERAL(buildMelody {

		beat {
			sound(Sound.BLOCK_NOTE_BLOCK_BELL, pitch = .9, volume = .1)
			sound(Sound.UI_TOAST_IN, pitch = 1.5)
		}

	}),

	NOTIFICATION_LEVEL(buildMelody {

		delayPerBeat = 3.minecraftTicks

		beat(Sound.ENTITY_PLAYER_LEVELUP)

		beat(Sound.ENTITY_PLAYER_LEVELUP, pitch = 2)

	}),

	NOTIFICATION_WARNING(buildMelody {

		delayPerBeat = 3.minecraftTicks
		repetitions = 2

		beat {
			sound(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 0)
			sound(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 2)
		}

	}),

	NOTIFICATION_ATTENTION(buildMelody {

		delayPerBeat = 2.minecraftTicks
		repetitions = 9

		beat(Sound.BLOCK_NOTE_BLOCK_PLING, pitch = 2)

	}),

	NOTIFICATION_PAYMENT(buildMelody {

		delayPerBeat = 3.minecraftTicks

		beat {
			sound(Sound.BLOCK_NOTE_BLOCK_PLING)
			sound(Sound.ITEM_ARMOR_EQUIP_CHAIN)
		}

		beat(Sound.BLOCK_NOTE_BLOCK_PLING, pitch = 2)

	}),

	NOTIFICATION_APPLIED(buildMelody {

		delayPerBeat = 2.minecraftTicks

		beat(Sound.BLOCK_NOTE_BLOCK_BASS)
		beat(Sound.BLOCK_NOTE_BLOCK_BASS)
		beat(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 2)
		beat(Sound.BLOCK_NOTE_BLOCK_BASS)

	});

	override fun play(vararg locations: Location?): Unit =
		melody.play(*locations)

	override fun play(vararg entities: Entity?, sticky: Boolean): Unit =
		melody.play(*entities, sticky = sticky)

	override fun play(vararg worlds: World?, sticky: Boolean): Unit =
		melody.play(*worlds, sticky = sticky)

	override fun play(locations: Set<Location>, entities: Set<Entity>): Unit =
		melody.play(locations, entities)

}