package de.fruxz.sparkle.framework.sandbox

import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor

data class SandBoxInteraction internal constructor(
	val sandBox: SandBox,
	val executor: InterchangeExecutor,
	val parameters: List<String>,
)