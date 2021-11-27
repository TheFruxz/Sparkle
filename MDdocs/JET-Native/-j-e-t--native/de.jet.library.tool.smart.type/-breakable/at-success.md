//[JET-Native](../../../index.md)/[de.jet.library.tool.smart.type](../index.md)/[Breakable](index.md)/[atSuccess](at-success.md)

# atSuccess

[jvm]\
infix fun [atSuccess](at-success.md)(process: [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](index.md)&lt;[STATE](index.md), [RESULT](index.md)&gt;

Replaces the [atSuccess](at-success.md) function with the [process](at-success.md)

#### Return

the [Breakable](index.md) instance

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| process | function, that would be called, if the operation is succeeded |

[jvm]\
var [atSuccess](at-success.md): [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)
