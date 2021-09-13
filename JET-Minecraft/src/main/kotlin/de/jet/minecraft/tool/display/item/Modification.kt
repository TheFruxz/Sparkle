package de.jet.minecraft.tool.display.item

import kotlinx.serialization.Serializable
import org.bukkit.NamespacedKey
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment

@Serializable
data class Modification(
	val type: String,
	val level: Int,
) : ConfigurationSerializable {

	constructor(
		enchantment: Enchantment,
		level: Int,
	) : this(enchantment.key.toString(), level)

	constructor(
		map: Map<String, Any>
	) : this(Enchantment.getByKey(NamespacedKey.fromString("${map["type"]}"))!!, (map["level"] as Number).toInt())

	val enchantment: Enchantment
		get() = Enchantment.getByKey(NamespacedKey.fromString(type))!!

	override fun serialize() = mapOf(
		"type" to type,
		"level" to level,
	)

}
