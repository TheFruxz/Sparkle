//[JET-Native](../../index.md)/[de.jet.library.extension](index.md)/[checkAllObjects](check-all-objects.md)

# checkAllObjects

[jvm]\
fun &lt;[T](check-all-objects.md)&gt; [checkAllObjects](check-all-objects.md)(vararg objects: [T](check-all-objects.md), check: [T](check-all-objects.md).() -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Executes a check, if all [objects](check-all-objects.md) are passing the [check](check-all-objects.md) check.

#### Return

true if all [objects](check-all-objects.md) are passing the [check](check-all-objects.md) check, false otherwise.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| objects | The objects to check. |
| check | The check to execute. |
