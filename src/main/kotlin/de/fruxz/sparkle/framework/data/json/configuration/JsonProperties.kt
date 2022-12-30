package de.fruxz.sparkle.framework.data.json.configuration

import de.fruxz.ascend.annotation.LanguageFeature
import de.fruxz.ascend.tool.delegate.JsonProperty
import de.fruxz.ascend.tool.delegate.property
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.server.SparkleApp
import java.nio.file.Path
import kotlin.io.path.div

@LanguageFeature
fun <T : Any> property(
	app: SparkleApp,
	key: String,
	file: Path = SparklePath.appPath(app) / "configuration.json",
	defaultValue: () -> T
): JsonProperty<T> = property(file, key, defaultValue)

@LanguageFeature
fun <T : Any> property(
	component: Component,
	key: String,
	file: Path = SparklePath.componentPath(component) / "configuration.json",
	defaultValue: () -> T,
): JsonProperty<T> = property(file, key, defaultValue)
