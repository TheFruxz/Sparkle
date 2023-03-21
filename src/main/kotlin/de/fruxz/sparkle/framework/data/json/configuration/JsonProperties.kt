package de.fruxz.sparkle.framework.data.json.configuration

import de.fruxz.ascend.annotation.LanguageFeature
import de.fruxz.ascend.json.JsonProperty
import de.fruxz.ascend.json.property

import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.infrastructure.component.Component
import de.fruxz.sparkle.server.SparkleApp
import java.nio.file.Path
import kotlin.io.path.div

@LanguageFeature
inline fun <reified T : Any> property(
	app: SparkleApp,
	key: String,
	file: Path = SparklePath.appPath(app) / "configuration.json",
	noinline defaultValue: () -> T
): JsonProperty<T> = property(file, key, defaultValue = defaultValue)

@LanguageFeature
inline fun <reified T : Any> property(
	component: Component,
	key: String,
	file: Path = SparklePath.componentPath(component) / "configuration.json",
	noinline defaultValue: () -> T,
): JsonProperty<T> = property(file, key, defaultValue = defaultValue)
