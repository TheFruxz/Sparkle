package de.moltenKt.paper.tool.display.canvas

import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenCache
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
	fun getViewersInSession(queryCanvas: Key) =
		getSessions(queryCanvas).map { it.key }

	/**
	 * This function returns every canvas session, viewing a
	 * canvas, that is identified with the given [queryCanvas] key ([Key]).
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getSessions(queryCanvas: Key): Set<Map.Entry<Player, CanvasSession>> =
		MoltenCache.canvasSessions.filter { it.value.canvas == queryCanvas }.entries

	/**
	 * This function returns every canvas session, viewing a
	 * canvas, that is identified with the given [queryCanvas] key ([Key]).
	 * And also viewed by the given [sessionHost] entity.
	 * (bool && bool)
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getSession(sessionHost: Player, queryCanvas: Key): CanvasSession? =
		getSession(sessionHost)?.takeIf { it.canvas == queryCanvas }

	/**
	 * This function returns the [CanvasSession] of the given [sessionHost] entity.
	 * Or null if the entity is not officially viewing a canvas.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getSession(sessionHost: Player): CanvasSession? =
		MoltenCache.canvasSessions[sessionHost]

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
		MoltenCache.canvasSessions -= sessionHost
	}

	/**
	 * This function adds the [CanvasSession] of the given [sessionHost] entity.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun putSession(sessionHost: Player, canvas: Key, parameters: Map<Key, Any> = emptyMap()) {
		MoltenCache.canvasSessions += sessionHost to CanvasSession(
			canvas = canvas,
			parameters = parameters,
			created = Calendar.now()
		)
	}

	/**
	 * This data class represents a canvas session.
	 * @author Fruxz
	 * @since 1.0
	 */
	data class CanvasSession(
		val canvas: Key,
		val parameters: Map<Key, Any>,
		val created: Calendar = Calendar.now(),
	)

}