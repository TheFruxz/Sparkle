package de.jet.library.runtime.sandbox

import de.jet.library.structure.app.App
import de.jet.library.tool.smart.Identifiable

data class SandBox(
    val sandBoxIdentity: String,
    val sandBoxVendor: App,
    val sandBoxAction: SandBoxInteraction.() -> Unit,
) : Identifiable<SandBox> {
    override val identity = sandBoxIdentity
}
