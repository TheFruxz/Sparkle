package de.jet.paper.runtime.sandbox

import de.jet.paper.extension.interchange.InterchangeExecutor

data class SandBoxInteraction internal constructor(
    val sandBox: SandBox,
    val executor: InterchangeExecutor,
    val parameters: List<String>,
)