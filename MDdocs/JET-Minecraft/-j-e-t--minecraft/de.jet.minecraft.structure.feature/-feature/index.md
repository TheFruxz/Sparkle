//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.feature](../index.md)/[Feature](index.md)

# Feature

[jvm]\
class [Feature](index.md)(vendorIdentity: [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;out [App](../../de.jet.minecraft.structure.app/-app/index.md)&gt;, name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [VendorsIdentifiable](../../de.jet.minecraft.tool.smart/-vendors-identifiable/index.md)&lt;[Feature](index.md)&gt;

## Types

| Name | Summary |
|---|---|
| [FeatureProperties](-feature-properties/index.md) | [jvm]<br>class [FeatureProperties](-feature-properties/index.md) |
| [FeatureState](-feature-state/index.md) | [jvm]<br>enum [FeatureState](-feature-state/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Feature.FeatureState](-feature-state/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [registerIfNotRegistered](register-if-not-registered.md) | [jvm]<br>fun [registerIfNotRegistered](register-if-not-registered.md)(state: [Feature.FeatureState](-feature-state/index.md) = FeatureState.ENABLED) |

## Properties

| Name | Summary |
|---|---|
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Feature](index.md)&gt; |
| [isEnabled](is-enabled.md) | [jvm]<br>var [isEnabled](is-enabled.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Communicates directly with the cache |
| [properties](properties.md) | [jvm]<br>val [properties](properties.md): [Feature.FeatureProperties](-feature-properties/index.md) |
| [thisIdentity](this-identity.md) | [jvm]<br>open override val [thisIdentity](this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendorIdentity](vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;out [App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |
