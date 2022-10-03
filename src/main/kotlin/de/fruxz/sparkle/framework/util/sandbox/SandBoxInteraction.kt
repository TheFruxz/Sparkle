package de.fruxz.sparkle.framework.util.sandbox

import de.fruxz.sparkle.framework.util.extension.interchange.InterchangeExecutor

data class SandBoxInteraction internal constructor(
	val sandBox: SandBox,
	val executor: InterchangeExecutor,
	val parameters: List<String>,
)