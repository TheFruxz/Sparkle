package de.fruxz.sparkle.framework.positioning.world

import de.fruxz.ascend.extension.container.span
import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.extension.math.outOf
import de.fruxz.ascend.extension.math.round
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.ascend.tool.math.Percentage.Companion.percent
import de.fruxz.ascend.tool.time.calendar.Calendar
import de.fruxz.sparkle.framework.effect.particle.ParticleType
import de.fruxz.sparkle.framework.extension.coroutines.doSync
import de.fruxz.sparkle.framework.extension.coroutines.launch
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.effect.offset
import de.fruxz.sparkle.framework.extension.effect.particleOf
import de.fruxz.sparkle.framework.extension.onlinePlayers
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.extension.world.location
import de.fruxz.sparkle.framework.extension.world.positionX
import de.fruxz.sparkle.framework.extension.world.positionY
import de.fruxz.sparkle.framework.extension.world.positionZ
import de.fruxz.stacked.text
import io.ktor.util.*
import kotlinx.coroutines.delay
import net.kyori.adventure.bossbar.BossBar.Color.PURPLE
import net.kyori.adventure.bossbar.BossBar.Color.WHITE
import net.kyori.adventure.bossbar.BossBar.Overlay.PROGRESS
import net.kyori.adventure.bossbar.BossBar.bossBar
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.block.Container
import org.bukkit.entity.Player
import kotlin.time.Duration.Companion.seconds

object EnhancedWorldTools {

	suspend fun analyzeMaterialTypes(chunks: Set<Chunk>, consoleLog: Boolean = true, includeContainerContent: Boolean = true, vararg receivers: Player = onlinePlayers.toTypedArray()) =
		analyzeMaterialTypes(chunks, consoleLog, includeContainerContent) { receivers }

	suspend fun analyzeMaterialTypes(chunks: Set<Chunk>, consoleLog: Boolean = true, includeContainerContent: Boolean = true, spawnScanParticles: Boolean = true, receivers: () -> Array<out Player> = { onlinePlayers.toTypedArray() }): Map<Material, Long> {

		// Global
		val scanId = buildRandomTag()
		val scanParticle = particleOf(ParticleType.END_ROD).count(1).offset(0).extra(.0)
		val progressBar = bossBar(text("Preparing analysis...").color(NamedTextColor.LIGHT_PURPLE), 0F, PURPLE, PROGRESS)
		val blockAmount = chunks.sumOf { (it.positionX.span * it.positionY.span * it.positionZ.span).toLong() }
		var materialAmount = mapOf<Material, Long>()
		var heartBeat = 0L

		// Internal
		var timeTrackerRun = 0
		var lastTrackerTime = Calendar.now()
		var lastTrackerAmount = 0

		"Starting analysis ($scanId) of ${chunks.size} chunks with $blockAmount blocks...".let { startingMessage ->
			if (consoleLog) sparkle.logger.info(startingMessage) else debugLog(startingMessage)
		}

		// Displaying the bar the first time, to display the preparing message
		receivers.invoke().forEach { it.showBossBar(progressBar) }

		launch {

			while (heartBeat < blockAmount) {
				val blocksPerMillisecond = lastTrackerAmount.toDouble() / lastTrackerTime.durationToNow().inWholeMilliseconds
				val displayLabel = "Analysing... ${heartBeat outOf blockAmount}% | ${blocksPerMillisecond.round(2)} blocks/ms"

				lastTrackerAmount = 0
				lastTrackerTime = Calendar.now()
				progressBar.name(text(displayLabel) {
					color(NamedTextColor.GREEN)
				})

				if (timeTrackerRun % 10 == 0) {
					if (consoleLog) sparkle.logger.info("Chunk-Analyzer $scanId: $displayLabel")
					onlinePlayers.forEach { it.showBossBar(progressBar) }
				}

				timeTrackerRun++
				delay(.5.seconds)
			}

			// After finished the whole analyzing process
			delay(10.seconds)
			receivers.invoke().forEach { it.hideBossBar(progressBar) }

		}.printDebugTree()

		chunks.forEach { analyzingChunk ->
			analyzingChunk.positionX.forEach { analyzingX ->
				analyzingChunk.positionY.forEach { analyzingY ->
					analyzingChunk.positionZ.forEach { analyzingZ ->
						try {
							val location = location(analyzingChunk.world, analyzingX, analyzingY, analyzingZ)
							val block = location.world.getBlockAt(location)

							if (spawnScanParticles) scanParticle.location(location).receivers(*receivers.invoke()).spawn()

							lastTrackerAmount++
							heartBeat++

							if (includeContainerContent) {
								tryOrNull { block.state.takeIf { it !is Container } } ?: doSync {
									val iState = block.state
									if (iState is Container) {
										iState.snapshotInventory.let { inventory ->
											inventory.contents.forEach { item ->
												item?.let {
													materialAmount += it.type to (materialAmount.getOrDefault(block.type, 0L) + 1L)
												}
											}
										}
									}
								}
							}

							materialAmount += block.type to (materialAmount.getOrDefault(block.type, 0L) + 1L)

							progressBar.progress((heartBeat outOf blockAmount).percent.decimal.toFloat() % 1F)

						} catch (exception: Exception) {
							sparkle.logger.warning("Chunk-Analyzer $scanId: Error while analyzing block at $analyzingX, $analyzingY, $analyzingZ; Stacktrace following:")
							exception.printStackTrace()
						}
					}
				}
			}
		}

		progressBar.progress(1F)
		progressBar.color(WHITE)
		progressBar.name(text("Analysis finished!") {
			color(NamedTextColor.WHITE)
		})

		return materialAmount.toList().sortedByDescending { it.second }.toMap()
	}

}