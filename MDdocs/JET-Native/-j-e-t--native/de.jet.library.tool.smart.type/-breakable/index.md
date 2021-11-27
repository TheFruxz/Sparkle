//[JET-Native](../../../index.md)/[de.jet.library.tool.smart.type](../index.md)/[Breakable](index.md)

# Breakable

[jvm]\
class [Breakable](index.md)&lt;[STATE](index.md), [RESULT](index.md)&gt;

Operation results, that depends on unsafe factors, like user input. if failed or denied the [atBreak](at-break.md) function would be called, if succeeded, the [atSuccess](at-success.md) function would be called

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| atBreak | function, that would be called, if the operation is denied |
| atSuccess | function, that would be called, if the operation is succeeded |

## Functions

| Name | Summary |
|---|---|
| [atBreak](at-break.md) | [jvm]<br>infix fun [atBreak](at-break.md)(process: [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](index.md)&lt;[STATE](index.md), [RESULT](index.md)&gt;<br>Replaces the [atBreak](at-break.md) function with the [process](at-break.md) |
| [atSuccess](at-success.md) | [jvm]<br>infix fun [atSuccess](at-success.md)(process: [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](index.md)&lt;[STATE](index.md), [RESULT](index.md)&gt;<br>Replaces the [atSuccess](at-success.md) function with the [process](at-success.md) |

## Properties

| Name | Summary |
|---|---|
| [atBreak](at-break.md) | [jvm]<br>var [atBreak](at-break.md): [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [atSuccess](at-success.md) | [jvm]<br>var [atSuccess](at-success.md): [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
