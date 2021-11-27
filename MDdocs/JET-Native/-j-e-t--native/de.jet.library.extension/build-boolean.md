//[JET-Native](../../index.md)/[de.jet.library.extension](index.md)/[buildBoolean](build-boolean.md)

# buildBoolean

[jvm]\
fun [buildBoolean](build-boolean.md)(base: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, process: [FlexibleMutable](../de.jet.library.tool.mutable/-flexible-mutable/index.md)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

This function builds a new boolean using internally a mutable boolean but returns the value of the mutable boolean at the end.

#### Return

the value of the mutable boolean at the end

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| base | the first value of the mutable boolean |
| process | the edit process of the mutable boolean |
