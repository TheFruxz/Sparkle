package de.fruxz.sparkle.framework.infrastructure.command.live

fun interface InterchangeReaction {

	fun InterchangeAccess<*>.reaction(): Unit

}