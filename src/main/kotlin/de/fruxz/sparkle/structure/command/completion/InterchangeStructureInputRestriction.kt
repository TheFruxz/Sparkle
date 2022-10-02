package de.fruxz.sparkle.structure.command.completion

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.DoubleArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import de.fruxz.ascend.extension.empty
import de.fruxz.ascend.extension.math.isDouble
import de.fruxz.ascend.extension.math.isLong
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.extension.paper.offlinePlayer
import de.fruxz.sparkle.extension.paper.playerOrNull
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

interface InterchangeStructureInputRestriction<DATATYPE> {

	val type: ArgumentType<DATATYPE>?

	fun isValid(input: String): Boolean

	fun transformer(input: String): DATATYPE

	companion object {

		@JvmStatic
		val NONE = object : InterchangeStructureInputRestriction<Unit> {
			override val type = null
			override fun isValid(input: String) = false
			override fun transformer(input: String) = empty()
		}

		@JvmStatic
		val ANY = object : InterchangeStructureInputRestriction<String> {
			override val type = StringArgumentType.string()
			override fun isValid(input: String) = true
			override fun transformer(input: String) = input
		}

		@JvmStatic
		val WORD = object : InterchangeStructureInputRestriction<String> {
			override val type = StringArgumentType.word()
			override fun isValid(input: String) = !input.contains(" ")
			override fun transformer(input: String) = input
		}

		@JvmStatic
		val STRING = object : InterchangeStructureInputRestriction<String> {
			override val type = StringArgumentType.string()
			override fun isValid(input: String) = true
			override fun transformer(input: String) = input
		}

		@JvmStatic
		val LONG = object : InterchangeStructureInputRestriction<Long> {
			override val type = null
			override fun isValid(input: String) = input.isLong()
			override fun transformer(input: String) = input.toLong()
		}

		@JvmStatic
		val DOUBLE = object : InterchangeStructureInputRestriction<Double> {
			override val type = DoubleArgumentType.doubleArg()
			override fun isValid(input: String) = input.isDouble()
			override fun transformer(input: String) = input.toDouble()
		}

		@JvmStatic
		val BOOLEAN = object : InterchangeStructureInputRestriction<Boolean> {
			override val type = BoolArgumentType.bool()
			override fun isValid(input: String) = input.toBooleanStrictOrNull() != null
			override fun transformer(input: String) = input.toBooleanStrict()
		}

		@JvmStatic
		val ONLINE_PLAYER = object : InterchangeStructureInputRestriction<Player> {
			override val type = null
			override fun isValid(input: String) = (playerOrNull(input) ?: tryOrNull { playerOrNull(UUID.fromString(input)) }) != null
			override fun transformer(input: String) = playerOrNull(input) ?: playerOrNull(UUID.fromString(input))!!
		}

		@JvmStatic
		val OFFLINE_PLAYER = object : InterchangeStructureInputRestriction<OfflinePlayer> {
			override val type = null
			override fun isValid(input: String) = (tryOrNull { offlinePlayer(UUID.fromString(input)) } ?: offlinePlayer(input)).name != null
			override fun transformer(input: String) = tryOrNull { offlinePlayer(UUID.fromString(input)) } ?: offlinePlayer(input)
		}

	}

}