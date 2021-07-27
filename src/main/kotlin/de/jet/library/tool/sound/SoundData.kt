package de.jet.library.tool.sound

import de.jet.library.structure.smart.Identifiable
import kotlinx.serialization.Serializable
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.SoundCategory.MASTER

@Serializable
data class SoundData(
	var type: Sound,
	var volume: Double,
	var pitch: Double,
	var category: SoundCategory,
) : Identifiable<SoundData> {

	constructor(type: Sound, volume: Number, pitch: Number, soundCategory: SoundCategory = MASTER) : this(type, volume.toDouble(), pitch.toDouble(), soundCategory)

	override val id: String
		get() = "${type.name}:${category.name}:($pitch/$volume)"

}
