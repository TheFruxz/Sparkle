package de.fruxz.sparkle.tool.data.file

import de.fruxz.sparkle.extension.system
import de.fruxz.sparkle.structure.app.App
import de.fruxz.sparkle.structure.component.Component
import de.fruxz.sparkle.tool.smart.KeyedIdentifiable
import kotlin.io.path.Path
import kotlin.io.path.div

object MoltenPath {

    private val homePath = Path("MoltenApps")

    fun appPath(app: KeyedIdentifiable<out App>) =
        homePath / "main@${app.key().value()}"

    fun rootPath() =
        appPath(system)

    fun componentPath(component: KeyedIdentifiable<out Component>) =
        homePath / with(component.key()) {
            assert(!value().equals("main", true)) { "Component is not allowed to be called 'main' and also use the file system at the same time!" }

            "${value()}@${namespace()}"
        }

}