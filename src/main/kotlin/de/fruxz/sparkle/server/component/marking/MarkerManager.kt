package de.fruxz.sparkle.server.component.marking

import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.json.readJson
import dev.fruxz.ascend.json.writeJson
import de.fruxz.sparkle.framework.extension.coroutines.doSync
import de.fruxz.sparkle.framework.extension.entity.marker
import de.fruxz.sparkle.framework.extension.entity.markerOrNull
import org.bukkit.entity.Player
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

object MarkerManager {

	fun loadMarking(player: Player, name: String) = doSync {
		val file = (MarkerComponent.saves / (name.protect().removeSuffix(".selection.json") + ".selection.json"))

		if (file.exists() && !file.isDirectory()) {
			player.marker = file.readJson()
		} else error("File '$file' does not exist or is a directory")

	}

	fun saveMarking(player: Player, name: String = "new") = doSync {
		val file = (MarkerComponent.saves / (name.protect().removeSuffix(".selection.json") + ".selection.json"))

		with(file) {
			createFileAndDirectories()
			writeJson(player.markerOrNull ?: error("Player has no marker"))
		}

	}

	private fun String.protect() =
		replace("..", "")
			.replace("/", "")
			.replace("\\", "")
			.replace(":", "")
			.replace("*", "")
			.replace("?", "")
			.replace("\"", "")
			.replace("<", "")
			.replace(">", "")
			.replace("|", "")
			.replace("~", "")

}