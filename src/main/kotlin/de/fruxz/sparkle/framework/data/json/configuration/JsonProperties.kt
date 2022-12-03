package de.fruxz.sparkle.framework.data.json.configuration

import de.fruxz.ascend.extension.div
import de.fruxz.ascend.tool.delegate.jsonProperty
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.server.SparkleApp
import java.nio.file.Path

fun <T : Any> property(
	app: SparkleApp,
	key: String,
	file: Path = SparklePath.appPath(app) / "configuration.json",
	defaultValue: () -> T
) = jsonProperty(file, key, defaultValue)

fun <T : Any> property(
	component: Component,
	key: String,
	file: Path = SparklePath.componentPath(component) / "configuration.json",
	defaultValue: () -> T,
) = jsonProperty(file, key, defaultValue)
