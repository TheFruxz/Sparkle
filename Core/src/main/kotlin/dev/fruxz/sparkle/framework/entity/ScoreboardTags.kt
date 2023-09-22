package dev.fruxz.sparkle.framework.entity

import org.bukkit.entity.Entity

fun Entity.hasScoreboardTag(tag: String, ignoreTags: Boolean = false) = scoreboardTags.any { it.equals(tag, ignoreTags) }

fun Entity.hasScoreboardTags(vararg tags: String, ignoreTags: Boolean = false) = tags.all { hasScoreboardTag(it, ignoreTags) }

fun Entity.addScoreboardTags(vararg tags: String) {
    scoreboardTags += tags
}

fun Entity.removeScoreboardTags(vararg tags: String) {
    scoreboardTags -= tags.toSet()
}