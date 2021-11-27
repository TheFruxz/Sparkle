//[JET-Native](../../index.md)/[de.jet.library.extension](index.md)/[modifyIf](modify-if.md)

# modifyIf

[jvm]\
fun &lt;[T](modify-if.md)&gt; [T](modify-if.md).[modifyIf](modify-if.md)(modifyIf: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), modification: [T](modify-if.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [T](modify-if.md)

Applying the [modification](modify-if.md) to [this](../../../JET-Native/de.jet.library.extension/index.md) if [modifyIf](modify-if.md) is true, otherwise to nothing to [this](../../../JET-Native/de.jet.library.extension/index.md).

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| modifyIf | if true modification is applied, else keep original |
| modification | the modification to apply |
