package de.jet.library.tool.effect.sound

import de.jet.library.extension.effect.buildMelody
import de.jet.library.extension.effect.soundOf
import org.bukkit.Sound.*

object SoundLibrary {

	val NOTIFICATION_FAIL = buildMelody(5, 0) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = .5))
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 0))
	}

	val NOTIFICATION_ERROR = buildMelody(5, 0) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 0))
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 0))
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_BASS, pitch = 0))
	}

	val NOTIFICATION_INFO = buildMelody(10, 0) {
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_PLING, pitch = 1))
		addNextBeat(soundOf(BLOCK_NOTE_BLOCK_PLING, pitch = 2))
	}

	val NOTIFICATION_GENERAL = buildMelody(0, 0) {
		addNextBeat(soundOf(UI_TOAST_IN, pitch = 1.5))
	}

}