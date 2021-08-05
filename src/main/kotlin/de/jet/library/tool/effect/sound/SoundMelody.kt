package de.jet.library.tool.effect.sound

import de.jet.library.JET
import de.jet.library.extension.effect.playSoundEffect
import de.jet.library.extension.tasky.task
import de.jet.library.tool.tasky.TemporalAdvice
import org.bukkit.entity.Entity

class SoundMelody(
	var insideDelay: Long,
	var wholeRepeats: Int,
	var content: MutableList<Set<SoundData>>,
	var sync: Boolean = true,
) {

	constructor(insideDelay: Long, wholeRepeats: Int, sync: Boolean = true) : this(insideDelay, wholeRepeats, mutableListOf(), sync)

	val builder: Builder
		get() = Builder(this)

	fun play(receivers: Collection<Entity>) = createPlay {
		receivers.forEach { r -> r.playSoundEffect(it) }
	}

	private fun createPlay(execution: (SoundData) -> Unit) {

		var repeatTimes = 0
		var innerRound = 0

		task(JET.appInstance, TemporalAdvice.ticking(0, insideDelay, !sync)) {
			val currentSounds = content[innerRound]

			currentSounds.forEach(execution)

			innerRound ++
			if (innerRound >= content.size) {
				innerRound = 0
				if (wholeRepeats == - 1 || wholeRepeats > repeatTimes) {
					repeatTimes ++
				} else {
					shutdown()
				}
			}

		}

	}

	class Builder internal constructor(
		base: SoundMelody,
	) {

		private var currentState: SoundMelody = base

		fun produce() = currentState

		fun addToBeat(soundData: SoundData) {
			currentState.content = currentState.content.apply {
				set(currentState.content.lastIndex, currentState.content.last()+soundData)
			}
		}

		fun addNextBeat(vararg soundData: SoundData) {
			currentState.content = currentState.content.apply { add(setOf(*soundData)) }
		}

	}

}