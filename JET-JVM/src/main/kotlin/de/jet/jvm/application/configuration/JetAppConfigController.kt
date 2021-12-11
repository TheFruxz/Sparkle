package de.jet.jvm.application.configuration

import de.jet.jvm.extension.data.fromJson
import de.jet.jvm.extension.data.toJson
import java.io.File


object JetAppConfigController {

	val jetConfig = File("jet.apps.json")

	private lateinit var configState: JetAppConfiguration

	fun get(): JetAppConfiguration {
		return if (JetAppConfigController::configState.isInitialized) {
			configState
		} else {
			if (jetConfig.exists()) {
				configState = jetConfig.readText().fromJson()
				configState
			} else {
				set()
				get()
			}
		}
	}

	fun set(content: JetAppConfiguration = JetAppConfiguration(apps = emptyList())) {
		jetConfig.writeText(content.toJson())
	}

	fun addApp(appModule: JetAppConfigModule) {
		set(get().let {
			return@let it.copy(apps = (it.apps + appModule))
		})
	}

	fun getApp(app: JetApp): JetAppConfigModule? {
		return get().apps.firstOrNull { it.identity == app.identity }
	}

}