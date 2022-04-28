package de.moltenKt.paper.tool.data.file

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.component.Component
import kotlin.io.path.Path
import kotlin.io.path.div

object MoltenFileSystem {

    private val homePath = Path("MoltenApps")

    fun appPath(app: Identifiable<App>) =
        homePath / "main@${app.identity}"

    fun rootPath() =
        appPath(system)

    fun componentPath(component: Identifiable<out Component>) =
        homePath / with(component.identity.split(":")) {
            assert(!this[1].equals("main", true)) { "Component is not allowed to be called 'main' and also use the file system at the same time!" }

            "${this[1]}@${this[0]}"
        }

}