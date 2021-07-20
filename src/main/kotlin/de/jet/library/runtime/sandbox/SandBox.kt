package de.jet.library.runtime.sandbox

import de.jet.library.structure.app.App

data class SandBox(
    val sandBoxIdentity: String,
    val sandBoxVendor: App,
    val sandBoxAction: SandBoxInteraction.() -> Unit,
)
