package de.moltenKt.paper.app.old_component.essentials.world.tree

import de.moltenKt.jvm.extension.container.mutableReplaceWith
import de.moltenKt.jvm.extension.isNotNull
import de.moltenKt.jvm.extension.switchResult
import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.jvm.tool.smart.positioning.Address
import de.moltenKt.jvm.tool.smart.positioning.Address.Companion.address
import de.moltenKt.jvm.tool.smart.positioning.Addressable
import de.moltenKt.paper.app.MoltenData
import de.moltenKt.paper.app.old_component.essentials.world.WorldConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import kotlin.io.path.Path

object WorldRenderer {

	private var pathDepth = -1

	@Serializable
	@SerialName("TreeWorldStructure")
	data class WorldStructure(
		var smashedStructure: List<RenderObject>,
	) {

		private fun printFolderContents(folder: RenderFolder, onlyBase: Boolean = false) {
			pathDepth++

			println(buildString {
				repeat(pathDepth) { append("   ") }
				append("|- [${folder.identity}] ${folder.isRootDirectory.switchResult("(root)", "")}")
			})

			computeFolderContents(folder, this).let { result ->

				result.renderWorlds.forEach { printWorld(it) }
				result.renderFolders.forEach {
					if (onlyBase) {
						pathDepth++
						println(buildString {
							repeat(pathDepth) { append("   ") }
							append("|- [${it.identity}]")
						})
						pathDepth--
					} else
						printFolderContents(it)
				}
			}
			pathDepth--

		}

		private fun printWorld(world: RenderWorld) {
			pathDepth++
			println(buildString {
				repeat(pathDepth) { append("   ") }
				append("|- ${world.identity}.world")
			})
			pathDepth--
		}

		fun visualize(onlyBase: Boolean = false) {
			printFolderContents(RenderFolder("/", "/", address("/"), emptyList(), false), onlyBase)
		}

		val root: RenderFolder?
			get() = smashedStructure.firstOrNull { it.addressObject.addressString == "/" } as RenderFolder?

	}

	data class OpenWorldStructure(
		val smashedStructure: MutableList<RenderObject>,
	) {
		fun close() = WorldStructure(smashedStructure)
	}

	interface RenderObject : Identifiable<RenderObject>, Addressable<RenderObject> {
		val displayName: String
		val labels: List<String>
		val archived: Boolean

		fun renderLabels() = if (labels.isEmpty()) {
			"No Labels"
		} else
			labels.joinToString()

		fun renderArchiveStatus() = if (archived) "<aqua>Archived" else "<green>Active"

	}

	@Serializable
	@SerialName("WorldStructureRenderWorld")
	data class RenderWorld(
		override val displayName: String,
		override val identity: String,
		override val addressObject: Address<RenderObject>,
		override val labels: List<String>,
		override val archived: Boolean,
		val visitors: List<String>,
	) : RenderObject {

		init {
			// no / at the end, because it is no directory
			assert(addressObject.addressString.endsWith(identity)) { "value address (path) have to include itself at the end" }
		}

	}

	@Serializable
	@SerialName("WorldStructureRenderDirectory")
	data class RenderFolder(
		override val displayName: String,
		override val identity: String,
		override val addressObject: Address<RenderObject>,
		override val labels: List<String>,
		override val archived: Boolean,
	) : RenderObject {

		init {
			// / at the end, because it is a directory
			assert(addressObject.addressString.endsWith("$identity/")) { "value address (path) have to include itself at the end" }
		}

		val isRootDirectory = addressObject.addressString == "/"

	}

	@Serializable
	@SerialName("WorldStructureRenderBranch")
	data class RenderBranchResult(
		val currentDirectory: String,
		val renderWorlds: List<RenderWorld> = emptyList(),
		val renderFolders: List<RenderFolder> = emptyList(),
	) {

		fun smash() = renderFolders + renderWorlds

		fun structure() = WorldStructure(smash())

		fun getFolder(path: Address<RenderObject>) = renderFolders.firstOrNull { it.addressObject == path }

		fun folderExists(path: Address<RenderObject>) = getFolder(path).isNotNull

		fun getWorld(path: Address<RenderObject>) = renderWorlds.firstOrNull { it.addressObject == path }

		fun worldExists(path: Address<RenderObject>) = getWorld(path).isNotNull

	}

	fun computeFolderContents(folder: RenderFolder, worldStructure: WorldStructure = MoltenData.worldStructure.content) = renderWorldStructure(worldStructure, true, folder.addressObject.addressString)

	fun renderBase(base: WorldConfig = MoltenData.worldConfig.content) =
		RenderBranchResult("/", renderWorlds = base.importedWorlds.map {
			RenderWorld(it, it, address("/$it"), emptyList(), false, emptyList())
		})

	/**
	 * Path-Structure:
	 * - `/` home-dir
	 * - `/favorites/` *favorites* dir in the home dir
	 * @throws NoSuchElementException if the target-directory not exist
	 */
	fun renderWorldStructure(worldStructure: WorldStructure = MoltenData.worldStructure.content, onlyBaseFolder: Boolean = false, basePath: String = "/"): RenderBranchResult {
		val resultWorlds = mutableListOf<RenderWorld>()
		val resultFolders = mutableListOf<RenderFolder>()
		val smashedStructure = worldStructure.smashedStructure

		smashedStructure.forEach { undefined ->

			when (undefined) {
				is RenderWorld -> resultWorlds.add(undefined)
				is RenderFolder -> resultFolders.add(undefined)
			}

		}

		resultWorlds.mutableReplaceWith(resultWorlds.filter {
			if (onlyBaseFolder) {
				it.addressObject.addressString == "$basePath${it.identity}"
			} else
				it.addressObject.addressString.startsWith(basePath)
		})
		resultFolders.mutableReplaceWith(resultFolders.filter {
			if (onlyBaseFolder) {
				it.addressObject.addressString == "$basePath${it.identity}/" && it.addressObject.addressString != basePath
			} else
				it.addressObject.addressString.startsWith(basePath) && it.addressObject.addressString != basePath
		})

		return RenderBranchResult("/", resultWorlds, resultFolders)
	}

	/**
	 * Path-Structure:
	 * - `/` home-dir
	 * - `/favorites/` *favorites* dir in the home dir
	 * @throws NoSuchElementException if the target-directory not exist
	 */
	fun renderOverview(
		path: String = "/",
		base: WorldStructure = MoltenData.worldStructure.content,
	) = renderWorldStructure(base, true, path)

	object FileSystem {

		fun directoryExists(path: Address<RenderObject>) =
			renderWorldStructure().folderExists(path)

		fun getDirectory(path: Address<RenderObject>) =
			renderWorldStructure().getFolder(path)

		fun createDirectory(path: Address<RenderObject>) {
			assert(path.addressString.endsWith("/")) { "path does not define a folder (missing / at the end)" }

			MoltenData.worldStructure.editContent {
				val name = path.addressString.removeSurrounding("/").split("/").last()
				smashedStructure = (smashedStructure + RenderFolder(name, name, path, emptyList(), false)).distinctBy { it.identity }
			}
		}

		fun deleteDirectory(path: Address<RenderObject>, keepContent: Boolean = false) {
			assert(path.addressString.endsWith("/")) { "path does not define a folder (missing / at the end)" }

			MoltenData.worldStructure.editContent {
				val directory = getDirectory(path)
				if (directory != null) {

					// TODO the content of the folder have to be moved to the root directory
					// TODO: 25.10.21 to move, maybe use moveobject function at every content-object
					// TODO: 25.10.21 MOVE-EXPERIMENTAL START 

					computeFolderContents(directory).smash().forEach {
						if (keepContent) {
							moveObject(it.addressObject, address(
								when (it) {
									is RenderFolder -> "/$it/"
									else -> "/$it"
								}
							))
						} else {
							when (it) {
								is RenderWorld -> deleteWorld(it.addressObject)
								is RenderFolder -> deleteDirectory(it.addressObject)
							}
						}
					}
					// TODO: 25.10.21 EXPERIMENTAL END

					smashedStructure = smashedStructure.toMutableList().apply {
						removeAll { it.addressObject == path }
					}

				} else
					throw NoSuchElementException("No directory")
			}
		}

		fun worldExists(path: Address<RenderObject>) =
			renderWorldStructure().worldExists(path)

		fun getWorld(path: Address<RenderObject>) =
			renderWorldStructure().getWorld(path)

		fun importWorld(worldName: String, renderWorld: RenderWorld = RenderWorld(worldName, worldName, address("/$worldName"), emptyList(), false, emptyList())) {
			MoltenData.worldConfig.editContent {
				importedWorlds = (importedWorlds + worldName).distinctBy { it }
			}
			MoltenData.worldStructure.editContent {
				smashedStructure = (smashedStructure + renderWorld).distinctBy { it.identity }
			}
		}

		fun createWorld(worldName: String, worldEnvironment: World.Environment, worldType: WorldType, worldData: RenderWorld) {
			Bukkit.createWorld(WorldCreator(worldName).environment(worldEnvironment).type(worldType))
			importWorld(worldName, worldData.copy(identity = worldName))
		}

		/**
		 * Removes the world from the config
		 */
		fun ignoreWorld(path: Address<RenderObject>) {
			assert(!path.addressString.endsWith("/")) { "path does not define a world (/ at the end)" }
			val identity = path.addressString.split('/').last()

			MoltenData.worldConfig.editContent {
				importedWorlds = importedWorlds.filterNot { equals(identity) }
				autostartWorlds = autostartWorlds.filterNot { equals(identity) }
			}

			MoltenData.worldStructure.editContent {
				smashedStructure = smashedStructure.filterNot { it.identity == identity }
			}
		}

		fun deleteWorld(path: Address<RenderObject>) {
			assert(!path.addressString.endsWith("/")) { "path does not define a world (/ at the end)" }
			val identity = path.addressString.split('/').last()

			ignoreWorld(path)
			Path("$identity/").toFile().deleteRecursively()
		}

		@Suppress("UNUSED_PARAMETER")
		fun moveObject(currentObjectPath: Address<RenderObject>, futurePath: Address<RenderObject>) {
			// TODO: 25.10.21 Check if the currentPath object exists
			// TODO: 25.10.21 check if current is folder or world (world is easier than folder, because folder have contents)
			// TODO: 25.10.21 rename path to future path of world/folder and its folder-contents
		}

	}

}