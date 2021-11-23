//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.structure.component](../../index.md)/[Component](../index.md)/[RunType](index.md)

# RunType

[jvm]\
enum [RunType](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Component.RunType](index.md)&gt;

## Entries

| | |
|---|---|
| [ENABLED](-e-n-a-b-l-e-d/index.md) | [jvm]<br>[ENABLED](-e-n-a-b-l-e-d/index.md)()<br>Enabled after registration, but cannot be stopped or removed from the auto-start components. |
| [AUTOSTART_MUTABLE](-a-u-t-o-s-t-a-r-t_-m-u-t-a-b-l-e/index.md) | [jvm]<br>[AUTOSTART_MUTABLE](-a-u-t-o-s-t-a-r-t_-m-u-t-a-b-l-e/index.md)()<br>Starting after registration, Auto-Start option can be changed. |
| [AUTOSTART_IMMUTABLE](-a-u-t-o-s-t-a-r-t_-i-m-m-u-t-a-b-l-e/index.md) | [jvm]<br>[AUTOSTART_IMMUTABLE](-a-u-t-o-s-t-a-r-t_-i-m-m-u-t-a-b-l-e/index.md)()<br>Starting after registration, Auto-Start option cannot be changed, for components, which should start at the registering-process, and have to do so every time. |
| [DISABLED](-d-i-s-a-b-l-e-d/index.md) | [jvm]<br>[DISABLED](-d-i-s-a-b-l-e-d/index.md)()<br>Disabled after registration, but can be started, stopped and configured to be part of the auto-start system. |

## Properties

| Name | Summary |
|---|---|
| [name](../../../de.jet.minecraft.tool.input/-keyboard/-type/-a-n-y/index.md#-372974862%2FProperties%2F-726029290) | [jvm]<br>val [name](../../../de.jet.minecraft.tool.input/-keyboard/-type/-a-n-y/index.md#-372974862%2FProperties%2F-726029290): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../../../de.jet.minecraft.tool.input/-keyboard/-type/-a-n-y/index.md#-739389684%2FProperties%2F-726029290) | [jvm]<br>val [ordinal](../../../de.jet.minecraft.tool.input/-keyboard/-type/-a-n-y/index.md#-739389684%2FProperties%2F-726029290): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
