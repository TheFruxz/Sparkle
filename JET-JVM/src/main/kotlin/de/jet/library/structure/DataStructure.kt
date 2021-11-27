package de.jet.library.structure

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.library.tool.smart.positioning.Address
import de.jet.library.tool.smart.positioning.Address.Companion.address
import de.jet.library.tool.smart.positioning.Pathed

open class DataStructureHolder<T : DataStructureItem>(
	open val smashedStructure: List<T>,
) {

	fun renderView(basePath: Address<DataStructureItem> = address("/"), onlyBase: Boolean = true): DataStructureRenderedView<DataStructureItem> {
		val resultItems = mutableListOf<T>()

		smashedStructure.forEach { item ->

			assert(item.existenceAssertion(item)) { "${item.addressString} failed existenceAssertion!" }

			if (item.path.addressString.startsWith(basePath.addressString) && (basePath.equals("/") || basePath.addressString != item.path.addressString)) {

				when {
					(onlyBase && basePath.addressString == "$basePath${item.path}") -> resultItems.add(item)
				}

			}

		}

		return DataStructureRenderedView(basePath, resultItems.toList())
	}

}

interface DataStructureItem : Identifiable<DataStructureItem>, Pathed<DataStructureItem> {

	val existenceAssertion: DataStructureItem.() -> Boolean
		get() = { addressString.endsWith("/$identity") }

	fun <T : DataStructureItem> computeParent(holder: DataStructureHolder<T>, onlyBase: Boolean = true) = if (address.addressString == "/$identity") {
		holder.smashedStructure.first { it.addressString == "/" }
	} else {
		holder.smashedStructure.first { it.addressString == pathParts.dropLast(1).joinToString("/") + "/" }
	}

}

interface DataStructureDirectory : DataStructureItem {

	override val existenceAssertion: DataStructureItem.() -> Boolean
		get() = { addressString.endsWith("/$identity/") }

	fun <T : DataStructureItem> computeChildren(holder: DataStructureHolder<T>, onlyBase: Boolean = true): List<DataStructureItem> =
		holder.renderView(address, onlyBase).structure

	val isRootDirectory: Boolean
		get() = addressString == "/"

}

data class DataStructureRenderedView<T : DataStructureItem>(
	val basePath: Address<T>,
	val structure: List<T>,
)

object DataStructure {

	private var currentPathDepth = -1



}