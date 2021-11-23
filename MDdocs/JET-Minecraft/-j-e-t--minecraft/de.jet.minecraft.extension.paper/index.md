//[JET-Minecraft](../../index.md)/[de.jet.minecraft.extension.paper](index.md)

# Package de.jet.minecraft.extension.paper

## Functions

| Name | Summary |
|---|---|
| [addRecipe](add-recipe.md) | [jvm]<br>fun [addRecipe](add-recipe.md)(recipe: Recipe): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [banIP](ban-i-p.md) | [jvm]<br>fun [banIP](ban-i-p.md)(address: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [box](box.md) | [jvm]<br>fun [box](box.md)(location1: Location, location2: Location): @NotNullBoundingBox |
| [broadcast](broadcast.md) | [jvm]<br>fun [broadcast](broadcast.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), permission: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>fun [broadcast](broadcast.md)(message: Component, permission: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [broadcastMessage](broadcast-message.md) | [jvm]<br>fun [broadcastMessage](broadcast-message.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [clearRecipes](clear-recipes.md) | [jvm]<br>fun [clearRecipes](clear-recipes.md)() |
| [contains](contains.md) | [jvm]<br>fun BoundingBox.[contains](contains.md)(location: Location): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun BoundingBox.[contains](contains.md)(block: Block): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun BoundingBox.[contains](contains.md)(entity: Entity): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun BoundingBox.[contains](contains.md)(player: Player): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [createBlockData](create-block-data.md) | [jvm]<br>fun [createBlockData](create-block-data.md)(data: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NotNullBlockData<br>fun [createBlockData](create-block-data.md)(material: Material): @NotNullBlockData<br>fun [createBlockData](create-block-data.md)(material: Material, consumer: [Consumer](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html)&lt;BlockData&gt;?): @NotNullBlockData<br>fun [createBlockData](create-block-data.md)(material: Material?, data: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): @NotNullBlockData |
| [createBossBar](create-boss-bar.md) | [jvm]<br>fun [createBossBar](create-boss-bar.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, color: BarColor, style: BarStyle, vararg flags: BarFlag): @NotNullBossBar<br>fun [createBossBar](create-boss-bar.md)(key: NamespacedKey, title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, color: BarColor, style: BarStyle, vararg flags: BarFlag): @NotNullKeyedBossBar |
| [createChunkData](create-chunk-data.md) | [jvm]<br>fun [createChunkData](create-chunk-data.md)(world: World): @NotNullChunkGenerator.ChunkData |
| [createExplorerMap](create-explorer-map.md) | [jvm]<br>fun [createExplorerMap](create-explorer-map.md)(world: World, location: Location, structureType: StructureType): @NotNullItemStack<br>fun [createExplorerMap](create-explorer-map.md)(world: World, location: Location, structureType: StructureType, radius: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), findUnexplored: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): @NotNullItemStack |
| [createInventory](create-inventory.md) | [jvm]<br>fun [createInventory](create-inventory.md)(owner: InventoryHolder?, size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): @NotNullInventory<br>fun [createInventory](create-inventory.md)(owner: InventoryHolder?, type: InventoryType): @NotNullInventory<br>fun [createInventory](create-inventory.md)(owner: InventoryHolder?, size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NotNullInventory<br>fun [createInventory](create-inventory.md)(owner: InventoryHolder?, size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), title: Component): @NotNullInventory<br>fun [createInventory](create-inventory.md)(owner: InventoryHolder?, type: InventoryType, title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NotNullInventory<br>fun [createInventory](create-inventory.md)(owner: InventoryHolder?, type: InventoryType, title: Component): @NotNullInventory |
| [createKey](create-key.md) | [jvm]<br>fun Plugin.[createKey](create-key.md)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): NamespacedKey |
| [createMap](create-map.md) | [jvm]<br>fun [createMap](create-map.md)(world: World): @NotNullMapView |
| [createMerchant](create-merchant.md) | [jvm]<br>fun [createMerchant](create-merchant.md)(title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NotNullMerchant<br>fun [createMerchant](create-merchant.md)(title: Component): @NotNullMerchant |
| [createProfile](create-profile.md) | [jvm]<br>fun [createProfile](create-profile.md)(uuid: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): @NotNullPlayerProfile<br>fun [createProfile](create-profile.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NotNullPlayerProfile<br>fun [createProfile](create-profile.md)(uuid: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)?, name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): @NotNullPlayerProfile |
| [createVanillaChunkData](create-vanilla-chunk-data.md) | [jvm]<br>fun [createVanillaChunkData](create-vanilla-chunk-data.md)(world: World, x: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), z: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): @NotNullChunkGenerator.ChunkData |
| [createWorld](create-world.md) | [jvm]<br>fun [createWorld](create-world.md)(creator: WorldCreator): @NullableWorld? |
| [directionVectorVelocity](direction-vector-velocity.md) | [jvm]<br>fun [directionVectorVelocity](direction-vector-velocity.md)(from: Location, to: Location): @NotNullVector |
| [dispatchCommand](dispatch-command.md) | [jvm]<br>fun [dispatchCommand](dispatch-command.md)(sender: CommandSender, commandLine: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [displayString](display-string.md) | [jvm]<br>fun [LocationBox](../de.jet.minecraft.tool.position/-location-box/index.md).[displayString](display-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>fun Location.[displayString](display-string.md)(withNames: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, withRotation: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, displayX: [Char](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char/index.html) = 'x', displayY: [Char](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char/index.html) = 'y', displayZ: [Char](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char/index.html) = 'z', displayYaw: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "yaw", displayPitch: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "pitch"): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getAdvancement](get-advancement.md) | [jvm]<br>fun [getAdvancement](get-advancement.md)(key: NamespacedKey): @NullableAdvancement? |
| [getBanList](get-ban-list.md) | [jvm]<br>fun [getBanList](get-ban-list.md)(type: BanList.Type): @NotNullBanList |
| [getBossBar](get-boss-bar.md) | [jvm]<br>fun [getBossBar](get-boss-bar.md)(key: NamespacedKey): @NullableKeyedBossBar? |
| [getEntity](get-entity.md) | [jvm]<br>fun [getEntity](get-entity.md)(uuid: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): @NullableEntity? |
| [getLootTable](get-loot-table.md) | [jvm]<br>fun [getLootTable](get-loot-table.md)(key: NamespacedKey): @NullableLootTable? |
| [getMessageOfTheDay](get-message-of-the-day.md) | [jvm]<br>fun [getMessageOfTheDay](get-message-of-the-day.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getNearbyBlocks](get-nearby-blocks.md) | [jvm]<br>fun Location.[getNearbyBlocks](get-nearby-blocks.md)(radius: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;Block&gt; |
| [getOfflinePlayer](get-offline-player.md) | [jvm]<br>fun [getOfflinePlayer](get-offline-player.md)(uniqueIdentity: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): @NotNullOfflinePlayer<br>fun [getOfflinePlayer](get-offline-player.md)(playerName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NotNullOfflinePlayer |
| [getPlayer](get-player.md) | [jvm]<br>fun [getPlayer](get-player.md)(uniqueIdentity: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): @NullablePlayer?<br>fun [getPlayer](get-player.md)(playerName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NullablePlayer? |
| [getPluginCommand](get-plugin-command.md) | [jvm]<br>fun [getPluginCommand](get-plugin-command.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NullablePluginCommand? |
| [getRecipe](get-recipe.md) | [jvm]<br>fun [getRecipe](get-recipe.md)(recipeKey: NamespacedKey): @NullableRecipe? |
| [getRecipesFor](get-recipes-for.md) | [jvm]<br>fun [getRecipesFor](get-recipes-for.md)(result: ItemStack): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Recipe&gt; |
| [getShutdownMessage](get-shutdown-message.md) | [jvm]<br>fun [getShutdownMessage](get-shutdown-message.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [getWorld](get-world.md) | [jvm]<br>fun [getWorld](get-world.md)(uid: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): @NullableWorld?<br>fun [getWorld](get-world.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NullableWorld?<br>fun [getWorld](get-world.md)(worldKey: NamespacedKey): @NullableWorld? |
| [hasApproval](has-approval.md) | [jvm]<br>fun Permissible.[hasApproval](has-approval.md)(approval: [Approval](../de.jet.minecraft.tool.permission/-approval/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [loadServerIcon](load-server-icon.md) | [jvm]<br>fun [loadServerIcon](load-server-icon.md)(image: [BufferedImage](https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImage.html)): @NotNullCachedServerIcon<br>fun [loadServerIcon](load-server-icon.md)(file: [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html)): @NotNullCachedServerIcon |
| [matchPlayer](match-player.md) | [jvm]<br>fun [matchPlayer](match-player.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Player&gt; |
| [maxOutHealth](max-out-health.md) | [jvm]<br>fun LivingEntity.[maxOutHealth](max-out-health.md)() |
| [plus](plus.md) | [jvm]<br>operator fun Location.[plus](plus.md)(location: Location): [LocationBox](../de.jet.minecraft.tool.position/-location-box/index.md) |
| [positioning](positioning.md) | [jvm]<br>fun ParticleBuilder.[positioning](positioning.md)(block: Block): @NotNullParticleBuilder<br>fun ParticleBuilder.[positioning](positioning.md)(entity: Entity): @NotNullParticleBuilder<br>fun ParticleBuilder.[positioning](positioning.md)(world: World, boundingBox: BoundingBox): @NotNullParticleBuilder |
| [reload](reload.md) | [jvm]<br>fun [reload](reload.md)() |
| [reloadCommandAliases](reload-command-aliases.md) | [jvm]<br>fun [reloadCommandAliases](reload-command-aliases.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [reloadData](reload-data.md) | [jvm]<br>fun [reloadData](reload-data.md)() |
| [reloadPermissions](reload-permissions.md) | [jvm]<br>fun [reloadPermissions](reload-permissions.md)() |
| [reloadWhitelist](reload-whitelist.md) | [jvm]<br>fun [reloadWhitelist](reload-whitelist.md)() |
| [removeBossBar](remove-boss-bar.md) | [jvm]<br>fun [removeBossBar](remove-boss-bar.md)(key: NamespacedKey): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [removeRecipe](remove-recipe.md) | [jvm]<br>fun [removeRecipe](remove-recipe.md)(key: NamespacedKey): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [resetRecipes](reset-recipes.md) | [jvm]<br>fun [resetRecipes](reset-recipes.md)() |
| [savePlayers](save-players.md) | [jvm]<br>fun [savePlayers](save-players.md)() |
| [selectEntities](select-entities.md) | [jvm]<br>fun [selectEntities](select-entities.md)(sender: CommandSender, selector: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Entity&gt; |
| [setDefaultGameMode](set-default-game-mode.md) | [jvm]<br>fun [setDefaultGameMode](set-default-game-mode.md)(mode: GameMode) |
| [shutdown](shutdown.md) | [jvm]<br>fun [shutdown](shutdown.md)() |
| [unbanIP](unban-i-p.md) | [jvm]<br>fun [unbanIP](unban-i-p.md)(address: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [unloadWorld](unload-world.md) | [jvm]<br>fun [unloadWorld](unload-world.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), save: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun [unloadWorld](unload-world.md)(world: World, save: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [advancementIterator](advancement-iterator.md) | [jvm]<br>val [advancementIterator](advancement-iterator.md): [Iterator](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterator/index.html)&lt;Advancement&gt; |
| [adventureComponent](adventure-component.md) | [jvm]<br>val [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?.[adventureComponent](adventure-component.md): TextComponent |
| [allowEnd](allow-end.md) | [jvm]<br>val [allowEnd](allow-end.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [allowFlight](allow-flight.md) | [jvm]<br>val [allowFlight](allow-flight.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [allowNether](allow-nether.md) | [jvm]<br>val [allowNether](allow-nether.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [ambientSpawnLimit](ambient-spawn-limit.md) | [jvm]<br>val [ambientSpawnLimit](ambient-spawn-limit.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [animalSpawnLimit](animal-spawn-limit.md) | [jvm]<br>val [animalSpawnLimit](animal-spawn-limit.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [averageTickTime](average-tick-time.md) | [jvm]<br>val [averageTickTime](average-tick-time.md): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [bannedPlayers](banned-players.md) | [jvm]<br>val [bannedPlayers](banned-players.md): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;OfflinePlayer&gt; |
| [bossBars](boss-bars.md) | [jvm]<br>val [bossBars](boss-bars.md): [Iterator](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterator/index.html)&lt;KeyedBossBar&gt; |
| [buildMode](build-mode.md) | [jvm]<br>var OfflinePlayer.[buildMode](build-mode.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [bukkitVersion](bukkit-version.md) | [jvm]<br>val [bukkitVersion](bukkit-version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [commandAliases](command-aliases.md) | [jvm]<br>val [commandAliases](command-aliases.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |
| [commandMap](command-map.md) | [jvm]<br>val [commandMap](command-map.md): CommandMap |
| [connectionThrottle](connection-throttle.md) | [jvm]<br>val [connectionThrottle](connection-throttle.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [consoleSender](console-sender.md) | [jvm]<br>val [consoleSender](console-sender.md): ConsoleCommandSender |
| [currentTick](current-tick.md) | [jvm]<br>val [currentTick](current-tick.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [dataPackManager](data-pack-manager.md) | [jvm]<br>val [dataPackManager](data-pack-manager.md): DatapackManager |
| [defaultGameMode](default-game-mode.md) | [jvm]<br>val [defaultGameMode](default-game-mode.md): GameMode |
| [generateStructures](generate-structures.md) | [jvm]<br>val [generateStructures](generate-structures.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hasWhitelist](has-whitelist.md) | [jvm]<br>var [hasWhitelist](has-whitelist.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [helpMap](help-map.md) | [jvm]<br>val [helpMap](help-map.md): HelpMap |
| [identityObject](identity-object.md) | [jvm]<br>val CommandSender.[identityObject](identity-object.md): [Identity](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;CommandSender&gt; |
| [identityObject](identity-object.md) | [jvm]<br>val Entity.[identityObject](identity-object.md): [Identity](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;Entity&gt; |
| [identityObject](identity-object.md) | [jvm]<br>val Player.[identityObject](identity-object.md): [Identity](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;Player&gt; |
| [identityObject](identity-object.md) | [jvm]<br>val OfflinePlayer.[identityObject](identity-object.md): [Identity](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;OfflinePlayer&gt; |
| [idleTimeout](idle-timeout.md) | [jvm]<br>var [idleTimeout](idle-timeout.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [ip](ip.md) | [jvm]<br>val [ip](ip.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [IPBans](-i-p-bans.md) | [jvm]<br>val [IPBans](-i-p-bans.md): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [isGlass](is-glass.md) | [jvm]<br>val Block.[isGlass](is-glass.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isHardcore](is-hardcore.md) | [jvm]<br>val [isHardcore](is-hardcore.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isPhysical](is-physical.md) | [jvm]<br>val Action.[isPhysical](is-physical.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isPrimaryThread](is-primary-thread.md) | [jvm]<br>val [isPrimaryThread](is-primary-thread.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isStopping](is-stopping.md) | [jvm]<br>val [isStopping](is-stopping.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [itemFactory](item-factory.md) | [jvm]<br>val [itemFactory](item-factory.md): ItemFactory |
| [legacyString](legacy-string.md) | [jvm]<br>val Component.[legacyString](legacy-string.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [legacyString](legacy-string.md) | [jvm]<br>val TextComponent.[legacyString](legacy-string.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [logger](logger.md) | [jvm]<br>val [logger](logger.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [marker](marker.md) | [jvm]<br>var Player.[marker](marker.md): [LocationBox](../de.jet.minecraft.tool.position/-location-box/index.md) |
| [maxPlayers](max-players.md) | [jvm]<br>var [maxPlayers](max-players.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [maxWorldSize](max-world-size.md) | [jvm]<br>val [maxWorldSize](max-world-size.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [messenger](messenger.md) | [jvm]<br>val [messenger](messenger.md): Messenger |
| [minecraftVersion](minecraft-version.md) | [jvm]<br>val [minecraftVersion](minecraft-version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [mobGoals](mob-goals.md) | [jvm]<br>val [mobGoals](mob-goals.md): MobGoals |
| [monsterSpawnLimit](monster-spawn-limit.md) | [jvm]<br>val [monsterSpawnLimit](monster-spawn-limit.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [motd](motd.md) | [jvm]<br>val [motd](motd.md): Component |
| [name](name.md) | [jvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [onlineMode](online-mode.md) | [jvm]<br>val [onlineMode](online-mode.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onlinePlayers](online-players.md) | [jvm]<br>val [onlinePlayers](online-players.md): [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;Player&gt; |
| [operators](operators.md) | [jvm]<br>val [operators](operators.md): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;OfflinePlayer&gt; |
| [permissionMessage](permission-message.md) | [jvm]<br>val [permissionMessage](permission-message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [pluginManager](plugin-manager.md) | [jvm]<br>val [pluginManager](plugin-manager.md): PluginManager |
| [port](port.md) | [jvm]<br>val [port](port.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [quickMaxHealth](quick-max-health.md) | [jvm]<br>var LivingEntity.[quickMaxHealth](quick-max-health.md): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [realAffectedBlock](real-affected-block.md) | [jvm]<br>val [PlayerInteractAtBlockEvent](../de.jet.minecraft.runtime.event.interact/-player-interact-at-block-event/index.md).[realAffectedBlock](real-affected-block.md): Location |
| [realAffectedBlock](real-affected-block.md) | [jvm]<br>val PlayerInteractEvent.[realAffectedBlock](real-affected-block.md): Location? |
| [recipeIterator](recipe-iterator.md) | [jvm]<br>val [recipeIterator](recipe-iterator.md): [Iterator](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterator/index.html)&lt;Recipe&gt; |
| [safeBoundingBox](safe-bounding-box.md) | [jvm]<br>val Block.[safeBoundingBox](safe-bounding-box.md): BoundingBox |
| [scheduler](scheduler.md) | [jvm]<br>val [scheduler](scheduler.md): BukkitScheduler |
| [scoreboardManager](scoreboard-manager.md) | [jvm]<br>val [scoreboardManager](scoreboard-manager.md): ScoreboardManager |
| [server](server.md) | [jvm]<br>var [server](server.md): Server |
| [serverIcon](server-icon.md) | [jvm]<br>val [serverIcon](server-icon.md): CachedServerIcon? |
| [servicesManager](services-manager.md) | [jvm]<br>val [servicesManager](services-manager.md): ServicesManager |
| [shutdownMessage](shutdown-message.md) | [jvm]<br>val [shutdownMessage](shutdown-message.md): Component? |
| [spawnRadius](spawn-radius.md) | [jvm]<br>var [spawnRadius](spawn-radius.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [spigot](spigot.md) | [jvm]<br>val [spigot](spigot.md): Server.Spigot |
| [suggestPlayerNamesWhenNullTabCompletions](suggest-player-names-when-null-tab-completions.md) | [jvm]<br>val [suggestPlayerNamesWhenNullTabCompletions](suggest-player-names-when-null-tab-completions.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [templateLocation](template-location.md) | [jvm]<br>val [templateLocation](template-location.md): Location |
| [ticksPerAmbientSpawns](ticks-per-ambient-spawns.md) | [jvm]<br>val [ticksPerAmbientSpawns](ticks-per-ambient-spawns.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [ticksPerAnimalSpawns](ticks-per-animal-spawns.md) | [jvm]<br>val [ticksPerAnimalSpawns](ticks-per-animal-spawns.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [ticksPerMonsterSpawns](ticks-per-monster-spawns.md) | [jvm]<br>val [ticksPerMonsterSpawns](ticks-per-monster-spawns.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [ticksPerWaterAmbientSpawns](ticks-per-water-ambient-spawns.md) | [jvm]<br>val [ticksPerWaterAmbientSpawns](ticks-per-water-ambient-spawns.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [ticksPerWaterSpawns](ticks-per-water-spawns.md) | [jvm]<br>val [ticksPerWaterSpawns](ticks-per-water-spawns.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [tickTimes](tick-times.md) | [jvm]<br>val [tickTimes](tick-times.md): [LongArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long-array/index.html) |
| [tps](tps.md) | [jvm]<br>val [tps](tps.md): [DoubleArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html) |
| [updateFolder](update-folder.md) | [jvm]<br>val [updateFolder](update-folder.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [updateFolderFile](update-folder-file.md) | [jvm]<br>val [updateFolderFile](update-folder-file.md): [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html) |
| [version](version.md) | [jvm]<br>val [version](version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [viewDistance](view-distance.md) | [jvm]<br>val [viewDistance](view-distance.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [warningState](warning-state.md) | [jvm]<br>val [warningState](warning-state.md): Warning.WarningState |
| [waterAmbientSpawnLimit](water-ambient-spawn-limit.md) | [jvm]<br>val [waterAmbientSpawnLimit](water-ambient-spawn-limit.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [waterAnimalSpawnLimit](water-animal-spawn-limit.md) | [jvm]<br>val [waterAnimalSpawnLimit](water-animal-spawn-limit.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [whitelistedPlayers](whitelisted-players.md) | [jvm]<br>val [whitelistedPlayers](whitelisted-players.md): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;OfflinePlayer&gt; |
| [worldContainer](world-container.md) | [jvm]<br>val [worldContainer](world-container.md): [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html) |
| [worlds](worlds.md) | [jvm]<br>val [worlds](worlds.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;World&gt; |
| [worldType](world-type.md) | [jvm]<br>val [worldType](world-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
