@file:Suppress("unused", "DEPRECATION")

package de.moltenKt.paper.extension.paper

import com.destroystokyo.paper.entity.ai.MobGoals
import de.moltenKt.jvm.extension.classType.UUID
import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.unfold.extension.asComponent
import de.moltenKt.unfold.extension.asString
import io.papermc.paper.datapack.DatapackManager
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.block.data.BlockData
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.boss.KeyedBossBar
import org.bukkit.command.CommandMap
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.help.HelpMap
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemFactory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.ServicesManager
import org.bukkit.plugin.messaging.Messenger
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scoreboard.ScoreboardManager
import org.bukkit.util.CachedServerIcon
import java.awt.image.BufferedImage
import java.io.File
import java.util.function.Consumer
import java.util.logging.Logger

val consoleSender: ConsoleCommandSender
	get() = Bukkit.getConsoleSender()

val onlinePlayers: Collection<Player>
	get() = Bukkit.getOnlinePlayers()

val offlinePlayers: List<OfflinePlayer>
	get() = Bukkit.getOfflinePlayers().toList()

fun getPlayer(playerName: String) = Bukkit.getPlayer(playerName)

fun getPlayer(uniqueIdentity: UUID) = Bukkit.getPlayer(uniqueIdentity)

fun getPlayer(identity: Identifiable<out OfflinePlayer>) = getPlayer(UUID.fromString(identity.identity))

@Suppress("DEPRECATION")
fun getOfflinePlayer(playerName: String) = Bukkit.getOfflinePlayer(playerName)

fun getOfflinePlayer(uniqueIdentity: UUID) = Bukkit.getOfflinePlayer(uniqueIdentity)

fun getOfflinePlayer(identity: Identifiable<out OfflinePlayer>) = getOfflinePlayer(UUID.fromString(identity.identity))

fun Plugin.createKey(key: String) = NamespacedKey(this, key)

val templateLocation: Location
	get() = Bukkit.getWorlds().first().spawnLocation

fun addRecipe(recipe: Recipe) = Bukkit.addRecipe(recipe)

val advancementIterator: Iterator<Advancement>
	get() = Bukkit.advancementIterator()

fun banIP(address: String) = Bukkit.banIP(address)

fun broadcast(message: Component, permission: String) = Bukkit.broadcast(message, permission)

fun broadcast(message: String, permission: String) = broadcast(message.asComponent, permission)

fun broadcastMessage(message: String) = Bukkit.broadcast(message.asComponent, "")

fun clearRecipes() = Bukkit.clearRecipes()

fun createBlockData(data: String) = Bukkit.createBlockData(data)

fun createBlockData(material: Material) = Bukkit.createBlockData(material)

fun createBlockData(material: Material, consumer: Consumer<BlockData>?) = Bukkit.createBlockData(material, consumer)

fun createBlockData(material: Material?, data: String?) = Bukkit.createBlockData(material, data)

fun createBossBar(key: NamespacedKey, title: String?, color: BarColor, style: BarStyle, vararg flags: BarFlag) = Bukkit.createBossBar(key, title, color, style, *flags)

fun createBossBar(title: String?, color: BarColor, style: BarStyle, vararg flags: BarFlag) = Bukkit.createBossBar(title, color, style, *flags)

fun createChunkData(world: World) = Bukkit.createChunkData(world)

fun createExplorerMap(world: World, location: Location, structureType: StructureType) = Bukkit.createExplorerMap(world, location, structureType)

fun createExplorerMap(world: World, location: Location, structureType: StructureType, radius: Int, findUnexplored: Boolean) = Bukkit.createExplorerMap(world, location, structureType, radius, findUnexplored)

fun createInventory(owner: InventoryHolder?, size: Int) = Bukkit.createInventory(owner, size)

fun createInventory(owner: InventoryHolder?, size: Int, title: String) = createInventory(owner, size, title.asComponent)

fun createInventory(owner: InventoryHolder?, size: Int, title: Component) = Bukkit.createInventory(owner, size, title)

fun createInventory(owner: InventoryHolder?, type: InventoryType) = Bukkit.createInventory(owner, type)

fun createInventory(owner: InventoryHolder?, type: InventoryType, title: String) = createInventory(owner, type, title.asComponent)

fun createInventory(owner: InventoryHolder?, type: InventoryType, title: Component) = Bukkit.createInventory(owner, type, title)

fun createMap(world: World) = Bukkit.createMap(world)

fun createMerchant(title: String) = createMerchant(title.asComponent)

fun createMerchant(title: Component) = Bukkit.createMerchant(title)

fun createProfile(name: String) = Bukkit.createProfile(name)

fun createProfile(uuid: UUID) = Bukkit.createProfile(uuid)

fun createProfile(uuid: UUID?, name: String?) = Bukkit.createProfile(uuid, name)


fun createWorld(creator: WorldCreator) = Bukkit.createWorld(creator)

fun dispatchCommand(executor: InterchangeExecutor, commandLine: String) = Bukkit.dispatchCommand(executor, commandLine)

fun getAdvancement(key: NamespacedKey) = Bukkit.getAdvancement(key)

val allowEnd: Boolean
	get() = Bukkit.getAllowEnd()

val allowFlight: Boolean
	get() = Bukkit.getAllowFlight()

val allowNether: Boolean
	get() = Bukkit.getAllowNether()

val ambientSpawnLimit: Int
	get() = Bukkit.getAmbientSpawnLimit()

val animalSpawnLimit: Int
	get() = Bukkit.getAnimalSpawnLimit()

val averageTickTime: Double
	get() = Bukkit.getAverageTickTime()

fun getBanList(type: BanList.Type) = Bukkit.getBanList(type)

val bannedPlayers: Set<OfflinePlayer>
	get() = Bukkit.getBannedPlayers()

fun getBossBar(key: NamespacedKey) = Bukkit.getBossBar(key)

val bossBars: Iterator<KeyedBossBar>
	get() = Bukkit.getBossBars()

val bukkitVersion: String
	get() = Bukkit.getBukkitVersion()

val commandAliases: Map<String, Array<String>>
	get() = Bukkit.getCommandAliases()

val commandMap: CommandMap
	get() = Bukkit.getCommandMap()

val connectionThrottle: Long
	get() = Bukkit.getConnectionThrottle()

val currentTick: Int
	get() = Bukkit.getCurrentTick()

val dataPackManager: DatapackManager
	get() = Bukkit.getDatapackManager()

val defaultGameMode: GameMode
	get() = Bukkit.getDefaultGameMode()

fun getEntity(uuid: UUID) = Bukkit.getEntity(uuid)

val generateStructures: Boolean
	get() = Bukkit.getGenerateStructures()

val helpMap: HelpMap
	get() = Bukkit.getHelpMap()

var idleTimeout: Int
	get() = Bukkit.getIdleTimeout()
	set(value) = Bukkit.setIdleTimeout(value)

val ip: String
	get() = Bukkit.getIp()

val IPBans: Set<String>
	get() = Bukkit.getIPBans()

val itemFactory: ItemFactory
	get() = Bukkit.getItemFactory()

@get:Deprecated(message = "Internally, explicit section-logs from Logging is used!",
	replaceWith = ReplaceWith("Bukkit.getLogger()", "org.bukkit.Bukkit")
)
val logger: Logger
	get() = Bukkit.getLogger()

fun getLootTable(key: NamespacedKey) = Bukkit.getLootTable(key)

var maxPlayers: Int
	get() = Bukkit.getMaxPlayers()
	set(value) = Bukkit.setMaxPlayers(value)

val maxWorldSize: Int
	get() = Bukkit.getMaxWorldSize()

val messenger: Messenger
	get() = Bukkit.getMessenger()

val minecraftVersion: String
	get() = Bukkit.getMinecraftVersion()

val mobGoals: MobGoals
	get() = Bukkit.getMobGoals()

val monsterSpawnLimit: Int
	get() = Bukkit.getMonsterSpawnLimit()

fun getMessageOfTheDay() = motd.asString

val motd: Component
	get() = Bukkit.motd()

val name: String
	get() = Bukkit.getName()

val onlineMode: Boolean
	get() = Bukkit.getOnlineMode()

val operators: Set<OfflinePlayer>
	get() = Bukkit.getOperators()

val permissionMessage: String
	get() = Bukkit.getPermissionMessage()

fun getPluginCommand(name: String) = Bukkit.getPluginCommand(name)

val pluginManager: PluginManager
	get() = Bukkit.getPluginManager()

val port: Int
	get() = Bukkit.getPort()

fun getRecipe(recipeKey: NamespacedKey) = Bukkit.getRecipe(recipeKey)

fun getRecipesFor(result: ItemStack): List<Recipe> = Bukkit.getRecipesFor(result)

val scheduler: BukkitScheduler
	get() = Bukkit.getScheduler()

val scoreboardManager: ScoreboardManager
	get() = Bukkit.getScoreboardManager()

var server: Server
	get() = Bukkit.getServer()
	set(value) = Bukkit.setServer(value)

val serverIcon: CachedServerIcon?
	get() = Bukkit.getServerIcon()

val servicesManager: ServicesManager
	get() = Bukkit.getServicesManager()

fun getShutdownMessage() = shutdownMessage?.asString

val shutdownMessage: Component?
	get() = Bukkit.shutdownMessage()

var spawnRadius: Int
	get() = Bukkit.getSpawnRadius()
	set(value) = Bukkit.setSpawnRadius(value)

val ticksPerAmbientSpawns: Int
	get() = Bukkit.getTicksPerAmbientSpawns()

val ticksPerAnimalSpawns: Int
	get() = Bukkit.getTicksPerAnimalSpawns()

val ticksPerMonsterSpawns: Int
	get() = Bukkit.getTicksPerMonsterSpawns()

val ticksPerWaterAmbientSpawns: Int
	get() = Bukkit.getTicksPerWaterAmbientSpawns()

val ticksPerWaterSpawns: Int
	get() = Bukkit.getTicksPerWaterSpawns()

val tickTimes: LongArray
	get() = Bukkit.getTickTimes()

val tps: DoubleArray
	get() = Bukkit.getTPS()

val updateFolder: String
	get() = Bukkit.getUpdateFolder()

val updateFolderFile: File
	get() = Bukkit.getUpdateFolderFile()

val version: String
	get() = Bukkit.getVersion()

val viewDistance: Int
	get() = Bukkit.getViewDistance()

val warningState: Warning.WarningState
	get() = Bukkit.getWarningState()

val waterAmbientSpawnLimit: Int
	get() = Bukkit.getWaterAmbientSpawnLimit()

val waterAnimalSpawnLimit: Int
	get() = Bukkit.getWaterAnimalSpawnLimit()

val whitelistedPlayers: Set<OfflinePlayer>
	get() = Bukkit.getWhitelistedPlayers()

fun getWorld(name: String) = Bukkit.getWorld(name)

fun getWorld(uid: UUID) = Bukkit.getWorld(uid)

fun getWorld(worldKey: NamespacedKey) = Bukkit.getWorld(worldKey)

val worldContainer: File
	get() = Bukkit.getWorldContainer()

val worlds: List<World>
	get() = Bukkit.getWorlds()

val worldType: String
	get() = Bukkit.getWorldType()

var hasWhitelist: Boolean
	get() = Bukkit.hasWhitelist()
	set(value) = Bukkit.setWhitelist(value)

val isHardcore: Boolean
	get() = Bukkit.isHardcore()

val isPrimaryThread: Boolean
	get() = Bukkit.isPrimaryThread()

val isStopping: Boolean
	get() = Bukkit.isStopping()

fun loadServerIcon(image: BufferedImage) = Bukkit.loadServerIcon(image)

fun loadServerIcon(file: File) = Bukkit.loadServerIcon(file)

fun matchPlayer(name: String): List<Player> = Bukkit.matchPlayer(name)

val recipeIterator: Iterator<Recipe>
	get() = Bukkit.recipeIterator()

fun reload() = Bukkit.reload()

fun reloadCommandAliases() = Bukkit.reloadCommandAliases()

fun reloadData() = Bukkit.reloadData()

fun reloadPermissions() = Bukkit.reloadPermissions()

fun reloadWhitelist() = Bukkit.reloadWhitelist()

fun removeBossBar(key: NamespacedKey) = Bukkit.removeBossBar(key)

fun removeRecipe(key: NamespacedKey) = Bukkit.removeRecipe(key)

fun resetRecipes() = Bukkit.resetRecipes()

fun savePlayers() = Bukkit.savePlayers()

fun selectEntities(executor: InterchangeExecutor, selector: String): List<Entity> = Bukkit.selectEntities(executor, selector)

fun setDefaultGameMode(mode: GameMode) = Bukkit.setDefaultGameMode(mode)

fun shutdown() = Bukkit.shutdown()

val spigot: Server.Spigot
	get() = Bukkit.spigot()

val suggestPlayerNamesWhenNullTabCompletions: Boolean
	get() = Bukkit.suggestPlayerNamesWhenNullTabCompletions()

fun unbanIP(address: String) = Bukkit.unbanIP(address)

fun unloadWorld(name: String, save: Boolean) = Bukkit.unloadWorld(name, save)

fun unloadWorld(world: World, save: Boolean) = Bukkit.unloadWorld(world, save)