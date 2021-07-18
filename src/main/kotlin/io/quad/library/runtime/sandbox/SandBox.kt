package io.quad.library.runtime.sandbox

import io.quad.library.structure.app.App

data class SandBox(
    val sandBoxIdentity: String,
    val sandBoxVendor: App,
    val sandBoxAction: SandBoxInteraction.() -> Unit,
)
