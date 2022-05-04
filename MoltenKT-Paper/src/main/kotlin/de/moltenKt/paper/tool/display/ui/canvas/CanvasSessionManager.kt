package de.moltenKt.paper.tool.display.ui.canvas

import de.moltenKt.core.tool.timing.calendar.Calendar
import de.moltenKt.paper.app.MoltenCache
import net.kyori.adventure.key.Key
import org.bukkit.entity.HumanEntity

object CanvasSessionManager {

	fun getSessions(queryCanvas: Key): Set<Map.Entry<HumanEntity, CanvasSession>> =
		MoltenCache.canvasSessions.filter { it.value.canvas == queryCanvas }.entries

	fun getSession(sessionHost: HumanEntity, queryCanvas: Key): CanvasSession? =
		getSession(sessionHost)?.takeIf { it.canvas == queryCanvas }

	fun getSession(sessionHost: HumanEntity): CanvasSession? =
		MoltenCache.canvasSessions[sessionHost]

	fun hasSession(sessionHost: HumanEntity): Boolean = getSession(sessionHost) != null

	fun removeSession(sessionHost: HumanEntity) {
		MoltenCache.canvasSessions -= sessionHost
	}

	fun putSession(sessionHost: HumanEntity, canvas: Key, parameters: Map<Key, Any> = emptyMap()) {
		MoltenCache.canvasSessions += sessionHost to CanvasSession(
			canvas = canvas,
			parameters = parameters,
			created = Calendar.now()
		)
	}

	data class CanvasSession(
		val canvas: Key,
		val parameters: Map<Key, Any>,
		val created: Calendar = Calendar.now(),
	)

}