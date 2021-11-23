//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.general.api.mojang](../index.md)/[MojangProfile](index.md)

# MojangProfile

[jvm]\
data class [MojangProfile](index.md)(created_at: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, textures: [MojangProfileTextures](../-mojang-profile-textures/index.md), username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), username_history: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MojangProfileUsernameHistoryEntry](../-mojang-profile-username-history-entry/index.md)&gt;, uuid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

## Functions

| Name | Summary |
|---|---|
| [applyNameToPlayer](apply-name-to-player.md) | [jvm]<br>fun [applyNameToPlayer](apply-name-to-player.md)(target: Player) |
| [applySkinToPlayer](apply-skin-to-player.md) | [jvm]<br>fun [applySkinToPlayer](apply-skin-to-player.md)(target: Player) |
| [applySkinToSkull](apply-skin-to-skull.md) | [jvm]<br>fun [applySkinToSkull](apply-skin-to-skull.md)(item: [Item](../../de.jet.minecraft.tool.display.item/-item/index.md), replaceName: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Item](../../de.jet.minecraft.tool.display.item/-item/index.md)<br>WARNING! current quirk will be overwritten! |
| [applySkinToSkullMeta](apply-skin-to-skull-meta.md) | [jvm]<br>fun [applySkinToSkullMeta](apply-skin-to-skull-meta.md)(target: SkullMeta, replaceName: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>WARNING! owningPlayer have to be set! |

## Properties

| Name | Summary |
|---|---|
| [created_at](created_at.md) | [jvm]<br>val [created_at](created_at.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [textures](textures.md) | [jvm]<br>val [textures](textures.md): [MojangProfileTextures](../-mojang-profile-textures/index.md) |
| [username](username.md) | [jvm]<br>val [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [username_history](username_history.md) | [jvm]<br>val [username_history](username_history.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MojangProfileUsernameHistoryEntry](../-mojang-profile-username-history-entry/index.md)&gt; |
| [uuid](uuid.md) | [jvm]<br>val [uuid](uuid.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
