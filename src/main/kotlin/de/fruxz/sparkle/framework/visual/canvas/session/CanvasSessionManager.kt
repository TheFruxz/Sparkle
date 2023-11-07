package de.fruxz.sparkle.framework.visual.canvas.session

import dev.fruxz.ascend.tool.smart.identification.Identity
import dev.fruxz.ascend.tool.time.calendar.Calendar
import de.fruxz.sparkle.framework.visual.canvas.Canvas
import de.fruxz.sparkle.server.SparkleCache
import net.kyori.adventure.key.Key
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player

/**
 * This class manages the sessions of the players,
 * using canvas based UI's.
 * @author Fruxz
 * @since 1.0
 */
object CanvasSessionManager {

	/**
	 * This function returns every [HumanEntity] viewing
	 * a canvas, that is identified with the given [queryCanvas]
	 * key ([Key]).
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getViewersOfCanvas(queryCanvas: Identity<Canvas>) =
		getSessions(queryCanvas).map { it.key }

	/**
	 * This function returns every canvas session, viewing a
	 * canvas, that is identified with the given [queryCanvas] key ([Key]).
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getSessions(queryCanvas: Identity<Canvas>): Set<Map.Entry<Player, CanvasSession>> =
		SparkleCache.canvasSessions.filter { it.value.canvas.identityObject == queryCanvas }.entries

	/**
	 * This function returns every canvas session, viewing a
	 * canvas, that is identified with the given [queryCanvas] key ([Key]).
	 * And also viewed by the given [sessionHost] entity.
	 * (bool && bool)
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getSession(sessionHost: Player, queryCanvas: Identity<Canvas>): CanvasSession? =
		getSession(sessionHost)?.takeIf { it.canvas.identityObject == queryCanvas }

	/**
	 * This function returns the [Canvas] of the given [sessionHost] entity,
	 * or null if the entity is not officially viewing a canvas.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getSession(sessionHost: Player): CanvasSession? =
		SparkleCache.canvasSessions[sessionHost]

	/**
	 * This function returns, if the given [sessionHost] entity is
	 * officially viewing a canvas.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun hasSession(sessionHost: Player): Boolean = getSession(sessionHost) != null

	/**
	 * This function removes the [CanvasSession] of the given [sessionHost] entity.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun removeSession(sessionHost: Player) {
		SparkleCache.canvasSessions -= sessionHost
	}

	/**
	 * This function adds the [CanvasSession] of the given [sessionHost] entity.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun putSession(sessionHost: Player, canvas: Canvas, parameters: Map<Key, Any> = emptyMap()) {
		SparkleCache.canvasSessions += sessionHost to CanvasSession(
			canvas = canvas,
			parameters = parameters,
			created = Calendar.now()
		)
	}

	data class CanvasSession(
		val canvas: Canvas,
		val parameters: Map<Key, Any>,
		val created: Calendar = Calendar.now(),
	)

}