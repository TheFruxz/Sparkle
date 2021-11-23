//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.tool.data](../../index.md)/[DataTransformer](../index.md)/[Companion](index.md)

# Companion

[jvm]\
object [Companion](index.md)

## Functions

| Name | Summary |
|---|---|
| [empty](empty.md) | [jvm]<br>fun &lt;[BOTH](empty.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [empty](empty.md)(): [DataTransformer](../index.md)&lt;[BOTH](empty.md), [BOTH](empty.md)&gt; |
| [json](json.md) | [jvm]<br>inline fun &lt;[T](json.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [json](json.md)(): [DataTransformer](../index.md)&lt;[T](json.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [jsonItem](json-item.md) | [jvm]<br>fun [jsonItem](json-item.md)(): [DataTransformer](../index.md)&lt;[Item](../../../de.jet.minecraft.tool.display.item/-item/index.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [setCollection](set-collection.md) | [jvm]<br>inline fun &lt;[SET](set-collection.md)&gt; [setCollection](set-collection.md)(): [DataTransformer](../index.md)&lt;[Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[SET](set-collection.md)&gt;, [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[SET](set-collection.md)&gt;&gt; |
| [simpleColorCode](simple-color-code.md) | [jvm]<br>fun [simpleColorCode](simple-color-code.md)(): [DataTransformer](../index.md)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [simpleLocationArrayBukkit](simple-location-array-bukkit.md) | [jvm]<br>fun [simpleLocationArrayBukkit](simple-location-array-bukkit.md)(): [DataTransformer](../index.md)&lt;[Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;Location&gt;, [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[SimpleLocation](../../../de.jet.minecraft.tool.display.world/-simple-location/index.md)&gt;&gt; |
| [simpleLocationBukkit](simple-location-bukkit.md) | [jvm]<br>fun [simpleLocationBukkit](simple-location-bukkit.md)(): [DataTransformer](../index.md)&lt;Location, [SimpleLocation](../../../de.jet.minecraft.tool.display.world/-simple-location/index.md)&gt; |
| [simpleLocationListBukkit](simple-location-list-bukkit.md) | [jvm]<br>fun [simpleLocationListBukkit](simple-location-list-bukkit.md)(): [DataTransformer](../index.md)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Location&gt;, [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SimpleLocation](../../../de.jet.minecraft.tool.display.world/-simple-location/index.md)&gt;&gt; |
