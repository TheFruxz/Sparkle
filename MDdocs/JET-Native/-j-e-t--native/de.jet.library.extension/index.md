//[JET-Native](../../index.md)/[de.jet.library.extension](index.md)

# Package de.jet.library.extension

## Functions

| Name | Summary |
|---|---|
| [buildBoolean](build-boolean.md) | [jvm]<br>fun [buildBoolean](build-boolean.md)(base: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, process: [FlexibleMutable](../de.jet.library.tool.mutable/-flexible-mutable/index.md)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [catchException](catch-exception.md) | [jvm]<br>fun [catchException](catch-exception.md)(exception: [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)) |
| [checkAllObjects](check-all-objects.md) | [jvm]<br>fun &lt;[T](check-all-objects.md)&gt; [checkAllObjects](check-all-objects.md)(vararg objects: [T](check-all-objects.md), check: [T](check-all-objects.md).() -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [dump](dump.md) | [jvm]<br>fun [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?.[dump](dump.md)()<br>fun [Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)?.[dump](dump.md)() |
| [forceCast](force-cast.md) | [jvm]<br>fun &lt;[O](force-cast.md)&gt; [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?.[forceCast](force-cast.md)(): [O](force-cast.md)<br>fun &lt;[O](force-cast.md)&gt; [Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)?.[forceCast](force-cast.md)(): [O](force-cast.md) |
| [forceCastOrNull](force-cast-or-null.md) | [jvm]<br>fun &lt;[O](force-cast-or-null.md)&gt; [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?.[forceCastOrNull](force-cast-or-null.md)(): [O](force-cast-or-null.md)?<br>fun &lt;[O](force-cast-or-null.md)&gt; [Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)?.[forceCastOrNull](force-cast-or-null.md)(): [O](force-cast-or-null.md)? |
| [forceNullableCast](force-nullable-cast.md) | [jvm]<br>fun &lt;[O](force-nullable-cast.md)&gt; [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?.[forceNullableCast](force-nullable-cast.md)(): [O](force-nullable-cast.md)?<br>fun &lt;[O](force-nullable-cast.md)&gt; [Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)?.[forceNullableCast](force-nullable-cast.md)(): [O](force-nullable-cast.md)? |
| [forceNullableCastOrNull](force-nullable-cast-or-null.md) | [jvm]<br>fun &lt;[O](force-nullable-cast-or-null.md)&gt; [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?.[forceNullableCastOrNull](force-nullable-cast-or-null.md)(): [O](force-nullable-cast-or-null.md)?<br>fun &lt;[O](force-nullable-cast-or-null.md)&gt; [Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)?.[forceNullableCastOrNull](force-nullable-cast-or-null.md)(): [Nothing](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing/index.html)? |
| [getResourceByteArray](get-resource-byte-array.md) | [jvm]<br>inline fun [getResourceByteArray](get-resource-byte-array.md)(resource: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html) |
| [getResourceText](get-resource-text.md) | [jvm]<br>inline fun [getResourceText](get-resource-text.md)(resource: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [jetTry](jet-try.md) | [jvm]<br>fun [jetTry](jet-try.md)(catchBlock: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = { }, tryBlock: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [modifiedIf](modified-if.md) | [jvm]<br>fun &lt;[T](modified-if.md)&gt; [T](modified-if.md).[modifiedIf](modified-if.md)(modifyIf: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), modification: [T](modified-if.md).() -&gt; [T](modified-if.md)): [T](modified-if.md) |
| [modifyIf](modify-if.md) | [jvm]<br>fun &lt;[T](modify-if.md)&gt; [T](modify-if.md).[modifyIf](modify-if.md)(modifyIf: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), modification: [T](modify-if.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [T](modify-if.md) |
| [randomString](random-string.md) | [jvm]<br>fun [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html).[randomString](random-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [switchResult](switch-result.md) | [jvm]<br>fun &lt;[T](switch-result.md)&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html).[switchResult](switch-result.md)(match: [T](switch-result.md), mismatch: [T](switch-result.md)): [T](switch-result.md) |
| [turnFalse](turn-false.md) | [jvm]<br>fun [Mutable](../de.jet.library.tool.mutable/-mutable/index.md)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;.[turnFalse](turn-false.md)() |
| [turnTrue](turn-true.md) | [jvm]<br>fun [Mutable](../de.jet.library.tool.mutable/-mutable/index.md)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;.[turnTrue](turn-true.md)() |

## Properties

| Name | Summary |
|---|---|
| [asString](as-string.md) | [jvm]<br>val [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html).[asString](as-string.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [isNotNull](is-not-null.md) | [jvm]<br>val [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?.[isNotNull](is-not-null.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isNull](is-null.md) | [jvm]<br>val [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?.[isNull](is-null.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
