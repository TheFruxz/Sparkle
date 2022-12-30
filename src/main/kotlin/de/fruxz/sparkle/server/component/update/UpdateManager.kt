package de.fruxz.sparkle.server.component.update

import de.fruxz.ascend.extension.createFileAndDirectories
import de.fruxz.ascend.extension.network.downloadTo
import de.fruxz.sparkle.framework.attachment.Logging
import de.fruxz.sparkle.framework.extension.coroutines.asAsync
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.app.update.AppUpdater.UpdateState.UPDATE_AVAILABLE
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.server.component.update.UpdateManager.UpdateResult.FAILED
import kotlinx.coroutines.Job
import org.bukkit.Bukkit
import kotlin.io.path.div

object UpdateManager : Logging {

	override val vendor = sparkle
	override val sectionLabel = "Sparkle-Updates"

	suspend fun getUpdate(app: App) = try {
		app.updater?.getUpdate(app)
	} catch (e: AbstractMethodError) {
		null
	}

	suspend fun performUpdate(app: App): UpdateResult {
		sectionLog.info("Initializing update for app '${app.key.asString()}'...")

		return asAsync {

			try {

				sectionLog.info("Async update now checking for updates...")

				val update = getUpdate(app)
				val url = update?.currentJar

				return@asAsync if (update != null && url != null) {
					sectionLog.info("Update found for app '${app.key.asString()}'! Starting preparation...")

					val fileName = url.path.split("/").last()
					val destination = Bukkit.getPluginsFolder().toPath() / "update" / fileName

					destination.createFileAndDirectories()

					sectionLog.info("Foundation for update established! Starting download...")

					url downloadTo destination // download the url content to the destination

					sectionLog.info("Download & installation succeeded, restart now pending!")

					UpdateResult.SUCCESSFUL

				} else {
					sectionLog.info("No update found for app '${app.key.asString()}', stopping operation!")
					UpdateResult.UP_TO_DATE
				}

			} catch (e: Exception) {
				sectionLog.warning("Updater failed at updating '${app.key}' with exception:")
				e.printStackTrace()
				return@asAsync FAILED
			}

		}.also { UpdateComponent.updateProcesses += app to it }.await()

	}

	val runningUpdates: Map<App, Job>
		get() = UpdateComponent.updateProcesses.filter { !it.value.isCompleted }

	val finishedUpdates: Map<App, Job>
		get() = UpdateComponent.updateProcesses.filter { it.value.isCompleted }

	suspend fun availableUpdates() = SparkleCache.registeredApps.filter { it.updater?.getUpdate(it)?.type == UPDATE_AVAILABLE }

	enum class UpdateResult {
		UP_TO_DATE, SUCCESSFUL, FAILED;
	}

}