//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.app.component.essentials.world.tree](../../index.md)/[WorldRenderer](../index.md)/[RenderWorld](index.md)

# RenderWorld

[jvm]\
data class [RenderWorld](index.md)(displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), address: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;, labels: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, archived: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), visitors: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) : [WorldRenderer.RenderObject](../-render-object/index.md)

## Functions

| Name | Summary |
|---|---|
| [renderArchiveStatus](../-render-object/render-archive-status.md) | [jvm]<br>open fun [renderArchiveStatus](../-render-object/render-archive-status.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [renderLabels](../-render-object/render-labels.md) | [jvm]<br>open fun [renderLabels](../-render-object/render-labels.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [address](address.md) | [jvm]<br>open override val [address](address.md): [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt; |
| [addressString](../-render-folder/index.md#1659247637%2FProperties%2F-726029290) | [jvm]<br>open val [addressString](../-render-folder/index.md#1659247637%2FProperties%2F-726029290): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [archived](archived.md) | [jvm]<br>open override val [archived](archived.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [displayName](display-name.md) | [jvm]<br>open override val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt; |
| [labels](labels.md) | [jvm]<br>open override val [labels](labels.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [visitors](visitors.md) | [jvm]<br>val [visitors](visitors.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
