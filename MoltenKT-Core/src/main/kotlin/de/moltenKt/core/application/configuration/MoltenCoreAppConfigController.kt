package de.moltenKt.core.application.configuration

import de.moltenKt.core.extension.data.fromJson
import de.moltenKt.core.extension.data.readJson
import de.moltenKt.core.extension.data.toJson
import de.moltenKt.core.extension.data.writeJson
import java.io.File

/**
 * This class is responsible for the configuration of applications.
 * It manages, stores and loads the configuration of them.
 * @author Fruxz
 * @since 1.0
 */
object MoltenCoreAppConfigController {

	/**
	 * The general configuration file of the MoltenKT management system itself
	 * @author Fruxz
	 * @since 1.0
	 */
	private val moltenConfig = File("molten.apps.json")

	/**
	 * The current configuration state of the application
	 * @author Fruxz
	 * @since 1.0
	 */
	private lateinit var configState: MoltenCoreAppConfiguration // TODO multi application support

	/**
	 * Gets the current or the loaded configuration state of the application
	 * @return the current or saved state of the config
	 * @author Fruxz
	 * @since 1.0
	 */
	fun get(): MoltenCoreAppConfiguration {
		return if (MoltenCoreAppConfigController::configState.isInitialized) {
			configState
		} else {
			if (moltenConfig.exists()) {
				configState = moltenConfig.readJson()
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
	fun set(content: MoltenCoreAppConfiguration = MoltenCoreAppConfiguration(
		apps = emptyList()
	)
	) {
		configState = content
		moltenConfig.writeJson(content)
	}

	/**
	 * This function adds an app to the configuration with the module
	 * values, which often use the default values.
	 * @param appModule the module of the app, to get saved
	 * @author Fruxz
	 * @since 1.0
	 */
	fun addApp(appModule: MoltenCoreAppConfigModule) {
		val get = get()
		if (get.apps.none { it.identity == appModule.identity }) {
			set(get.let {
				return@let it.copy(apps = (it.apps + appModule))
			})
		}
	}

	/**
	 * This function returns the [MoltenCoreAppConfigModule] of the searched
	 * MoltenKT app [app] or returns null, if the module is not stored.
	 * The module contains, most importantly, the configured path
	 * of the files, which the [app] uses.
	 * @param app is the searched app
	 * @return the found module or null
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getApp(app: MoltenCoreApp): MoltenCoreAppConfigModule? {
		return get().apps.firstOrNull { it.identity == app.identity }
	}

}