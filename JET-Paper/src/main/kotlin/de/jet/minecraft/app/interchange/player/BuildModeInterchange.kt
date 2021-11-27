package de.jet.minecraft.app.interchange.player

import de.jet.jvm.extension.collection.last
import de.jet.jvm.extension.collection.replaceVariables
import de.jet.jvm.extension.switchResult
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.lang
import de.jet.minecraft.extension.paper.buildMode
import de.jet.minecraft.extension.paper.getOfflinePlayer
import de.jet.minecraft.extension.paper.onlinePlayers
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.*
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.minecraft.structure.command.live.InterchangeAccess
import de.jet.minecraft.tool.display.message.Transmission.Level.*
import org.bukkit.OfflinePlayer
import java.util.*

class BuildModeInterchange(vendor: App = system) :
	Interchange(vendor, "buildmode", requiresAuthorization = true, completion = buildCompletion {
		next(setOf("enable", "disable", "toggle", "info", "list", "enableAll", "disableAll")) isRequired true mustMatchOutput true
		next(CompletionVariable.PLAYER_NAME) isRequired false mustMatchOutput true
	}) {

	override val execution: InterchangeAccess.() -> InterchangeResult = out@{

		fun enable(target: OfflinePlayer) {
			if (!target.buildMode) {
				target.buildMode = true
				lang("interchange.internal.buildmode.enable")
					.replaceVariables("player" to target.name)
					.notification(APPLIED, executor).display()
			} else
				lang("interchange.internal.buildmode.stay")
					.notification(FAIL, executor).display()
		}

		fun disable(target: OfflinePlayer) {
			if (target.buildMode) {
				target.buildMode = false
				lang("interchange.internal.buildmode.disable")
					.replaceVariables("player" to target.name)
					.notification(APPLIED, executor).display()
			} else
				lang("interchange.internal.buildmode.stay")
					.notification(FAIL, executor).display()
		}

		when (parameters.size) {

			1 -> {

				when (parameters.last.lowercase()) {

					"list" -> {
						val active = JetCache.buildModePlayers.map { getOfflinePlayer(UUID.fromString(it.identity)) }

						if (active.isNotEmpty()) {

							buildString {
								appendLine(lang("interchange.internal.buildmode.list.header"))

								active.forEach { player ->
									appendLine(
										lang("interchange.internal.buildmode.list.line")
											.replaceVariables(
												"player" to player.name,
												"statusColor" to player.isOnline.switchResult("§a§l", "§7§l")
											)
									)
								}

							}
								.notification(INFO, executor).display()

						} else
							lang("interchange.internal.buildmode.list.empty")
								.notification(FAIL, executor).display()

					}

					"enableAll" -> {

						onlinePlayers.forEach(::enable)

					}

					"disableAll" -> {

						onlinePlayers.forEach(::disable)

					}

				}

			}

			2 -> {
				val target = getOfflinePlayer(parameters.last)

				if (target.hasPlayedBefore()) {

					when (parameters.first().lowercase()) {

						"enable" -> {
							enable(target)
						}

						"disable" -> {
							disable(target)
						}

						"toggle" -> {
							target.buildMode = !target.buildMode

							if (target.buildMode) {
								lang("interchange.internal.buildmode.enable")
									.replaceVariables("player" to target.name)
									.notification(APPLIED, executor).display()
							} else {
								lang("interchange.internal.buildmode.disable")
									.replaceVariables("player" to target.name)
									.notification(APPLIED, executor).display()
							}
						}

						"info" -> {
							lang("interchange.internal.buildmode.info")
								.replaceVariables(
									"state" to target.buildMode,
									"player" to target.name,
								)
								.notification(INFO, executor).display()
						}

						else -> return@out WRONG_USAGE

					}

				} else
					lang("interchange.internal.buildmode.neverOnline")
						.replaceVariables("player" to parameters.last)
						.notification(FAIL, executor).display()

			}

			else -> return@out WRONG_USAGE

		}

		return@out SUCCESS
	}

}