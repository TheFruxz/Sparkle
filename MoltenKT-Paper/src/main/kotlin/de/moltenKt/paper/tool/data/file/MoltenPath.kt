package de.moltenKt.paper.tool.data.file

import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.tool.smart.KeyedIdentifiable
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