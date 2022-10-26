package de.fruxz.sparkle.framework.effect.sound

import de.fruxz.sparkle.framework.extension.effect.beat
import de.fruxz.sparkle.framework.extension.effect.buildMelody
import de.fruxz.sparkle.framework.extension.effect.sound
import de.fruxz.sparkle.framework.extension.effect.soundOf
import de.fruxz.sparkle.framework.extension.time.minecraftTicks
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.Entity
import kotlin.time.Duration.Companion.seconds

enum class SoundLibrary(val sound: SoundEffect) : SoundEffect {

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

	}),

	UI_BUTTON_PRESS(buildMelody {
		beat {
			sound(Sound.BLOCK_LEVER_CLICK, pitch = .7)
		}
	}),
	UI_BUTTON_PRESS_HEAVY(buildMelody {
		beat {
			sound(Sound.BLOCK_LEVER_CLICK, pitch = .4)
			sound(Sound.ITEM_BOOK_PAGE_TURN, pitch = 1.5)
		}
	}),
	UI_BUTTON_PRESS_PUNCH(buildMelody {
		delayPerBeat = .05.seconds
		beat(Sound.BLOCK_LEVER_CLICK, pitch = .6)
		beat(Sound.BLOCK_LEVER_CLICK, pitch = .8)
		beat(Sound.BLOCK_LEVER_CLICK, pitch = 1)
	}),

	ITEM_RECEIVE(buildMelody {
		beat {
			sound(soundOf(Sound.ENTITY_ITEM_PICKUP, pitch = .8, volume = .6))
			sound(soundOf(Sound.ITEM_ARMOR_EQUIP_CHAIN, pitch = 1, volume = 1))
			sound(soundOf(Sound.BLOCK_WOODEN_DOOR_OPEN, pitch = 2, volume = .6))
		}
	});

	override fun play(vararg locations: Location?): Unit =
		sound.play(*locations)

	override fun play(vararg entities: Entity?, sticky: Boolean): Unit =
		sound.play(*entities, sticky = sticky)

	override fun play(vararg worlds: World?, sticky: Boolean): Unit =
		sound.play(*worlds, sticky = sticky)

	override fun play(locations: Set<Location>, entities: Set<Entity>): Unit =
		sound.play(locations, entities)

}