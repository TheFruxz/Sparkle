package de.moltenKt.core.extension.app

import de.moltenKt.core.application.configuration.MoltenCoreApp
import de.moltenKt.core.application.runtime.MoltenCoreAppRuntime
import de.moltenKt.core.application.tag.Version
import de.moltenKt.core.application.tag.version

/**
 * This function creates and runs a new application run
 * @see MoltenCoreApp
 * @param appName is the name of the running application
 * @param appVersion is the version of the running application
 * @param appInstance is the [MoltenCoreApp] on which the [MoltenCoreAppRuntime] is based
 * @param runtime is the process, which get [apply]'d after the initialization of the [MoltenCoreAppRuntime]
 * @author Fruxz
 * @since 1.0
 */
fun runApp(appName: String, appVersion: Version = 1.0.version, appInstance: de.moltenKt.core.application.configuration.MoltenCoreApp = de.moltenKt.core.application.configuration.MoltenCoreApp(
	appName,
	appVersion
), runtime: MoltenCoreAppRuntime.() -> Unit) {
	with(appInstance) { MoltenCoreAppRuntime(this.identity, this.version) }.apply {
		init()
	}.apply(runtime)
}