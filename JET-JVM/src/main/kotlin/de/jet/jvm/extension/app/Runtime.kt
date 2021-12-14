package de.jet.jvm.extension.app

import de.jet.jvm.application.configuration.JetApp
import de.jet.jvm.application.runtime.JetAppRuntime
import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version

/**
 * This function creates and runs a new application run
 * @see JetApp
 * @param appName is the name of the running application
 * @param appVersion is the version of the running application
 * @param appInstance is the [JetApp] on which the [JetAppRuntime] is based
 * @param runtime is the process, which get [apply]'d after the initialization of the [JetAppRuntime]
 * @author Fruxz
 * @since 1.0
 */
fun runApp(appName: String, appVersion: Version = 1.0.version, appInstance: JetApp = JetApp(appName, appVersion), runtime: JetAppRuntime.() -> Unit) {
	with(appInstance) { JetAppRuntime(this.identity, this.version) }.apply {
		init()
	}.apply(runtime)
}