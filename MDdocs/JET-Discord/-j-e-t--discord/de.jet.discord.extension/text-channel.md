//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[textChannel](text-channel.md)

# textChannel

[jvm]\
fun DiscordApi.[textChannel](text-channel.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): TextChannel?

Gets the TextChannel with the given [id](text-channel.md) searched discord-api wide.

#### Return

The TextChannel with the given [id](text-channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| id | The id of the TextChannel to get. |

[jvm]\
fun DiscordApi.[textChannel](text-channel.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): TextChannel?

Gets the first TextChannel with the given [name](text-channel.md) searched discord-api wide.

#### Return

The TextChannel with the given [name](text-channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | The name of the TextChannel to get. |

[jvm]\
fun Server.[textChannel](text-channel.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ServerTextChannel?

Gets the TextChannel with the given [id](text-channel.md) searched in the given server [this](../../../JET-Discord/de.jet.discord.extension/index.md).

#### Return

The TextChannel with the given [id](text-channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| id | The id of the TextChannel to get. |

[jvm]\
fun Server.[textChannel](text-channel.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ServerTextChannel?

Gets the first TextChannel with the given [name](text-channel.md) searched in the given server [this](../../../JET-Discord/de.jet.discord.extension/index.md).

#### Return

The TextChannel with the given [name](text-channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | The name of the TextChannel to get. |
