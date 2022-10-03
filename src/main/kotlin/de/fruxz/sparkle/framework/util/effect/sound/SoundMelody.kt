package de.fruxz.sparkle.framework.util.effect.sound

import de.fruxz.ascend.extension.dump
import de.fruxz.ascend.extension.time.inWholeMinecraftTicks
import de.fruxz.sparkle.framework.util.extension.system
import de.fruxz.sparkle.framework.util.extension.time.minecraftTicks
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import kotlin.time.Duration

@Serializable
class SoundMelody(
	private var ticksPerBeat: Long = 10,
	private var ticksPerSound: Long = 0,
	var repetitions: Int = 0,
	private val _structure: MutableList<MutableList<SoundData>> = mutableListOf(),
) : SoundEffect {

	var delayPerBeat: Duration
		get() = ticksPerBeat.takeIf { it > 0 }?.minecraftTicks ?: Duration.ZERO
		set(value) {
			ticksPerBeat = value.inWholeMinecraftTicks
		}

	var delayPerSound: Duration
		get() = ticksPerSound.takeIf { it > 0 }?.minecraftTicks ?: Duration.ZERO
		set(value) {
			ticksPerSound = value.inWholeMinecraftTicks
		}

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

	override fun play(locations: Set<Location>, entities: Set<Entity>) = executePlay {
		play(locations, entities)
	}

}