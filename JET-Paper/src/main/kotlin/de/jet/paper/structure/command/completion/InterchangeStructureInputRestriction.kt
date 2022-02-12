package de.jet.paper.structure.command.completion

import de.jet.jvm.extension.empty
import de.jet.jvm.extension.math.isDouble
import de.jet.jvm.extension.math.isLong
import de.jet.jvm.extension.tryOrNull
import de.jet.paper.extension.paper.getOfflinePlayer
import de.jet.paper.extension.paper.getPlayer
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

interface InterchangeStructureInputRestriction<DATATYPE> {

	fun isValid(input: String): Boolean

	fun transformer(input: String): DATATYPE

	companion object {

		val NONE = object : InterchangeStructureInputRestriction<Unit> {
			override fun isValid(input: String) = false
			override fun transformer(input: String) = empty()
		}

		val ANY = object : InterchangeStructureInputRestriction<String> {
			override fun isValid(input: String) = true
			override fun transformer(input: String) = input
		}

		val STRING = object : InterchangeStructureInputRestriction<String> {
			override fun isValid(input: String) = true
			override fun transformer(input: String) = input
		}

		val LONG = object : InterchangeStructureInputRestriction<Long> {
			override fun isValid(input: String) = input.isLong()
			override fun transformer(input: String) = input.toLong()
		}

		val DOUBLE = object : InterchangeStructureInputRestriction<Double> {
			override fun isValid(input: String) = input.isDouble()
			override fun transformer(input: String) = input.toDouble()
		}

		val BOOLEAN = object : InterchangeStructureInputRestriction<Boolean> {
			override fun isValid(input: String) = input.toBooleanStrictOrNull() != null
			override fun transformer(input: String) = input.toBooleanStrict()
		}

		val ONLINE_PLAYER = object : InterchangeStructureInputRestriction<Player> {
			override fun isValid(input: String) = (getPlayer(input) ?: tryOrNull { getPlayer(UUID.fromString(input)) }) != null
			override fun transformer(input: String) = getPlayer(input) ?: getPlayer(UUID.fromString(input))!!
		}

		val OFFLINE_PLAYER = object : InterchangeStructureInputRestriction<OfflinePlayer> {
			override fun isValid(input: String) = (tryOrNull { getOfflinePlayer(UUID.fromString(input)) } ?: getOfflinePlayer(input)).name != null
			override fun transformer(input: String) = tryOrNull { getOfflinePlayer(UUID.fromString(input)) } ?: getOfflinePlayer(input)
		}

	}

}