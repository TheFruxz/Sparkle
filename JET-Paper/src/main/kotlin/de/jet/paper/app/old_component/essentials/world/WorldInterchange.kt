package de.jet.paper.app.old_component.essentials.world

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.positioning.Address.Companion.address
import de.jet.paper.app.old_component.essentials.world.WorldInterchange.WorldPanelViewProperties.ViewType.*
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer.FileSystem
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer.RenderFolder
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer.RenderObject
import de.jet.paper.app.old_component.essentials.world.tree.WorldRenderer.RenderWorld
import de.jet.paper.extension.display.ui.buildPanel
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.display.ui.skull
import de.jet.paper.extension.external.texturedSkull
import de.jet.paper.extension.paper.displayString
import de.jet.paper.extension.paper.getWorld
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.execution
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.ui.panel.PanelFlag.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World.Environment.NETHER
import org.bukkit.WorldCreator
import org.bukkit.WorldType.AMPLIFIED
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType.*

class WorldInterchange : Interchange(
	label = "world",
	protectedAccess = true
) {

	data class WorldPanelViewProperties(
		val path: String = "/",
		val viewType: ViewType = ALL,
		val page: Int = 1,
		val searchKey: String? = null,
		val searchFilters: List<Filter> = emptyList(),
	) {

		enum class ViewType {
			ALL, DIRECTORIES, WORLDS;
		}

		enum class Filter {
			NAME, LABEL, VISITOR;
		}

	}

	private fun renderItem(view: WorldPanelViewProperties, renderObject: RenderObject): Item {
		return when (renderObject) {
			is RenderWorld -> texturedSkull(32442).apply {
				val world = Bukkit.getWorlds().firstOrNull { it.name == renderObject.identity.lowercase() }

				identity = renderObject.identity

				if (world != null) {
					label = "§6${renderObject.displayName}"
					lore = buildString {
						appendLine("§7Identity: §e${renderObject.identity}")
						appendLine("§7Path: §e${renderObject.addressObject.addressObject}")
						appendLine()
						appendLine("§7Status: §aRUNNING")
						appendLine("§7Labels: §6${renderObject.renderLabels()}")
						appendLine("§7Archive: §6${renderObject.renderArchiveStatus()}")
						appendLine("§7Now playing: §6${world.playerCount} Player(s)")
						appendLine("§7Spawn-Point: §6${world.spawnLocation.displayString()}")
						appendLine("§7Difficulty: §6${world.difficulty.name}")
						appendLine("§7Visitors: §6${renderObject.visitors.joinToString()}")
						appendLine("")
						appendLine()
						appendLine("§a§lLEFT-CLICK§7 - teleport to this world")
						appendLine("§a§lMIDDLE-CLICK§7 - unload this world")
						appendLine("§a§lRIGHT-CLICK§7 - edit this world")
					}

					putClickAction(async = false) {
						val identity =
							Identifiable.custom<RenderWorld>(lore.lines().first().removePrefix("§7Identity: §e"))
						//val address = address<RenderObject>(lore.lines()[1].removePrefix("§7Path: §e"))

						when (click) {
							LEFT, SHIFT_LEFT -> {
								whoClicked.teleport(getWorld(identity.identity)!!.spawnLocation)
							}
							MIDDLE -> {
								whoClicked.sendMessage("unloading...")
								Bukkit.unloadWorld(identity.identity, true)
								whoClicked.sendMessage("unloaded!")
								displayPanel(whoClicked, WorldPanelViewProperties())
							}
							RIGHT, SHIFT_RIGHT -> {
								whoClicked.sendMessage("edit")
							}
							else -> {}
						}

					}

				} else {
					label = "§7${renderObject.displayName}"
					lore = buildString {
						appendLine("§7Identity: §e${renderObject.identity}")
						appendLine("§7Path: §e${renderObject.addressObject.addressObject}")
						appendLine()
						appendLine("§6Status: §cOFFLINE")
						appendLine("§7Labels: §6${renderObject.renderLabels()}")
						appendLine("§7Archive: §6${renderObject.renderArchiveStatus()}")
						appendLine("§7Visitors: §6${renderObject.visitors.joinToString()}")
						appendLine("")
						appendLine()
						appendLine("§a§lLEFT-CLICK§7 - load this world")
						appendLine("§a§lRIGHT-CLICK§7 - edit this world")
					}

					putClickAction(async = false) {
						val identity =
							Identifiable.custom<RenderWorld>(lore.lines().first().removePrefix("§7Identity: §e"))
						//val address = address<RenderObject>(lore.lines()[1].removePrefix("§7Path: §e"))

						when (click) {
							LEFT, SHIFT_LEFT -> {
								whoClicked.sendMessage("loading...")
								Bukkit.createWorld(WorldCreator.name(identity.identity))
								whoClicked.sendMessage("loaded!")
								displayPanel(whoClicked, WorldPanelViewProperties())
							}
							RIGHT, SHIFT_RIGHT -> {
								whoClicked.sendMessage("edit")
							}
							else -> {}
						}

					}

				}

			}
			is RenderFolder -> (if (renderObject.archived) {
				texturedSkull(4465)
			} else
				texturedSkull(4459)).apply {

				label = "§3Directory: ${renderObject.displayName}"
				identity = renderObject.identity
				lore = buildString {
					appendLine("§7Identity: §e${renderObject.identity}")
					appendLine("§7Path: §e${renderObject.addressObject.addressObject}")
					appendLine()
					appendLine("§7Labels: §6${renderObject.renderLabels()}")
					appendLine("§7Archive: §6${renderObject.renderArchiveStatus()}")
					appendLine()
					appendLine("§a§lLEFT-CLICK§7 - open this directory")
					appendLine("§a§lRIGHT-CLICK§7 - edit this directory")
				}

				putClickAction(async = false) {
					when (click) {
						LEFT, SHIFT_LEFT -> {
							displayPanel(whoClicked, view.copy(path = renderObject.addressString))
						}
						RIGHT, SHIFT_RIGHT -> {
							whoClicked.sendMessage("edit")
						}
						else -> {
							whoClicked.sendMessage("nothing")
						}
					}
				}

			}
			else -> throw NullPointerException()
		}

	}

	val panel = buildPanel(lines = 5) {
		FileSystem.createDirectory(address("/test/"))

		set(38, texturedSkull(9885).apply {
			label = "Create"
		})

		set(42, texturedSkull(46454).apply {
			label = "Import"
		})

		onReceive {
			assert(receiveParameters["view"] != null) { "view-property not exist!" }
			val view = receiveParameters["view"] as WorldPanelViewProperties
			val viewContent = WorldRenderer.renderOverview(view.path).let {
				return@let when (view.viewType) {
					ALL -> it.renderFolders + it.renderWorlds
					DIRECTORIES -> it.renderFolders
					WORLDS -> it.renderWorlds
				}
			}

			editPanel {

				if (view.path != "/")
					set(2, FileSystem.getDirectory(address(view.path))!!.let {
						return@let (if (it.archived) {
							texturedSkull(4465)
						} else
							texturedSkull(4459))
							.apply {

								label = "§3Inside Directory: ${it.displayName}"
								lore = buildString {

									appendLine("§7Identity: §e${it.identity}")
									appendLine("§7Path: §e${it.addressObject.addressObject}")
									appendLine()
									appendLine("§7Labels: §e${it.renderLabels()}")
									appendLine("§7Archive: ${it.renderArchiveStatus()}")
									appendLine()
									appendLine("§a§lLEFT-CLICK§7 - go to parent directory")
									appendLine("§a§lRIGHT-CLICK§7 - edit this directory")

								}

								putClickAction {
									when (click) {
										RIGHT, SHIFT_RIGHT -> whoClicked.sendMessage("edit")
										LEFT, SHIFT_LEFT -> {
											displayPanel(
												whoClicked,
												view.copy(
													path = view.path.removeSurrounding("/").split("/").dropLast(1)
														.joinToString("/", prefix = "/", postfix = "/")
														.replaceFirst("//", "/")
												)
											)
										}
										else -> whoClicked.sendMessage("nothing")
									}
								}

							}
					})

				if (getWorld("testy") == null)
					FileSystem.createWorld(
						"testy",
						NETHER,
						AMPLIFIED,
						RenderWorld("testy", "testy", address("/test/testy"), emptyList(), false, emptyList())
					)

				panelFlags = setOf(NOT_CLICK_ABLE, NOT_MOVE_ABLE, NOT_DRAG_ABLE)

				innerSlots.forEach { innerSlot ->
					val viewContentSlot = (innerSlot + (innerSlots.last * (view.page - 1)))
					val item = viewContent.getOrNull(viewContentSlot)

					if (item != null) {
						placeInner(innerSlot, renderItem(view, item))
					}// else
					// TODO	placeInner(innerSlot, Material.RED_WOOL.item)

				}

				set(18, skull("MHF_ArrowLeft").blankLabel().putClickAction {
					displayPanel(whoClicked, "view" to view.copy(page = view.page - 1))
				})

				set(26, skull("MHF_ArrowRight").blankLabel().putClickAction {
					displayPanel(whoClicked, "view" to view.copy(page = view.page + 1))
				})

				set(6, Material.PAPER.item.apply {
					label = "Seite ${view.page}"
					size = view.page
				})

				set(40, Material.HOPPER.item.apply {
					label = "Filter"
				})

			}

		}

	}

	fun displayPanel(receiver: HumanEntity, vararg parameters: Pair<String, Any>) {
		panel.display(receiver, parameters.toMap())
	}

	fun displayPanel(receiver: HumanEntity, viewProperties: WorldPanelViewProperties) =
		displayPanel(receiver, "view" to viewProperties)

	override val execution = execution {

		displayPanel(executor as Player, WorldPanelViewProperties())

		SUCCESS
	}

}