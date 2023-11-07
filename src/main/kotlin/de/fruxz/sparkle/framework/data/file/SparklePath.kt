package de.fruxz.sparkle.framework.data.file

import dev.fruxz.ascend.extension.getHomePath
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.component.Component
import kotlin.io.path.div

object SparklePath {

    private val homePath = getHomePath() / "SparkleApps"

    fun appPath(app: KeyedIdentifiable<out App>) =
        homePath / "main@${app.key().value()}"

    fun rootPath() =
        appPath(sparkle)

    fun componentPath(component: KeyedIdentifiable<out Component>) =
        homePath / with(component.key()) {
            assert(!value().equals("main", true)) { "Component is not allowed to be called 'main' and also use the file system at the same time!" }

            "${value()}@${namespace()}"
        }

}