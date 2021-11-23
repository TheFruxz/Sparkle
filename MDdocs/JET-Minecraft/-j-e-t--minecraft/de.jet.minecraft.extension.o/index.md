//[JET-Minecraft](../../index.md)/[de.jet.minecraft.extension.o](index.md)

# Package de.jet.minecraft.extension.o

## Functions

| Name | Summary |
|---|---|
| [buildSandBox](build-sand-box.md) | [jvm]<br>inline fun [buildSandBox](build-sand-box.md)(vendor: [App](../de.jet.minecraft.structure.app/-app/index.md), identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), noinline action: [SandBoxInteraction](../de.jet.minecraft.runtime.sandbox/-sand-box-interaction/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [destroyAllSandBoxes](destroy-all-sand-boxes.md) | [jvm]<br>fun [destroyAllSandBoxes](destroy-all-sand-boxes.md)() |
| [destroySandBox](destroy-sand-box.md) | [jvm]<br>fun [destroySandBox](destroy-sand-box.md)(sandBox: [SandBox](../de.jet.minecraft.runtime.sandbox/-sand-box/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun [destroySandBox](destroy-sand-box.md)(fullIdentity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getSandBox](get-sand-box.md) | [jvm]<br>fun [getSandBox](get-sand-box.md)(fullIdentity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SandBox](../de.jet.minecraft.runtime.sandbox/-sand-box/index.md)? |

## Properties

| Name | Summary |
|---|---|
| [allSandBoxes](all-sand-boxes.md) | [jvm]<br>val [allSandBoxes](all-sand-boxes.md): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[SandBox](../de.jet.minecraft.runtime.sandbox/-sand-box/index.md)&gt; |
