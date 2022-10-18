package de.fruxz.sparkle.framework.infrastructure.app.update

import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater.UpdateState.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.URL

fun interface AppUpdater {

	suspend fun getUpdate(app: App): UpdateCheckResult

	companion object {

		fun none() = AppUpdater { UpdateCheckResult(UP_TO_DATE) }

		fun github(slug: String, fileNameFilter: (String) -> Boolean = { true }) = AppUpdater {
			val currentRelease = it.httpClient.get("https://api.github.com/repos/$slug/releases/latest")
			val response = currentRelease.body<JsonObject>()
			val remoteTag = response["tag_name"]?.jsonPrimitive?.content ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to get tag") }

			if (remoteTag != it.description.version) {
				val allFiles = response["assets"]?.jsonArray ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to get assets") }

				allFiles
					.map { it.jsonObject }
					.firstOrNull { fileNameFilter(it["name"]?.jsonPrimitive?.content ?: "") }
					?.let {
						return@AppUpdater UpdateCheckResult(
							UPDATE_AVAILABLE,
							it["browser_download_url"]?.jsonPrimitive?.content?.let { URL(it) } ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to get download url") }
						)
					} ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to resolve matching file") }

			} else
				return@AppUpdater UpdateCheckResult(UP_TO_DATE)


		}
		fun modrinth(projectId: String) = AppUpdater {
			val currentRelease = it.httpClient.get("https://api.modrinth.com/v2/project/$projectId/version")
			val response = currentRelease.body<JsonArray>()
			val remoteVersion = response.firstOrNull()?.jsonObject ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to get versions") }
			val remoteTag = remoteVersion["version_number"]?.jsonPrimitive?.content ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to get tag") }

			if (remoteTag != it.description.version) {
				val allFiles = remoteVersion["files"]?.jsonArray ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to get assets") }

				(allFiles.firstOrNull { it.jsonObject["primary"]?.jsonPrimitive?.boolean == true } ?: allFiles.firstOrNull())
					?.let {
						return@AppUpdater UpdateCheckResult(
							UPDATE_AVAILABLE,
							it.jsonObject["url"]?.jsonPrimitive?.content?.let { URL(it) } ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to get download url") }
						)
					} ?: return@AppUpdater UpdateCheckResult(FAILED).also { debugLog("Failed to resolve matching file") }

			} else
				return@AppUpdater UpdateCheckResult(UP_TO_DATE)

		}

		fun custom(process: (App) -> UpdateCheckResult) = AppUpdater(process)

	}

	data class UpdateCheckResult(
		val type: UpdateState,
		val currentJar: URL? = null,
	)

	enum class UpdateState {
		/**
		 * This version is the current version
		 */
		UP_TO_DATE,

		/**
		 * This version is an old version
		 */
		UPDATE_AVAILABLE,

		/**
		 * Update state is not retrievable
		 */
		FAILED;
	}

}