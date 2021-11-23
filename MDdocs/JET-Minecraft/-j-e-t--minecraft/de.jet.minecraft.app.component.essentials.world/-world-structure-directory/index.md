//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.app.component.essentials.world](../index.md)/[WorldStructureDirectory](index.md)

# WorldStructureDirectory

[jvm]\
data class [WorldStructureDirectory](index.md)(displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), path: [Address](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt;, labels: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, archived: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [WorldStructureObject](../-world-structure-object/index.md)

## Functions

| Name | Summary |
|---|---|
| [computeParent](index.md#972478507%2FFunctions%2F-726029290) | [jvm]<br>open fun &lt;[T](index.md#972478507%2FFunctions%2F-726029290) : [DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt; [computeParent](index.md#972478507%2FFunctions%2F-726029290)(holder: [DataStructureHolder](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-holder/index.md)&lt;[T](index.md#972478507%2FFunctions%2F-726029290)&gt;, onlyBase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [T](index.md#972478507%2FFunctions%2F-726029290) |
| [renderArchiveStatus](../-world-structure-object/render-archive-status.md) | [jvm]<br>open fun [renderArchiveStatus](../-world-structure-object/render-archive-status.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [renderLabels](../-world-structure-object/render-labels.md) | [jvm]<br>open fun [renderLabels](../-world-structure-object/render-labels.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [address](index.md#2128915704%2FProperties%2F-726029290) | [jvm]<br>open override val [address](index.md#2128915704%2FProperties%2F-726029290): [Address](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt; |
| [addressString](../../de.jet.minecraft.app.component.essentials.world.tree/-world-renderer/-render-folder/index.md#1659247637%2FProperties%2F-726029290) | [jvm]<br>open val [addressString](../../de.jet.minecraft.app.component.essentials.world.tree/-world-renderer/-render-folder/index.md#1659247637%2FProperties%2F-726029290): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [archived](archived.md) | [jvm]<br>open override val [archived](archived.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [displayName](display-name.md) | [jvm]<br>open override val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [existenceAssertion](index.md#1961974457%2FProperties%2F-726029290) | [jvm]<br>open val [existenceAssertion](index.md#1961974457%2FProperties%2F-726029290): [DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md).() -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt; |
| [labels](labels.md) | [jvm]<br>open override val [labels](labels.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [path](path.md) | [jvm]<br>open override val [path](path.md): [Address](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[DataStructureItem](../../../../JET-Native/-j-e-t--native/de.jet.library.structure/-data-structure-item/index.md)&gt; |
| [pathParts](index.md#600539473%2FProperties%2F-726029290) | [jvm]<br>open val [pathParts](index.md#600539473%2FProperties%2F-726029290): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
