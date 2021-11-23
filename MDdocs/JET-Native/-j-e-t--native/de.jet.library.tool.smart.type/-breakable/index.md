//[JET-Native](../../../index.md)/[de.jet.library.tool.smart.type](../index.md)/[Breakable](index.md)

# Breakable

[jvm]\
class [Breakable](index.md)&lt;[STATE](index.md), [RESULT](index.md)&gt;

Operation results, that depends on unsafe factors, like user input. if failed or denied the [atBreak](at-break.md) function would be called, if succeeded, the [atSuccess](at-success.md) function would be called

## Functions

| Name | Summary |
|---|---|
| [atBreak](at-break.md) | [jvm]<br>infix fun [atBreak](at-break.md)(process: [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](index.md)&lt;[STATE](index.md), [RESULT](index.md)&gt; |
| [atSuccess](at-success.md) | [jvm]<br>infix fun [atSuccess](at-success.md)(process: [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](index.md)&lt;[STATE](index.md), [RESULT](index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [atBreak](at-break.md) | [jvm]<br>var [atBreak](at-break.md): [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [atSuccess](at-success.md) | [jvm]<br>var [atSuccess](at-success.md): [STATE](index.md).([RESULT](index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
