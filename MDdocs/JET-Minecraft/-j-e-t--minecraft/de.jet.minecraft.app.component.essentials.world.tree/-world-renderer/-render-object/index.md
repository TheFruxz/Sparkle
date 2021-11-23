//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.app.component.essentials.world.tree](../../index.md)/[WorldRenderer](../index.md)/[RenderObject](index.md)

# RenderObject

[jvm]\
interface [RenderObject](index.md) : [Identifiable](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[WorldRenderer.RenderObject](index.md)&gt; , [Addressable](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-addressable/index.md)&lt;[WorldRenderer.RenderObject](index.md)&gt;

## Functions

| Name | Summary |
|---|---|
| [renderArchiveStatus](render-archive-status.md) | [jvm]<br>open fun [renderArchiveStatus](render-archive-status.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [renderLabels](render-labels.md) | [jvm]<br>open fun [renderLabels](render-labels.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [address](index.md#750322150%2FProperties%2F-726029290) | [jvm]<br>abstract val [address](index.md#750322150%2FProperties%2F-726029290): [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](index.md)&gt; |
| [addressString](../-render-folder/index.md#1659247637%2FProperties%2F-726029290) | [jvm]<br>open val [addressString](../-render-folder/index.md#1659247637%2FProperties%2F-726029290): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [archived](archived.md) | [jvm]<br>abstract val [archived](archived.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [displayName](display-name.md) | [jvm]<br>abstract val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identity](../../../de.jet.minecraft.tool.display.ui/-u-i/index.md#2001442881%2FProperties%2F-726029290) | [jvm]<br>abstract val [identity](../../../de.jet.minecraft.tool.display.ui/-u-i/index.md#2001442881%2FProperties%2F-726029290): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[WorldRenderer.RenderObject](index.md)&gt; |
| [labels](labels.md) | [jvm]<br>abstract val [labels](labels.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

## Inheritors

| Name |
|---|
| [RenderWorld](../-render-world/index.md) |
| [RenderFolder](../-render-folder/index.md) |
