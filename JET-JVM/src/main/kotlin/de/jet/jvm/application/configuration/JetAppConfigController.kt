package de.jet.jvm.application.configuration

import de.jet.jvm.extension.data.fromJson
import de.jet.jvm.extension.data.toJson
import java.io.File

/**
 * This class is responsible for the configuration of applications.
 * It manages, stores and loads the configuration of them.
 * @author Fruxz
 * @since 1.0
 */
object JetAppConfigController {

	/**
	 * The general configuration file of the jet management system itself
	 * @author Fruxz
	 * @since 1.0
	 */
	val jetConfig = File("jet.apps.json")

	/**
	 * The current configuration state of the application
	 * @author Fruxz
	 * @since 1.0
	 */
	private lateinit var configState: JetAppConfiguration // TODO multi application support

	/**
	 * Gets the current or the loaded configuration state of the application
	 * @return the current or saved state of the config
	 * @author Fruxz
	 * @since 1.0
	 */
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

	/**
	 * Changes the current configuration state of the application to [content]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun set(content: JetAppConfiguration = JetAppConfiguration(apps = emptyList())) {
		configState = content
		jetConfig.writeText(content.toJson())
	}

	/**
	 * This function adds an app to the configuration with the module
	 * values, which often use the default values.
	 * @param appModule the module of the app, to get saved
	 * @author Fruxz
	 * @since 1.0
	 */
	fun addApp(appModule: JetAppConfigModule) {
		val get = get()
		if (get.apps.none { it.identity == appModule.identity }) {
			set(get.let {
				return@let it.copy(apps = (it.apps + appModule))
			})
		}
	}

	/**
	 * This functio returns the [JetAppConfigModule] of the searched
	 * jet app [app] or returns null, if the module is not stored.
	 * The module contains, most importantly, the configured path
	 * of the files, which the [app] uses.
	 * @param app is the searched app
	 * @return the found module or null
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getApp(app: JetApp): JetAppConfigModule? {
		return get().apps.firstOrNull { it.identity == app.identity }
	}

}