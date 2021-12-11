package de.jet.jvm.extension.app

import de.jet.jvm.application.configuration.JetApp
import de.jet.jvm.application.runtime.JetAppRuntime
import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version

fun runApp(appName: String, appVersion: Version = 1.0.version, appInstance: JetApp = JetApp(appName, appVersion), runtime: JetAppRuntime.() -> Unit) {
	with(appInstance) { JetAppRuntime(this.identity, this.version) }.apply {
		init()
	}.apply(runtime)
}