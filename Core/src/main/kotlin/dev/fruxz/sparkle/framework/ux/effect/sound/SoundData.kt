package dev.fruxz.sparkle.framework.ux.effect.sound

import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

@Serializable
data class SoundData(
    val sound: Key, // TODO create serializer, if not already done
    val category: Sound.Source,
    val volume: Float,
    val pitch: Float,
) : SoundEffect {

    constructor(
        type: Sound.Type,
        volume: Number = 1,
        pitch: Number = 1,
        source: Sound.Source = Sound.Source.MASTER
    ) : this(
        sound = type.key(),
        category = source,
        volume = volume.toFloat(),
        pitch = pitch.toFloat()
    )

    constructor(
        type: Key,
        volume: Number = 1,
        pitch: Number = 1,
        source: Sound.Source = Sound.Source.MASTER,
    ) : this(
        sound = type.key(),
        category = source,
        volume = volume.toFloat(),
        pitch = pitch.toFloat()
    )

    private val raw: Sound by lazy { Sound.sound(sound, category, volume, pitch) }

    override fun play(vararg locations: Location?) = locations.forEach { location ->
        when (location) {
            null -> return@forEach
            else -> with(location) { world.playSound(raw, x, y, z) }
        }
    }

    override fun play(vararg worlds: World?, sticky: Boolean) = worlds.forEach { world ->
        when (sticky) {
            true -> world?.playSound(raw, Sound.Emitter.self())
            else -> world?.playSound(raw)
        }
    }

    override fun play(vararg entities: Entity?, sticky: Boolean) = entities.forEach { entity ->
        when (sticky) {
            true -> entity?.playSound(raw, Sound.Emitter.self())
            else -> entity?.playSound(raw)
        }
    }

    override fun play(locationSet: Set<Location>, entitySet: Set<Entity>) = locationSet.forEach { location ->
        for (receiver in entitySet) {
            when {
                receiver.world != location.world -> continue
                else -> with(location) { receiver.playSound(raw, x, y, z) }
            }
        }
    }

}
