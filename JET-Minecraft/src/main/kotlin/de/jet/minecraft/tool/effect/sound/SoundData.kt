package de.jet.minecraft.tool.effect.sound

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.minecraft.tool.effect.Effect
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.SoundCategory.MASTER

@Serializable
@SerialName("EffectSoundData")
data class SoundData(
	@SerialName("soundType") var type: Sound,
	var volume: Float,
	var pitch: Float,
	var category: SoundCategory,
) : Identifiable<SoundData>, Effect {

	constructor(type: Sound, volume: Number, pitch: Number, soundCategory: SoundCategory = MASTER) : this(type, volume.toFloat(), pitch.toFloat(), soundCategory)

	override val identity: String
		get() = "${type.name}:${category.name}:($pitch/$volume)"

}
