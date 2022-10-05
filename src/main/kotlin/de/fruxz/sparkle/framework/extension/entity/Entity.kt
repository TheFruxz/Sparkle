package de.fruxz.sparkle.framework.extension.entity

import de.fruxz.ascend.tool.smart.identification.Identity
import org.bukkit.block.Biome
import org.bukkit.entity.Entity

val <T : Entity> T.identityObject: Identity<T>
	get() = Identity("$uniqueId")

/**
 * This var represents the [Entity.hasGravity] and
 * the [Entity.setGravity] functions.
 * @author Fruxz
 * @since 1.0
 */
var Entity.gravity: Boolean
	get() = hasGravity()
	set(value) {
		setGravity(value)
	}

/**
 * This function returns, if the given [scoreboardTag] is
 * inside the [Entity.getScoreboardTags].
 * The [ignoreCase] defines, if during the checking, the
 * case is ignored, or not.
 * @author Fruxz
 * @since 1.0
 */
fun Entity.hasScoreboardTag(scoreboardTag: String, ignoreCase: Boolean = false) =
	scoreboardTags.any { it.equals(scoreboardTag, ignoreCase) }

/**
 * This function returns, if the given [scoreboardTag]s are
 * all inside the [Entity.getScoreboardTags].
 * The [ignoreCase] defines, if during the checking, the
 * case is ignored, or not.
 * @author Fruxz
 * @since 1.0
 */
fun Entity.hasScoreboardTags(vararg scoreboardTag: String, ignoreCase: Boolean = true) =
	scoreboardTag.all { hasScoreboardTag(it, ignoreCase) }

/**
 * This function adds every scoreboard tag specified in the
 * [scoreboardTag] vararg.
 * @author Fruxz
 * @since 1.0
 */
fun Entity.addScoreboardTags(vararg scoreboardTag: String) {
	scoreboardTags += scoreboardTag
}

/**
 * This function removes every scoreboard tag specified in
 * the [scoreboardTag] vararg.
 * @author Fruxz
 * @since 1.0
 */
fun Entity.removeScoreboardTags(vararg scoreboardTag: String) {
	scoreboardTags -= scoreboardTag.toSet()
}

/**
 * This computational value returns the biome of the
 * block, where the player is currently located.
 * @author Fruxz
 * @since 1.0
 */
val Entity.biome: Biome
	get() = location.block.biome