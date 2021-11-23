//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.app](../index.md)/[App](index.md)

# App

[jvm]\
abstract class [App](index.md) : JavaPlugin, [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[App](index.md)&gt; 

# App (abstract)

##  Info

This class structures the main class of every app; It defines variables, management-containers, language and the functions of the whole app.

##  Use

You should use this Class as the base-plate of your app/plugin instead of the JavaPlugin, because this class extends out of the JavaPlugin class. This class is way better, because it has more extending functionality over the classic JavaPlugin!

##  Base

The [App](index.md)-Class is abstract, so you can use it for your own class, to be the base of your own app.

This class uses these base-plates:

- 
   JavaPlugin: Every app is a whole real Minecraft-Server-Plugin, but it is heavely extended. But you can touch the original Bukkit/ Paper API, if you want to do so.
- 
   [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)<[App](index.md)>: Every app is identifiable thru is unique custom set [appIdentity](app-identity.md) property. ([appIdentity](app-identity.md) = [identity](identity.md))

#### Author

Fruxz (@TheFruxz)

#### Since

1.0-BETA-2 (preview)

## See also

jvm

| | |
|---|---|
| [de.jet.minecraft.app.JetApp](../../de.jet.minecraft.app/-jet-app/index.md) |  |

## Constructors

| | |
|---|---|
| [App](-app.md) | [jvm]<br>fun [App](-app.md)()<br>abstract |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [add](add.md) | [jvm]<br>fun [add](add.md)(eventListener: [EventListener](../../de.jet.minecraft.structure.app.event/-event-listener/index.md))<br>fun [add](add.md)(component: [Component](../../de.jet.minecraft.structure.component/-component/index.md))<br>[jvm]<br>fun [add](add.md)(interchange: [Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md))<br>Add Interchange |
| [bye](bye.md) | [jvm]<br>abstract fun [bye](bye.md)() |
| [equals](index.md#-956268231%2FFunctions%2F-726029290) | [jvm]<br>operator override fun [equals](index.md#-956268231%2FFunctions%2F-726029290)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getClassLoader](index.md#-1264005818%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>fun [getClassLoader](index.md#-1264005818%2FFunctions%2F-726029290)(): @NotNull[ClassLoader](https://docs.oracle.com/javase/8/docs/api/java/lang/ClassLoader.html) |
| [getCommand](index.md#-1722902754%2FFunctions%2F-726029290) | [jvm]<br>@Nullable<br>open fun [getCommand](index.md#-1722902754%2FFunctions%2F-726029290)(@NotNullp0: @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @NullablePluginCommand? |
| [getConfig](index.md#969075559%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open override fun [getConfig](index.md#969075559%2FFunctions%2F-726029290)(): @NotNullFileConfiguration |
| [getDataFolder](index.md#-1341384527%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>override fun [getDataFolder](index.md#-1341384527%2FFunctions%2F-726029290)(): @NotNull[File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html) |
| [getDefaultBiomeProvider](index.md#-1131500269%2FFunctions%2F-726029290) | [jvm]<br>@Nullable<br>open override fun [getDefaultBiomeProvider](index.md#-1131500269%2FFunctions%2F-726029290)(@NotNullp0: @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Nullablep1: @Nullable[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): @NullableBiomeProvider? |
| [getDefaultWorldGenerator](index.md#1284217961%2FFunctions%2F-726029290) | [jvm]<br>@Nullable<br>open override fun [getDefaultWorldGenerator](index.md#1284217961%2FFunctions%2F-726029290)(@NotNullp0: @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @Nullablep1: @Nullable[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): @NullableChunkGenerator? |
| [getDescription](index.md#-1721867755%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>override fun [getDescription](index.md#-1721867755%2FFunctions%2F-726029290)(): @NotNullPluginDescriptionFile |
| [getFile](index.md#1854065005%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open fun [getFile](index.md#1854065005%2FFunctions%2F-726029290)(): @NotNull[File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html) |
| [getLog4JLogger](index.md#-360014269%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open fun [getLog4JLogger](index.md#-360014269%2FFunctions%2F-726029290)(): @NotNullLogger |
| [getLogger](index.md#-190444327%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open override fun [getLogger](index.md#-190444327%2FFunctions%2F-726029290)(): @NotNull[Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [getName](index.md#-1617404175%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>override fun [getName](index.md#-1617404175%2FFunctions%2F-726029290)(): @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getPluginLoader](index.md#1789185283%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>override fun [getPluginLoader](index.md#1789185283%2FFunctions%2F-726029290)(): @NotNullPluginLoader |
| [getResource](index.md#1780025289%2FFunctions%2F-726029290) | [jvm]<br>@Nullable<br>open override fun [getResource](index.md#1780025289%2FFunctions%2F-726029290)(@NotNullp0: @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @Nullable[InputStream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html)? |
| [getServer](index.md#1660097158%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>override fun [getServer](index.md#1660097158%2FFunctions%2F-726029290)(): @NotNullServer |
| [getSLF4JLogger](index.md#663398778%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open fun [getSLF4JLogger](index.md#663398778%2FFunctions%2F-726029290)(): @NotNullLogger |
| [getTextResource](index.md#1065257046%2FFunctions%2F-726029290) | [jvm]<br>@Nullable<br>fun [getTextResource](index.md#1065257046%2FFunctions%2F-726029290)(@NotNullp0: @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): @Nullable[Reader](https://docs.oracle.com/javase/8/docs/api/java/io/Reader.html)? |
| [hashCode](index.md#-1047022707%2FFunctions%2F-726029290) | [jvm]<br>override fun [hashCode](index.md#-1047022707%2FFunctions%2F-726029290)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [hello](hello.md) | [jvm]<br>abstract fun [hello](hello.md)() |
| [init](index.md#263625417%2FFunctions%2F-726029290) | [jvm]<br>fun [init](index.md#263625417%2FFunctions%2F-726029290)(@NotNullp0: @NotNullPluginLoader, @NotNullp1: @NotNullServer, @NotNullp2: @NotNullPluginDescriptionFile, @NotNullp3: @NotNull[File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html), @NotNullp4: @NotNull[File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html), @NotNullp5: @NotNull[ClassLoader](https://docs.oracle.com/javase/8/docs/api/java/lang/ClassLoader.html)) |
| [isEnabled](index.md#-655197240%2FFunctions%2F-726029290) | [jvm]<br>override fun [isEnabled](index.md#-655197240%2FFunctions%2F-726029290)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isNaggable](index.md#-1116561404%2FFunctions%2F-726029290) | [jvm]<br>override fun [isNaggable](index.md#-1116561404%2FFunctions%2F-726029290)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onCommand](index.md#1148868763%2FFunctions%2F-726029290) | [jvm]<br>open override fun [onCommand](index.md#1148868763%2FFunctions%2F-726029290)(@NotNullp0: @NotNullCommandSender, @NotNullp1: @NotNullCommand, @NotNullp2: @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @NotNullp3: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;@NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onDisable](on-disable.md) | [jvm]<br>open override fun [onDisable](on-disable.md)() |
| [onEnable](on-enable.md) | [jvm]<br>open override fun [onEnable](on-enable.md)() |
| [onLoad](on-load.md) | [jvm]<br>open override fun [onLoad](on-load.md)() |
| [onTabComplete](index.md#193072766%2FFunctions%2F-726029290) | [jvm]<br>@Nullable<br>open override fun [onTabComplete](index.md#193072766%2FFunctions%2F-726029290)(@NotNullp0: @NotNullCommandSender, @NotNullp1: @NotNullCommand, @NotNullp2: @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), @NotNullp3: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;@NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): @Nullable[MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? |
| [register](register.md) | [jvm]<br>abstract fun [register](register.md)()<br>fun [register](register.md)(service: [Service](../../de.jet.minecraft.structure.service/-service/index.md))<br>fun [register](register.md)(serializable: [Class](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)&lt;out ConfigurationSerializable&gt;)<br>fun [register](register.md)(serializable: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;out ConfigurationSerializable&gt;) |
| [reloadConfig](index.md#-1959235648%2FFunctions%2F-726029290) | [jvm]<br>open override fun [reloadConfig](index.md#-1959235648%2FFunctions%2F-726029290)() |
| [remove](remove.md) | [jvm]<br>fun [remove](remove.md)(eventListener: [EventListener](../../de.jet.minecraft.structure.app.event/-event-listener/index.md)) |
| [replace](replace.md) | [jvm]<br>fun [replace](replace.md)(identity: [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md)&gt;, environment: [Interchange](../../de.jet.minecraft.structure.command/-interchange/index.md))<br>Interchange must not be initialized before executing this! |
| [reset](reset.md) | [jvm]<br>fun [reset](reset.md)(service: [Service](../../de.jet.minecraft.structure.service/-service/index.md)) |
| [restart](restart.md) | [jvm]<br>fun [restart](restart.md)(service: [Service](../../de.jet.minecraft.structure.service/-service/index.md)) |
| [saveConfig](index.md#-1998581028%2FFunctions%2F-726029290) | [jvm]<br>open override fun [saveConfig](index.md#-1998581028%2FFunctions%2F-726029290)() |
| [saveDefaultConfig](index.md#-1312772871%2FFunctions%2F-726029290) | [jvm]<br>open override fun [saveDefaultConfig](index.md#-1312772871%2FFunctions%2F-726029290)() |
| [saveResource](index.md#-1107407536%2FFunctions%2F-726029290) | [jvm]<br>open override fun [saveResource](index.md#-1107407536%2FFunctions%2F-726029290)(@NotNullp0: @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), p1: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setEnabled](index.md#496945089%2FFunctions%2F-726029290) | [jvm]<br>fun [setEnabled](index.md#496945089%2FFunctions%2F-726029290)(p0: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setNaggable](index.md#67101717%2FFunctions%2F-726029290) | [jvm]<br>override fun [setNaggable](index.md#67101717%2FFunctions%2F-726029290)(p0: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [start](start.md) | [jvm]<br>fun [start](start.md)(componentIdentity: [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Component](../../de.jet.minecraft.structure.component/-component/index.md)&gt;)<br>fun [start](start.md)(service: [Service](../../de.jet.minecraft.structure.service/-service/index.md)) |
| [stop](stop.md) | [jvm]<br>fun [stop](stop.md)(service: [Service](../../de.jet.minecraft.structure.service/-service/index.md))<br>fun [stop](stop.md)(componentIdentity: [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Component](../../de.jet.minecraft.structure.component/-component/index.md)&gt;, unregisterComponent: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [toString](index.md#1897086895%2FFunctions%2F-726029290) | [jvm]<br>@NotNull<br>open override fun [toString](index.md#1897086895%2FFunctions%2F-726029290)(): @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [unregister](unregister.md) | [jvm]<br>fun [unregister](unregister.md)(componentIdentity: [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Component](../../de.jet.minecraft.structure.component/-component/index.md)&gt;)<br>fun [unregister](unregister.md)(service: [Service](../../de.jet.minecraft.structure.service/-service/index.md))<br>fun [unregister](unregister.md)(serializable: [Class](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)&lt;out ConfigurationSerializable&gt;)<br>fun [unregister](unregister.md)(serializable: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;out ConfigurationSerializable&gt;) |

## Properties

| Name | Summary |
|---|---|
| [appCache](app-cache.md) | [jvm]<br>abstract val [appCache](app-cache.md): [AppCache](../-app-cache/index.md)<br>The cache of the application |
| [appIdentity](app-identity.md) | [jvm]<br>abstract val [appIdentity](app-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [appLabel](app-label.md) | [jvm]<br>abstract val [appLabel](app-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>This value defines the display-name of this app, which will be displayed in app-lists and information with the app included. Can be duplicated, but try to avoid duplicated display names with other apps! |
| [appRegistrationFile](app-registration-file.md) | [jvm]<br>var [appRegistrationFile](app-registration-file.md): YamlConfiguration |
| [companion](companion.md) | [jvm]<br>abstract val [companion](companion.md): [AppCompanion](../-app-companion/index.md)&lt;*&gt;<br>This value defines the Companion-Object of this class, which holds the instance variable *(lateinit recommended)* and other app-related special variables & functions. |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[App](index.md)&gt; |
| [languageSpeaker](language-speaker.md) | [jvm]<br>val [languageSpeaker](language-speaker.md): [LanguageSpeaker](../../de.jet.minecraft.runtime.app/-language-speaker/index.md) |
| [log](log.md) | [jvm]<br>val [log](log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [logger](index.md#625757323%2FProperties%2F-726029290) | [jvm]<br>val [logger](index.md#625757323%2FProperties%2F-726029290): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [runStatus](run-status.md) | [jvm]<br>var [runStatus](run-status.md): [RunStatus](../../de.jet.minecraft.runtime.app/-run-status/index.md)<br>The current status of app-runtime |

## Inheritors

| Name |
|---|
| [JetApp](../../de.jet.minecraft.app/-jet-app/index.md) |
