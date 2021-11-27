//[JET-Native](../../../index.md)/[de.jet.library.tool.smart.type](../index.md)/[Breakable](index.md)/[atBreak](at-break.md)

# atBreak

[jvm]\
infix fun [atBreak](at-break.md)(process: [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](index.md)&lt;[STATE](index.md), [RESULT](index.md)&gt;

Replaces the [atBreak](at-break.md) function with the [process](at-break.md)

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
| process | function, that would be called, if the operation is denied |

[jvm]\
var [atBreak](at-break.md): [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)
