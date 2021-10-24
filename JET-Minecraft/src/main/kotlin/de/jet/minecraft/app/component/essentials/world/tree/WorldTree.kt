package de.jet.minecraft.app.component.essentials.world.tree

import de.jet.library.extension.collection.mutableReplaceWith
import de.jet.library.extension.tag.PromisingData
import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.positioning.Address
import de.jet.library.tool.smart.positioning.Address.Companion.address
import de.jet.library.tool.smart.positioning.Addressable
import de.jet.minecraft.app.JetData
import de.jet.minecraft.app.component.essentials.world.WorldConfig
import kotlinx.serialization.Serializable
import org.bukkit.WorldType

object WorldTree {

	@Serializable
	data class WorldStructure(
		val smashedStructure: List<RenderObject>,
	) : PromisingData {

		private var pathDepth = 0

		private fun printFolderContents(folder: RenderFolder) {
			pathDepth++
			println(buildString {
				repeat(pathDepth) { append("   ") }
				append("|- [${folder.identity}]")
			})
			computeFolderContents(folder, this).let { result ->

				pathDepth++
				result.renderWorlds.forEach { printWorld(it) }
				result.renderFolders.forEach { printFolderContents(it) }
				pathDepth--
			}
			pathDepth--
		}

		private fun printWorld(world: RenderWorld) {
			println(buildString {
				repeat(pathDepth) { append("   ") }
				append("|- ${world.identity}.world")
			})
		}

		fun visualize() {
			println("AB-start")
			smashedStructure.forEach { println(it.address.address) }
			println("AB-end")
			println("|- ROOT: [/]")
			printFolderContents(smashedStructure.first { it.address.address == "/" } as RenderFolder)
		}

	}

	data class OpenWorldStructure(
		val smashedStructure: MutableList<RenderObject>,
	) {
		fun close() = WorldStructure(smashedStructure)
	}

	sealed interface RenderObject : Identifiable<RenderObject>, Addressable<RenderObject>, PromisingData {
		val displayName: String
		val labels: List<String>
		val archived: Boolean
	}

	@Serializable
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

		if (onlyBaseFolder && resultFolders.none { it.address.address == basePath }) {
			throw NoSuchElementException("basePath directory '$basePath' does not exist!")
		}

		if (basePath != "/") {
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
		}

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

		fun buildTree(process: OpenWorldStructure.() -> Unit) = OpenWorldStructure(mutableListOf(
			//RenderFolder("/", "/", address("/"), emptyList(), false)
		)).apply(process)

		fun OpenWorldStructure.folder(folderName: String, process: Pair<Address<RenderObject>, OpenWorldStructure>.() -> Unit = { }): Pair<Address<RenderObject>, OpenWorldStructure> {
			smashedStructure.add(RenderFolder(folderName, folderName, address("/$folderName/"), emptyList(), false))
			return address<RenderObject>("/$folderName/") to this
		}

		fun OpenWorldStructure.world(worldName: String): Pair<Address<RenderObject>, OpenWorldStructure> {
			smashedStructure.add(RenderWorld(worldName, worldName, address("/$worldName"), emptyList(), false, emptyList()))
			return address<RenderObject>("/$worldName") to this
		}

		fun Pair<Address<RenderObject>, OpenWorldStructure>.folder(folderName: String, process: Pair<Address<RenderObject>, OpenWorldStructure>.() -> Unit = { }): Pair<Address<RenderObject>, OpenWorldStructure> {
			second.smashedStructure.add(RenderFolder(folderName, folderName, address(first.address + folderName + "/"), emptyList(), false))
			return first to second
		}

		fun Pair<Address<RenderObject>, OpenWorldStructure>.world(worldName: String): Pair<Address<RenderObject>, OpenWorldStructure> {
			second.smashedStructure.add(RenderWorld(worldName, worldName, address(first.address + worldName), emptyList(), false, emptyList()))
			return first to second
		}

		fun demo() = buildTree {

			world("demo-world")
			world("more-world")

			folder("test")
			folder("lol")
			folder("more") {

				world("some")
				world("more")
				world("worlds")

				folder("demo1")
				folder("demo2")
				folder("demo3")
				folder("demo4") {
					folder("x1") {
						world("doit")
					}
					folder("x2") {
						world("smooth")
					}
				}
			}
		}

		fun directoryExists(path: Address<RenderObject>) =
			renderWorldStructure().folderExists(path)

		fun createDirectory(path: Address<RenderObject>) {

		}

		fun deleteDirectory(path: Address<RenderObject>) {

		}

		fun worldExists(path: Address<RenderObject>) =
			renderWorldStructure().worldExists(path)

		fun importWorld(worldName: String) {

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