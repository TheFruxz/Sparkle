//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.app.event](../index.md)/[EventListener](index.md)

# EventListener

[jvm]\
interface [EventListener](index.md) : Listener, [VendorsIdentifiable](../../de.jet.minecraft.tool.smart/-vendors-identifiable/index.md)&lt;[EventListener](index.md)&gt;

## Properties

| Name | Summary |
|---|---|
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[EventListener](index.md)&gt; |
| [listenerIdentity](listener-identity.md) | [jvm]<br>open val [listenerIdentity](listener-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [thisIdentity](this-identity.md) | [jvm]<br>open override val [thisIdentity](this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](vendor.md) | [jvm]<br>abstract val [vendor](vendor.md): [App](../../de.jet.minecraft.structure.app/-app/index.md) |
| [vendorIdentity](vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |

## Inheritors

| Name |
|---|
| [RequestListener](../../de.jet.minecraft.app.component.feature/-keyboard-feature-component/-request-listener/index.md) |
| [BuildModeListener](../../de.jet.minecraft.app.component.world/-jet-build-mode-component/-build-mode-listener/index.md) |
