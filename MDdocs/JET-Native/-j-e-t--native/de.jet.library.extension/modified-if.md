//[JET-Native](../../index.md)/[de.jet.library.extension](index.md)/[modifiedIf](modified-if.md)

# modifiedIf

[jvm]\
fun &lt;[T](modified-if.md)&gt; [T](modified-if.md).[modifiedIf](modified-if.md)(modifyIf: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), modification: [T](modified-if.md).() -&gt; [T](modified-if.md)): [T](modified-if.md)

Returning the [this](../../../JET-Native/de.jet.library.extension/index.md)<[T](modified-if.md)> modified with the [modification](modified-if.md) if [modifyIf](modified-if.md) is true, otherwise returning the [this](../../../JET-Native/de.jet.library.extension/index.md)<[T](modified-if.md)> directly.

#### Return

the [this](../../../JET-Native/de.jet.library.extension/index.md)<[T](modified-if.md)> modified with the [modification](modified-if.md) if [modifyIf](modified-if.md) is true, otherwise returning the [this](../../../JET-Native/de.jet.library.extension/index.md)<[T](modified-if.md)> directly.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| modifyIf | if true modification is returned, else original |
| modification | the modification to apply |
