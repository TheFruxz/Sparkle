package de.moltenKt.paper.tool.effect.sound

import de.moltenKt.core.extension.dump
import de.moltenKt.paper.extension.system
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SoundMelody(
	var delayPerBeat: Duration = .5.seconds,
	var delayPerSound: Duration = Duration.ZERO,
	var repetitions: Int = 0,
	private val _structure: MutableList<MutableList<SoundData>> = mutableListOf(),
) : SoundEffect {

	val structure: List<List<SoundData>>
		get() = _structure

	fun beat(process: SoundMelodyBeat.() -> Unit) {
		_structure.add(SoundMelodyBeat().apply(process).content.toMutableList())
	}

	fun beat(soundData: SoundData): Unit = beat {
		sound(soundData)
	}

	data class SoundMelodyBeat(
		private val _content: MutableList<SoundData> = mutableListOf(),
	) {

		val content: List<SoundData>
			get() = _content

		fun sound(soundData: SoundData) {
			_content.add(soundData)
		}

	}

	private fun executePlay(process: SoundData.() -> Unit): Unit = system.coroutineScope.launch {
		if (repetitions < 0) throw IllegalArgumentException("repetitions cannot be negative")
		if (delayPerSound.isNegative()) throw IllegalArgumentException("delayPerSound cannot be negative")
		if (delayPerBeat.isNegative()) throw IllegalArgumentException("delayPerBeat cannot be negative")

		repeat(1 + repetitions) {
			_structure.toList().forEach { beat ->

				beat.forEach { soundData ->
					soundData.process()
					delay(delayPerSound)
				}

				delay(delayPerBeat)

			}
		}

	}.dump()

	override fun play(vararg locations: Location?): Unit = executePlay {
		play(*locations)
	}

	override fun play(vararg worlds: World?, sticky: Boolean): Unit = executePlay {
		play(*worlds, sticky = sticky)
	}

	override fun play(vararg entities: Entity?, sticky: Boolean): Unit = executePlay {
		play(*entities, sticky = sticky)
	}

}