package de.moltenKt.paper.tool.data.file

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.component.Component
import kotlin.io.path.Path
import kotlin.io.path.div

object MoltenFileSystem {

    internal val homePath = Path("MoltenApps")

    fun appPath(app: Identifiable<App>) =
        homePath / "@${app.identity}"

    fun rootPath() =
        appPath(system)

    fun componentPath(component: Identifiable<out Component>) =
        homePath / with(component.identity.split(":")) {
            "${this[1]}@${this[0]}"
        }

}