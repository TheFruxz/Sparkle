//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.data](../index.md)/[Preference](index.md)/[transformer](transformer.md)

# transformer

[jvm]\
fun &lt;[CORE](transformer.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [transformer](transformer.md)(toCore: [SHELL](index.md).() -&gt; [CORE](transformer.md), toShell: [CORE](transformer.md).() -&gt; [SHELL](index.md)): [Preference](index.md)&lt;[SHELL](index.md)&gt;

fun &lt;[CORE](transformer.md) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [transformer](transformer.md)(transformer: [DataTransformer](../-data-transformer/index.md)&lt;[SHELL](index.md), [CORE](transformer.md)&gt;): [Preference](index.md)&lt;[SHELL](index.md)&gt;

var [transformer](transformer.md): [DataTransformer](../-data-transformer/index.md)&lt;[SHELL](index.md), out [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;
