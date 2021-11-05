package de.jet.library.tool.smart.positioning

@Suppress("SpellCheckingInspection")
interface Pathed<T> : Addressable<T> {

	override val address: Address<T>
		get() = path

	val path: Address<T>

	val pathParts: List<String>
		get() {
			var isDirectory = false
			var pathString = path.addressString

			if (pathString.endsWith("/")) {
				isDirectory = true
				pathString = pathString.removeSuffix("/")
			}

			val list = pathString.removePrefix("/").split("/")

			return list.mapIndexed { index, s ->
				if (isDirectory && index == list.lastIndex) {
					"$s/"
				} else {
					s
				}
			}
		}

}