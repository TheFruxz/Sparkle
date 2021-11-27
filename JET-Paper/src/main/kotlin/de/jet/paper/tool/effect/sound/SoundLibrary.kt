package de.jet.paper.tool.effect.sound

import de.jet.paper.extension.effect.buildMelody
import de.jet.paper.extension.effect.soundOf
import org.bukkit.Sound.*

object SoundLibrary {

	val NOTIFICATION_FAIL = buildMelody(3, 0) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 1))

		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 0))
	}

	val NOTIFICATION_ERROR = buildMelody(3, 2) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 0))
		addToBeat(soundOf(BLOCK_NOTE_BLOCK_BIT, pitch = 0))
	}

	val NOTIFICATION_INFO = buildMelody(3, 0) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_PLING, pitch = 1))

		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_PLING, pitch = 2))
	}

	val NOTIFICATION_GENERAL = buildMelody(0, 0) {
		addNextBeat(soundOf(ENTITY_PUFFER_FISH_FLOP, pitch = 1.5))
		addToBeat(soundOf(UI_TOAST_IN, pitch = 1.5))
	}

	val NOTIFICATION_LEVEL = buildMelody(3, 0) {
		addNextBeat(soundOf(ENTITY_PLAYER_LEVELUP, pitch = 1))

		addNextBeat(soundOf(ENTITY_PLAYER_LEVELUP, pitch = 2))
	}

	val NOTIFICATION_WARNING = buildMelody(3, 2) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 0))
		addToBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 2))
	}

	val NOTIFICATION_ATTENTION = buildMelody(2, 9) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_PLING, pitch = 2))
	}

	val NOTIFICATION_PAYMENT = buildMelody(3, 0) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_PLING))
		addToBeat(soundOf(ITEM_ARMOR_EQUIP_CHAIN))
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_PLING, pitch = 2))
	}

	val NOTIFICATION_APPLIED = buildMelody(2, 0) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS))
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS))
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 2))
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS))
	}

}