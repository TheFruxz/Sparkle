package de.jet.paper.extension.display.ui

import de.jet.jvm.extension.data.url
import de.jet.jvm.extension.tryOrNull
import de.jet.paper.extension.mojang.getMojangProfile
import de.jet.paper.extension.paper.getOfflinePlayer
import de.jet.paper.tool.display.color.ColorType
import de.jet.paper.tool.display.color.DyeableMaterial
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.item.quirk.Quirk.Companion.skullQuirk
import de.jet.unfold.text
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import java.util.*

// Modification / change

val Material.colorType: ColorType?
	get() = ColorType.fromMaterial(this)

val Material.dyeable: DyeableMaterial?
	get() = DyeableMaterial.fromMaterial(this)

@Throws(IllegalArgumentException::class)
fun Material.changeColor(newColorType: ColorType): Material {
	val material = dyeable

	if (material != null) {
		return material.withColor(newColorType)
	} else
		throw IllegalArgumentException("The material '$name' is not colored!")

}

val ItemStack.materialColorType: ColorType?
	get() = type.colorType

val ItemStack.materialToDyeable: DyeableMaterial?
	get() = type.dyeable

@Throws(IllegalArgumentException::class)
fun ItemStack.changeColor(newColorType: ColorType) = type.changeColor(newColorType)

// Generation / creation

val Material.item: Item
	get() = Item(this)

fun Material.item(process: Item.() -> Unit) =
	item.apply(process)

val Material.itemStack: ItemStack
	get() = ItemStack(this)

fun Material.itemStack(process: ItemStack.() -> Unit) =
	itemStack.apply(process)

val ItemStack.item: Item
	get() = Item(this)

fun ItemStack.item(process: Item.() -> Unit) =
	item.apply(process)

fun Material.spawnEntity(location: Location, amount: Int = 1) = item.putSize(amount).spawn(location)

fun ItemStack.spawnEntity(location: Location, amount: Int = 1) = item.putSize(amount).spawn(location)

fun item(material: Material) = material.item

fun item(itemStack: ItemStack) = itemStack.item

/**
 * This function creates a new [Item] with a [Material.PLAYER_HEAD].
 * If the [httpRequest] parameter is set to true, the player's skin will be fetched from the Mojang API.
 * If the [httpRequest] parameter is set to false, the player's skin will be fetched from the server.
 * @see getMojangProfile
 * @param owner the name or uuid of the player
 * @param httpRequest if true, the player's skin will be fetched from the Mojang API, otherwise from the server
 * @return the created [Item]
 * @author Fruxz
 * @since 1.0
 */
fun skull(owner: String, httpRequest: Boolean = true) = Material.PLAYER_HEAD.item {

	getOfflinePlayer(owner).name?.let { label = text(it).color(NamedTextColor.YELLOW) }

	skullQuirk {

		if (httpRequest) {
			val ownerProfile = tryOrNull { runBlocking { getMojangProfile(owner) } }

			owningPlayer = getOfflinePlayer("MHF_Question")

			if (ownerProfile != null) {
				playerProfile = playerProfile!!.apply {
					setTextures(textures.apply {
						this.skin = url(ownerProfile.textures.skin.url)
					})
				}
				playerProfile!!.complete(true, true)
			}
		} else
			owningPlayer = getOfflinePlayer(owner)

	}
}

/**
 * This function creates a new [Item] with a [Material.PLAYER_HEAD].
 * If the [httpRequest] parameter is set to true, the player's skin will be fetched from the Mojang API.
 * If the [httpRequest] parameter is set to false, the player's skin will be fetched from the server.
 * @see getMojangProfile
 * @param owner the uuid of the player
 * @param httpRequest if true, the player's skin will be fetched from the Mojang API, otherwise from the server
 * @return the created [Item]
 * @author Fruxz
 * @since 1.0
 */
fun skull(owner: UUID, httpRequest: Boolean = true) = Material.PLAYER_HEAD.item {

	getOfflinePlayer(owner).name?.let { label = text(it).color(NamedTextColor.YELLOW) }

	skullQuirk {

		if (httpRequest) {
			val ownerProfile = tryOrNull { runBlocking { getMojangProfile(owner) } }

			owningPlayer = getOfflinePlayer("MHF_Question")

			if (ownerProfile != null) {
				playerProfile = playerProfile!!.apply {
					setTextures(textures.apply {
						this.skin = url(ownerProfile.textures.skin.url)
					})
				}
				playerProfile!!.complete(true, true)
			}
		} else
			owningPlayer = getOfflinePlayer(owner)
	}
}

/**
 * This function creates a new [Item] with a [Material.PLAYER_HEAD].
 * If the [httpRequest] parameter is set to true, the player's skin will be fetched from the Mojang API.
 * If the [httpRequest] parameter is set to false, the player's skin will be fetched from the server.
 * @see getMojangProfile
 * @param owner the player
 * @param httpRequest if true, the player's skin will be fetched from the Mojang API, otherwise from the server
 * @return the created [Item]
 * @author Fruxz
 * @since 1.0
 */
fun skull(owner: OfflinePlayer, httpRequest: Boolean = true) =
	skull(owner.uniqueId, httpRequest)