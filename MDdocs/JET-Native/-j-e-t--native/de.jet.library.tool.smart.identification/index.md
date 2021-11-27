//[JET-Native](../../index.md)/[de.jet.library.tool.smart.identification](index.md)

# Package de.jet.library.tool.smart.identification

## Types

| Name | Summary |
|---|---|
| [Identifiable](-identifiable/index.md) | [jvm]<br>interface [Identifiable](-identifiable/index.md)&lt;[T](-identifiable/index.md)&gt;<br>This interface marks every object that can be identified using the [Identity](-identity/index.md) object and an identity [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html). |
| [Identity](-identity/index.md) | [jvm]<br>data class [Identity](-identity/index.md)&lt;[T](-identity/index.md)&gt;(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Identifiable](-identifiable/index.md)&lt;[T](-identity/index.md)&gt; <br>This data class represents an identity, which should be (in the most cases) unique inside the [T](-identity/index.md) environment. |
