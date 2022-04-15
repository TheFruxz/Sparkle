package de.moltenKt.paper.runtime.sandbox

import de.moltenKt.paper.extension.interchange.InterchangeExecutor

data class SandBoxInteraction internal constructor(
    val sandBox: SandBox,
    val executor: InterchangeExecutor,
    val parameters: List<String>,
)