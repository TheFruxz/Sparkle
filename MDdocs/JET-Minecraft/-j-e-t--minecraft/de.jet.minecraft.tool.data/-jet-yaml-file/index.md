//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.data](../index.md)/[JetYamlFile](index.md)

# JetYamlFile

[jvm]\
interface [JetYamlFile](index.md) : [JetFile](../-jet-file/index.md)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [contains](../-jet-file/contains.md) | [jvm]<br>abstract fun [contains](../-jet-file/contains.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [get](../-jet-file/get.md) | [jvm]<br>abstract operator fun &lt;[T](../-jet-file/get.md)&gt; [get](../-jet-file/get.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](../-jet-file/get.md)? |
| [load](../-jet-file/load.md) | [jvm]<br>abstract fun [load](../-jet-file/load.md)() |
| [save](../-jet-file/save.md) | [jvm]<br>abstract fun [save](../-jet-file/save.md)() |
| [set](../-jet-file/set.md) | [jvm]<br>abstract operator fun &lt;[T](../-jet-file/set.md)&gt; [set](../-jet-file/set.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [T](../-jet-file/set.md)) |

## Properties

| Name | Summary |
|---|---|
| [file](../-jet-file/file.md) | [jvm]<br>abstract val [file](../-jet-file/file.md): [Path](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html) |
