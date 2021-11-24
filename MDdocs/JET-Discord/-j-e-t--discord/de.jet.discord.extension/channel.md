//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[channel](channel.md)

# channel

[jvm]\
fun DiscordApi.[channel](channel.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): Channel?

Gets the Channel with the given [id](channel.md) searched discord-api wide.

#### Return

The Channel with the given [id](channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| id | The id of the Channel to get. |

[jvm]\
fun DiscordApi.[channel](channel.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Channel?

Gets the first Channel with the given [name](channel.md) searched discord-api wide.

#### Return

The Channel with the given [name](channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | The name of the Channel to get. |

[jvm]\
fun Server.[channel](channel.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ServerChannel?

Gets the Channel with the given [id](channel.md) searched in the given server [this](../../../JET-Discord/de.jet.discord.extension/index.md).

#### Return

The Channel with the given [id](channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| id | The id of the Channel to get. |

[jvm]\
fun Server.[channel](channel.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ServerChannel?

Gets the first Channel with the given [name](channel.md) searched in the given server [this](../../../JET-Discord/de.jet.discord.extension/index.md).

#### Return

The Channel with the given [name](channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | The name of the Channel to get. |
