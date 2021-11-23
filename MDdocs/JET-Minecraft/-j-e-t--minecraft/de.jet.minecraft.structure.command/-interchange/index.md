//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.command](../index.md)/[Interchange](index.md)

# Interchange

[jvm]\
abstract class [Interchange](index.md)(vendor: [App](../../de.jet.minecraft.structure.app/-app/index.md), label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), aliases: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, requiresAuthorization: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), requiredExecutorType: [InterchangeExecutorType](../-interchange-executor-type/index.md), authorizationCheck: [InterchangeAuthorizationCheck](../-interchange-authorization-check/index.md), hiddenFromRecommendation: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), completion: [Completion](../-completion/index.md)) : CommandExecutor, [VendorsIdentifiable](../../de.jet.minecraft.tool.smart/-vendors-identifiable/index.md)&lt;[Interchange](index.md)&gt; , [Logging](../../de.jet.minecraft.tool.smart/-logging/index.md)

## Functions

| Name | Summary |
|---|---|
| [canExecuteBasePlate](can-execute-base-plate.md) | [jvm]<br>fun [canExecuteBasePlate](can-execute-base-plate.md)(executor: CommandSender): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>If the [executor](can-execute-base-plate.md) can execute the base of the interchange with its approvals. (**Not looking for the parameters and its own possible approvals!**) |
| [execution](execution.md) | [jvm]<br>fun [Interchange](index.md).[execution](execution.md)(execution: [InterchangeAccess](../../de.jet.minecraft.structure.command.live/-interchange-access/index.md).() -&gt; [InterchangeResult](../-interchange-result/index.md)): [InterchangeAccess](../../de.jet.minecraft.structure.command.live/-interchange-access/index.md).() -&gt; [InterchangeResult](../-interchange-result/index.md) |
| [interchangeException](interchange-exception.md) | [jvm]<br>fun [interchangeException](interchange-exception.md)(exception: [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html), executor: CommandSender, executorType: [InterchangeExecutorType](../-interchange-executor-type/index.md)) |
| [onCommand](on-command.md) | [jvm]<br>open override fun [onCommand](on-command.md)(sender: CommandSender, command: Command, label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), args: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;out [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [aliases](aliases.md) | [jvm]<br>val [aliases](aliases.md): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [authorizationCheck](authorization-check.md) | [jvm]<br>val [authorizationCheck](authorization-check.md): [InterchangeAuthorizationCheck](../-interchange-authorization-check/index.md) |
| [completion](completion.md) | [jvm]<br>val [completion](completion.md): [Completion](../-completion/index.md) |
| [completionCheck](completion-check.md) | [jvm]<br>val [completionCheck](completion-check.md): (executor: CommandSender, interchange: [Interchange](index.md), parameters: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [completionDisplay](completion-display.md) | [jvm]<br>val [completionDisplay](completion-display.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [completionEngine](completion-engine.md) | [jvm]<br>val [completionEngine](completion-engine.md): TabCompleter |
| [execution](execution.md) | [jvm]<br>abstract val [execution](execution.md): [InterchangeAccess](../../de.jet.minecraft.structure.command.live/-interchange-access/index.md).() -&gt; [InterchangeResult](../-interchange-result/index.md) |
| [hiddenFromRecommendation](hidden-from-recommendation.md) | [jvm]<br>val [hiddenFromRecommendation](hidden-from-recommendation.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md) | [jvm]<br>open override val [identity](../../de.jet.minecraft.tool.smart/-vendors-identifiable/identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Interchange](index.md)&gt; |
| [label](label.md) | [jvm]<br>val [label](label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [requiredApproval](required-approval.md) | [jvm]<br>val [requiredApproval](required-approval.md): [Approval](../../de.jet.minecraft.tool.permission/-approval/index.md)? |
| [requiredExecutorType](required-executor-type.md) | [jvm]<br>val [requiredExecutorType](required-executor-type.md): [InterchangeExecutorType](../-interchange-executor-type/index.md) |
| [requiresAuthorization](requires-authorization.md) | [jvm]<br>val [requiresAuthorization](requires-authorization.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [sectionLabel](section-label.md) | [jvm]<br>open override val [sectionLabel](section-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md) | [jvm]<br>open val [sectionLog](../../de.jet.minecraft.tool.smart/-logging/section-log.md): [Logger](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html) |
| [thisIdentity](this-identity.md) | [jvm]<br>open override val [thisIdentity](this-identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [vendor](vendor.md) | [jvm]<br>override val [vendor](vendor.md): [App](../../de.jet.minecraft.structure.app/-app/index.md) |
| [vendorIdentity](vendor-identity.md) | [jvm]<br>open override val [vendorIdentity](vendor-identity.md): [Identity](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; |

## Inheritors

| Name |
|---|
| [PointInterchange](../../de.jet.minecraft.app.component.essentials.point/-point-interchange/index.md) |
| [WorldInterchange](../../de.jet.minecraft.app.component.essentials.world/-world-interchange/index.md) |
| [JetLanguageComponent](../../de.jet.minecraft.app.component.system/-jet-language-component/index.md) |
| [ComponentInterchange](../../de.jet.minecraft.app.interchange/-component-interchange/index.md) |
| [JETInterchange](../../de.jet.minecraft.app.interchange/-j-e-t-interchange/index.md) |
| [PreferenceInterchange](../../de.jet.minecraft.app.interchange/-preference-interchange/index.md) |
| [SandboxInterchange](../../de.jet.minecraft.app.interchange/-sandbox-interchange/index.md) |
| [ServiceInterchange](../../de.jet.minecraft.app.interchange/-service-interchange/index.md) |
| [BuildModeInterchange](../../de.jet.minecraft.app.interchange.player/-build-mode-interchange/index.md) |
| [ChangeSkinInterchange](../../de.jet.minecraft.app.interchange.player/-change-skin-interchange/index.md) |
| [IssuedInterchange](../../de.jet.minecraft.structure.app.interchange/-issued-interchange/index.md) |
