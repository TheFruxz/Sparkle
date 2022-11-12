package de.fruxz.sparkle.framework.visual.message

import de.fruxz.sparkle.framework.effect.sound.SoundEffect
import de.fruxz.sparkle.framework.effect.sound.SoundLibrary
import de.fruxz.sparkle.server.SparkleData
import de.fruxz.stacked.extension.asStyledComponent
import net.kyori.adventure.text.ComponentLike

interface TransmissionAppearance {

	val name: String

	val promptSound: SoundEffect

	val prefix: () -> ComponentLike

	companion object {

		val GENERAL = object : TransmissionAppearance {
			override val name = "GENERAL"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_GENERAL
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.general"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}

		val PROCESS = object : TransmissionAppearance {
			override val name = "PROCESS"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_PROCESS
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.process"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}

		val FAIL = object : TransmissionAppearance {
			override val name = "FAIL"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_FAIL
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.fail"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}

		val ERROR = object : TransmissionAppearance {
			override val name = "ERROR"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_ERROR
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.error"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}

		val LEVEL = object : TransmissionAppearance {
			override val name = "LEVEL"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_LEVEL
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.level"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}

		val WARNING = object : TransmissionAppearance {
			override val name = "WARNING"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_WARNING
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.warning"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}

		val ATTENTION = object : TransmissionAppearance {
			override val name = "ATTENTION"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_ATTENTION
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.attention"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}

		val PAYMENT = object : TransmissionAppearance {
			override val name = "PAYMENT"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_PAYMENT
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.payment"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}
		
		val APPLIED = object : TransmissionAppearance {
			override val name = "APPLIED"
			override val promptSound: SoundEffect = SoundLibrary.NOTIFICATION_APPLIED
			override val prefix: () -> ComponentLike = { (SparkleData.systemConfig.prefix["prefix.applied"] ?: "<dark_gray>⏵ ").asStyledComponent }
		}

		/**
		 * This value contains every [TransmissionAppearance] provided by Sparkle by default.
		 * @author Fruxz
		 * @since 1.0
		 */
		val values = arrayOf(GENERAL, PROCESS, FAIL, ERROR, LEVEL, WARNING, ATTENTION, PAYMENT, APPLIED)

	}

}