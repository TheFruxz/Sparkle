package de.fruxz.sparkle.tool.display.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.NamespacedKey
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.enchantments.Enchantment

/**
 * This class represents a [Enchantment], but in a [Serializable] way, which
 * can be used, to save and read it easily from a json file for example.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("ItemModification")
data class Modification(
	@SerialName("modificationType") val type: String,
	val level: Int,
) : ConfigurationSerializable {

	constructor(
		enchantment: Enchantment,
		level: Int,
	) : this(enchantment.key.toString(), level)

	constructor(
		map: Map<String, Any>
	) : this(Enchantment.getByKey(NamespacedKey.fromString("${map["type"]}"))!!, (map["level"] as Number).toInt())

	/**
	 * Returns this [Modification] type as a [Enchantment]
	 * @author Fruxz
	 * @since 1.0
	 */
	val enchantment: Enchantment
		get() = Enchantment.getByKey(NamespacedKey.fromString(type))!!

	override fun serialize() = mapOf(
		"type" to type,
		"level" to level,
	)

}
