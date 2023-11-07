package de.fruxz.sparkle.server.component.update

import dev.fruxz.ascend.json.readJson
import dev.fruxz.ascend.json.writeJsonIfNotExists
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.infrastructure.component.Component.RunType.AUTOSTART_MUTABLE
import de.fruxz.sparkle.framework.infrastructure.component.SmartComponent
import de.fruxz.sparkle.server.SparkleCache
import kotlinx.serialization.Serializable
import kotlin.io.path.div
import kotlin.time.Duration.Companion.minutes

class UpdateComponent : SmartComponent(AUTOSTART_MUTABLE) {

	override val label = "Updates"

	override suspend fun component() {

		service(UpdateService())
		listener(UpdateNotificationHandler())

	}

	val updateConfiguration by lazy {
		(SparklePath.componentPath(this) / "settings.json").writeJsonIfNotExists(Configuration()).readJson<Configuration>()
	}

	@Serializable
	data class Configuration(
		val updateJoinNotifications: Boolean = true,
		val updateUpdateNotifications: Boolean = true,
		val updateCheckIntervallSeconds: Long = 1.minutes.inWholeSeconds,
	)

	companion object {

		var updateProcesses = SparkleCache.updateProcesses
		var updateStates = SparkleCache.updateStates

	}

}