package de.jet.minecraft.app.component.essentials.world.tree

import de.jet.library.extension.collection.mutableReplaceWith
import de.jet.library.extension.switchResult
import de.jet.library.extension.tag.PromisingData
import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.positioning.Address
import de.jet.library.tool.smart.positioning.Address.Companion.address
import de.jet.library.tool.smart.positioning.Addressable
import de.jet.minecraft.app.JetData
import de.jet.minecraft.app.component.essentials.world.WorldConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.WorldType

object WorldTree {

	private var pathDepth = -1

	@Serializable
	data class WorldStructure(
		var smashedStructure: List<RenderObject>,
	) : PromisingData {

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
			get() = smashedStructure.firstOrNull { it.address.address == "/" } as RenderFolder?

	}

	data class OpenWorldStructure(
		val smashedStructure: MutableList<RenderObject>,
	) {
		fun close() = WorldStructure(smashedStructure)
	}

	interface RenderObject : Identifiable<RenderObject>, Addressable<RenderObject>, PromisingData {
		val displayName: String
		val labels: List<String>
		val archived: Boolean
	}

	@Serializable
	@SerialName("world")
	data class RenderWorld(
		override val displayName: String,
		override val identity: String,
		override val address: Address<RenderObject>,
		override val labels: List<String>,
		override val archived: Boolean,
		val visitors: List<String>,
	) : RenderObject {

		init {
			// no / at the end, because it is no directory
			assert(address.address.endsWith(identity)) { "value address (path) have to include itself at the end" }
		}

	}

	@Serializable
	@SerialName("folder")
	data class RenderFolder(
		override val displayName: String,
		override val identity: String,
		override val address: Address<RenderObject>,
		override val labels: List<String>,
		override val archived: Boolean,
	) : RenderObject {

		init {
			// / at the end, because it is a directory
			assert(address.address.endsWith("$identity/")) { "value address (path) have to include itself at the end" }
		}

		val isRootDirectory = address.address == "/"

	}

	@Serializable
	data class RenderBranchResult(
		val currentDirectory: String,
		val renderWorlds: List<RenderWorld> = emptyList(),
		val renderFolders: List<RenderFolder> = emptyList(),
	) : PromisingData {

		fun smash() = renderFolders + renderWorlds

		fun structure() = WorldStructure(smash())

		fun folderExists(path: Address<RenderObject>) = renderFolders.any { it.address.address == path.address }

		fun worldExists(path: Address<RenderObject>) = renderWorlds.any { it.address.address == path.address }

	}

	fun computeFolderContents(folder: RenderFolder, worldStructure: WorldStructure = JetData.worldStructure.content) = renderWorldStructure(worldStructure, true, folder.address.address)

	fun renderBase(base: WorldConfig = JetData.worldConfig.content) =
		RenderBranchResult("/", renderWorlds = base.importedWorlds.map {
			RenderWorld(it, it, address("/$it"), emptyList(), false, emptyList())
		})

	/**
	 * Path-Structure:
	 * - `/` home-dir
	 * - `/favorites/` *favorites* dir in the home dir
	 * @throws NoSuchElementException if the target-directory not exist
	 */
	fun renderWorldStructure(worldStructure: WorldStructure = JetData.worldStructure.content, onlyBaseFolder: Boolean = false, basePath: String = "/"): RenderBranchResult {
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
				it.address.address == "$basePath${it.identity}"
			} else
				it.address.address.startsWith(basePath)
		})
		resultFolders.mutableReplaceWith(resultFolders.filter {
			if (onlyBaseFolder) {
				it.address.address == "$basePath${it.identity}/" && it.address.address != basePath
			} else
				it.address.address.startsWith(basePath) && it.address.address != basePath
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
		base: WorldStructure = JetData.worldStructure.content,
	) = renderWorldStructure(base, true, path)

	object FileSystem {
		fun demo() = WorldStructure(listOf(

			RenderFolder("/", "/", address("/"), emptyList(), false),

			RenderFolder("onefolder", "onefolder", address("/onefolder/"), emptyList(), false),
			RenderFolder("onefolder1", "onefolder1", address("/onefolder1/"), emptyList(), false),
			RenderFolder("onefolder2", "onefolder2", address("/onefolder2/"), emptyList(), false),

			RenderWorld("oneworld", "oneworld", address("/oneworld"), emptyList(), false, emptyList()),
			RenderWorld("oneworld1", "oneworld1", address("/onefolder1/oneworld1"), emptyList(), false, emptyList()),
			RenderWorld("oneworld2", "oneworld2", address("/onefolder2/oneworld2"), emptyList(), false, emptyList()),

		))

		fun directoryExists(path: Address<RenderObject>) =
			renderWorldStructure().folderExists(path)

		fun createDirectory(path: Address<RenderObject>) {

		}

		fun deleteDirectory(path: Address<RenderObject>) {

		}

		fun worldExists(path: Address<RenderObject>) =
			renderWorldStructure().worldExists(path)

		fun importWorld(worldName: String) {
			JetData.worldConfig.editContent {
				importedWorlds = (importedWorlds + worldName).distinct()
			}
			JetData.worldStructure.editContent {
				smashedStructure = (smashedStructure + RenderWorld(worldName, worldName, address("/$worldName"), emptyList(), false, emptyList())).distinct()
			}
		}

		fun createWorld(worldName: String, worldType: WorldType, worldData: RenderWorld) {

		}

		/**
		 * Removes the world from the config
		 */
		fun ignoreWorld(path: Address<RenderObject>) {
			// TODO: 23.10.2021 remove from config & autostart words
			// TODO: 23.10.2021 remove from structure
		}

		fun deleteWorld(path: Address<RenderObject>) {
			// TODO: 23.10.2021 completly ignoreWorld() & delete of the real world folder
		}

	}

}