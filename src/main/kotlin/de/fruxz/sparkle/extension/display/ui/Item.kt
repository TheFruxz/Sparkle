package de.fruxz.sparkle.extension.display.ui

import de.fruxz.ascend.extension.data.url
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.sparkle.extension.mojang.getMojangProfile
import de.fruxz.sparkle.extension.paper.offlinePlayer
import de.fruxz.sparkle.tool.display.color.ColorType
import de.fruxz.sparkle.tool.display.color.DyeableMaterial
import de.fruxz.sparkle.tool.display.item.Item
import de.fruxz.sparkle.tool.display.item.ItemLike
import de.fruxz.sparkle.tool.display.item.quirk.Quirk.Companion.skullQuirk
import de.fruxz.stacked.text
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

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

val Material.itemLike: ItemLike
	get() = ItemLike.of(this)

fun Material.item(process: Item.() -> Unit) =
	item.apply(process)

val Material.itemStack: ItemStack
	get() = ItemStack(this)

fun Material.itemStack(process: ItemStack.() -> Unit) =
	itemStack.apply(process)

val ItemStack.item: Item
	get() = Item(this)

val ItemStack.itemLike: ItemLike
	get() = ItemLike.of(this)

fun ItemStack.item(process: Item.() -> Unit) =
	item.apply(process)

val ItemStack.itemMetaOrNull: ItemMeta?
	get() = when {
		hasItemMeta() -> itemMeta
		else -> null
	}

fun Material.spawnEntity(location: Location, amount: Int = 1) = item.putSize(amount).spawn(location)

fun ItemStack.spawnEntity(location: Location, amount: Int = 1) = item.putSize(amount).spawn(location)

fun item(material: Material) = material.item

fun item(itemStack: ItemStack) = itemStack.item

/**
 * This function creates a new [Item] with a [Material.PLAYER_HEAD].
 * If the [httpRequest] parameter is set to true, the player's skin will be fetched from the Mojang API.
 * If the [httpRequest] parameter is set to false, the player's skin will be fetched from the server.
 * @see getMojangProfile
 * @param ownerName the name or uuid of the player
 * @param httpRequest if true, the player's skin will be fetched from the Mojang API, otherwise from the server
 * @param timeout time allowed, before the http request is aborted (useful if mojang is down again)
 * @return the created [Item]
 * @author Fruxz
 * @since 1.0
 */
fun skull(ownerName: String, httpRequest: Boolean = true, timeout: Duration = 5.seconds): Item = Material.PLAYER_HEAD.item {

	runBlocking { withTimeoutOrNull(timeout) { offlinePlayer(ownerName).name } }?.let { label = text(it).color(NamedTextColor.YELLOW) }

	skullQuirk {

		if (httpRequest) {
			val ownerProfile = tryOrNull {
				runBlocking {
					withTimeoutOrNull(timeout) {
						getMojangProfile(ownerName)
					}
				}
			}

			owningPlayer = offlinePlayer("MHF_Question")

			if (ownerProfile != null) {
				playerProfile = playerProfile!!.apply {
					setTextures(textures.apply {
						this.skin = url(ownerProfile.textures.skin.url)
					})
				}
				playerProfile!!.complete(true, true)
			}
		} else
			owningPlayer = offlinePlayer(ownerName)

	}
}

/**
 * This function creates a new [Item] with a [Material.PLAYER_HEAD].
 * If the [httpRequest] parameter is set to true, the player's skin will be fetched from the Mojang API.
 * If the [httpRequest] parameter is set to false, the player's skin will be fetched from the server.
 * @see getMojangProfile
 * @param owner the uuid of the player
 * @param httpRequest if true, the player's skin will be fetched from the Mojang API, otherwise from the server
 * @param timeout time allowed, before the http request is aborted (useful if Mojang is down again)
 * @return the created [Item]
 * @author Fruxz
 * @since 1.0
 */
fun skull(ownerUUID: UUID, httpRequest: Boolean = true, timeout: Duration = 5.seconds): Item =
	skull(ownerName = "$ownerUUID", httpRequest, timeout)

/**
 * This function creates a new [Item] with a [Material.PLAYER_HEAD].
 * If the [httpRequest] parameter is set to true, the player's skin will be fetched from the Mojang API.
 * If the [httpRequest] parameter is set to false, the player's skin will be fetched from the server.
 * @see getMojangProfile
 * @param owner the player
 * @param httpRequest if true, the player's skin will be fetched from the Mojang API, otherwise from the server
 * @param timeout time allowed, before the http request is aborted (useful if Mojang is down again)
 * @return the created [Item]
 * @author Fruxz
 * @since 1.0
 */
fun skull(owner: OfflinePlayer, httpRequest: Boolean = true, timeout: Duration = 5.seconds) =
	skull(owner.uniqueId, httpRequest, timeout)

fun ItemStack.copy(
	material: Material = this.type,
	amount: Int = this.amount,
	itemMeta: ItemMeta = this.itemMeta,
) = ItemStack(this).apply {
	this.type = material
	this.amount = amount
	this.itemMeta = itemMeta
}