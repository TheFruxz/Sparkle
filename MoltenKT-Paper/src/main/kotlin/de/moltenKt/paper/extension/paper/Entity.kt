package de.moltenKt.paper.extension.paper

import de.moltenKt.core.tool.smart.identification.Identity
import org.bukkit.entity.Entity

val <T : Entity> T.identityObject: Identity<T>
	get() = Identity("$uniqueId")

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