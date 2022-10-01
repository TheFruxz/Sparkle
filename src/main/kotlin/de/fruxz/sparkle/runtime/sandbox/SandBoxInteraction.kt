package de.fruxz.sparkle.runtime.sandbox

import de.fruxz.sparkle.extension.interchange.InterchangeExecutor

data class SandBoxInteraction internal constructor(
    val sandBox: SandBox,
    val executor: InterchangeExecutor,
    val parameters: List<String>,
)