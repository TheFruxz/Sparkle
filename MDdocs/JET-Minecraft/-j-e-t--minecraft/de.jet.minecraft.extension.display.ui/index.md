//[JET-Minecraft](../../index.md)/[de.jet.minecraft.extension.display.ui](index.md)

# Package de.jet.minecraft.extension.display.ui

## Functions

| Name | Summary |
|---|---|
| [buildClickAction](build-click-action.md) | [jvm]<br>fun [buildClickAction](build-click-action.md)(async: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, stop: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, cooldown: [ActionCooldown](../de.jet.minecraft.tool.display.item.action/-action-cooldown/index.md)? = null, action: InventoryClickEvent.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [ItemClickAction](../de.jet.minecraft.tool.display.item.action/-item-click-action/index.md) |
| [buildContainer](build-container.md) | [jvm]<br>fun [buildContainer](build-container.md)(lines: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3, action: [Container](../de.jet.minecraft.tool.display.ui.inventory/-container/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Container](../de.jet.minecraft.tool.display.ui.inventory/-container/index.md) |
| [buildInteractAction](build-interact-action.md) | [jvm]<br>fun [buildInteractAction](build-interact-action.md)(async: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, stop: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, cooldown: [ActionCooldown](../de.jet.minecraft.tool.display.item.action/-action-cooldown/index.md)? = null, action: [PlayerInteractAtItemEvent](../de.jet.minecraft.runtime.event.interact/-player-interact-at-item-event/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [ItemInteractAction](../de.jet.minecraft.tool.display.item.action/-item-interact-action/index.md) |
| [buildPanel](build-panel.md) | [jvm]<br>fun [buildPanel](build-panel.md)(lines: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3, action: [Panel](../de.jet.minecraft.tool.display.ui.panel/-panel/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Panel](../de.jet.minecraft.tool.display.ui.panel/-panel/index.md) |
| [changeColor](change-color.md) | [jvm]<br>fun Material.[changeColor](change-color.md)(newColorType: [ColorType](../de.jet.minecraft.tool.display.color/-color-type/index.md)): Material<br>fun ItemStack.[changeColor](change-color.md)(newColorType: [ColorType](../de.jet.minecraft.tool.display.color/-color-type/index.md)): Material |
| [copyRaw](copy-raw.md) | [jvm]<br>fun Inventory.[copyRaw](copy-raw.md)(title: Component): @NotNullInventory |
| [emptyContainer](empty-container.md) | [jvm]<br>fun [emptyContainer](empty-container.md)(lines: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3): [Container](../de.jet.minecraft.tool.display.ui.inventory/-container/index.md) |
| [emptyPanel](empty-panel.md) | [jvm]<br>fun [emptyPanel](empty-panel.md)(lines: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3): [Panel](../de.jet.minecraft.tool.display.ui.panel/-panel/index.md) |
| [get](get.md) | [jvm]<br>operator fun &lt;[T](get.md) : Inventory&gt; [T](get.md).[get](get.md)(slot: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): @NullableItemStack? |
| [item](item.md) | [jvm]<br>fun [item](item.md)(material: Material): [Item](../de.jet.minecraft.tool.display.item/-item/index.md)<br>fun [item](item.md)(itemStack: ItemStack): [Item](../de.jet.minecraft.tool.display.item/-item/index.md) |
| [set](set.md) | [jvm]<br>operator fun &lt;[T](set.md) : Inventory&gt; [T](set.md).[set](set.md)(slot: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), item: [Item](../de.jet.minecraft.tool.display.item/-item/index.md))<br>operator fun &lt;[T](set.md) : Inventory&gt; [T](set.md).[set](set.md)(slot: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), itemStack: ItemStack) |
| [skull](skull.md) | [jvm]<br>fun [skull](skull.md)(owner: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Item](../de.jet.minecraft.tool.display.item/-item/index.md)<br>fun [skull](skull.md)(owner: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Item](../de.jet.minecraft.tool.display.item/-item/index.md)<br>fun [skull](skull.md)(owner: OfflinePlayer): [Item](../de.jet.minecraft.tool.display.item/-item/index.md) |
| [spawnEntity](spawn-entity.md) | [jvm]<br>fun Material.[spawnEntity](spawn-entity.md)(location: Location, amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1): @NotNullItem<br>fun ItemStack.[spawnEntity](spawn-entity.md)(location: Location, amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 1): @NotNullItem |

## Properties

| Name | Summary |
|---|---|
| [colorType](color-type.md) | [jvm]<br>val Material.[colorType](color-type.md): [ColorType](../de.jet.minecraft.tool.display.color/-color-type/index.md)? |
| [dyeable](dyeable.md) | [jvm]<br>val Material.[dyeable](dyeable.md): [DyeableMaterial](../de.jet.minecraft.tool.display.color/-dyeable-material/index.md)? |
| [item](item.md) | [jvm]<br>val Material.[item](item.md): [Item](../de.jet.minecraft.tool.display.item/-item/index.md) |
| [item](item.md) | [jvm]<br>val ItemStack.[item](item.md): [Item](../de.jet.minecraft.tool.display.item/-item/index.md) |
| [itemStack](item-stack.md) | [jvm]<br>val Material.[itemStack](item-stack.md): ItemStack |
| [materialColorType](material-color-type.md) | [jvm]<br>val ItemStack.[materialColorType](material-color-type.md): [ColorType](../de.jet.minecraft.tool.display.color/-color-type/index.md)? |
| [materialToDyeable](material-to-dyeable.md) | [jvm]<br>val ItemStack.[materialToDyeable](material-to-dyeable.md): [DyeableMaterial](../de.jet.minecraft.tool.display.color/-dyeable-material/index.md)? |
