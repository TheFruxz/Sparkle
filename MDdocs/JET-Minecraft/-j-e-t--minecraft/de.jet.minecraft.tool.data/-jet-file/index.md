//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.data](../index.md)/[JetFile](index.md)

# JetFile

[jvm]\
interface [JetFile](index.md)

## Functions

| Name | Summary |
|---|---|
| [contains](contains.md) | [jvm]<br>abstract fun [contains](contains.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [get](get.md) | [jvm]<br>abstract operator fun &lt;[T](get.md)&gt; [get](get.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](get.md)? |
| [load](load.md) | [jvm]<br>abstract fun [load](load.md)() |
| [save](save.md) | [jvm]<br>abstract fun [save](save.md)() |
| [set](set.md) | [jvm]<br>abstract operator fun &lt;[T](set.md)&gt; [set](set.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [T](set.md)) |

## Properties

| Name | Summary |
|---|---|
| [file](file.md) | [jvm]<br>abstract val [file](file.md): [Path](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html) |

## Inheritors

| Name |
|---|
| [JetJsonFile](../-jet-json-file/index.md) |
| [JetYamlFile](../-jet-yaml-file/index.md) |
