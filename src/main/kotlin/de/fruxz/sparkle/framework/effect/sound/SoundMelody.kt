package de.fruxz.sparkle.framework.effect.sound

import de.fruxz.ascend.extension.dump
import de.fruxz.ascend.extension.time.inWholeMinecraftTicks
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.extension.time.minecraftTicks
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
	private var _structure: List<List<SoundData>> = listOf(),
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

	var structure: List<List<SoundData>>
		get() = _structure
		set(value) { _structure = value }

	fun beat(process: SoundMelodyBeat.() -> Unit) {
		_structure = _structure.plusElement(SoundMelodyBeat().apply(process).content)
	}

	fun beat(soundData: SoundData): Unit = beat { sound(soundData) }

	data class SoundMelodyBeat(
		private var _content: List<SoundData> = listOf(),
	) {

		val content: List<SoundData>
			get() = _content

		fun sound(soundData: SoundData) {
			_content += soundData
		}

	}

	private fun executePlay(process: SoundData.() -> Unit): Unit = sparkle.coroutineScope.launch {
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