//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.app.component.essentials.world](../index.md)/[WorldStructureObject](index.md)

# WorldStructureObject

[jvm]\
interface [WorldStructureObject](index.md) : [DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)

## Functions

| Name | Summary |
|---|---|
| [computeParent](../-world-structure-directory/index.md#972478507%2FFunctions%2F-726029290) | [jvm]<br>open fun &lt;[T](../-world-structure-directory/index.md#972478507%2FFunctions%2F-726029290) : [DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt; [computeParent](../-world-structure-directory/index.md#972478507%2FFunctions%2F-726029290)(holder: [DataStructureHolder](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-holder/index.md)&lt;[T](../-world-structure-directory/index.md#972478507%2FFunctions%2F-726029290)&gt;, onlyBase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [T](../-world-structure-directory/index.md#972478507%2FFunctions%2F-726029290) |
| [renderArchiveStatus](render-archive-status.md) | [jvm]<br>open fun [renderArchiveStatus](render-archive-status.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [renderLabels](render-labels.md) | [jvm]<br>open fun [renderLabels](render-labels.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [address](../-world-structure-directory/index.md#2128915704%2FProperties%2F-726029290) | [jvm]<br>open override val [address](../-world-structure-directory/index.md#2128915704%2FProperties%2F-726029290): [Address](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt; |
| [addressString](../../de.jet.minecraft.app.component.essentials.world.tree/-world-renderer/-render-folder/index.md#1659247637%2FProperties%2F-726029290) | [jvm]<br>open val [addressString](../../de.jet.minecraft.app.component.essentials.world.tree/-world-renderer/-render-folder/index.md#1659247637%2FProperties%2F-726029290): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [archived](archived.md) | [jvm]<br>abstract val [archived](archived.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [displayName](display-name.md) | [jvm]<br>abstract val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [existenceAssertion](../-world-structure-directory/index.md#1961974457%2FProperties%2F-726029290) | [jvm]<br>open val [existenceAssertion](../-world-structure-directory/index.md#1961974457%2FProperties%2F-726029290): [DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md).() -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [identity](../../de.jet.minecraft.tool.display.ui/-u-i/index.md#2001442881%2FProperties%2F-726029290) | [jvm]<br>abstract val [identity](../../de.jet.minecraft.tool.display.ui/-u-i/index.md#2001442881%2FProperties%2F-726029290): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt; |
| [labels](labels.md) | [jvm]<br>abstract val [labels](labels.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [path](index.md#257445609%2FProperties%2F-726029290) | [jvm]<br>abstract val [path](index.md#257445609%2FProperties%2F-726029290): [Address](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt; |
| [pathParts](../-world-structure-directory/index.md#600539473%2FProperties%2F-726029290) | [jvm]<br>open val [pathParts](../-world-structure-directory/index.md#600539473%2FProperties%2F-726029290): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Inheritors

| Name |
|---|
| [WorldStructureWorld](../-world-structure-world/index.md) |
| [WorldStructureDirectory](../-world-structure-directory/index.md) |
