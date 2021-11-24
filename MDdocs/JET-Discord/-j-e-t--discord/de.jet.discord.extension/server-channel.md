//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[serverChannel](server-channel.md)

# serverChannel

[jvm]\
fun DiscordApi.[serverChannel](server-channel.md)(serverId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), channelId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ServerChannel?

Gets the Channel with the given [channelId](server-channel.md) on the server with the given [serverId](server-channel.md) searched discord-api wide.

#### Return

The Channel with the given [channelId](server-channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| serverId | The id of the server to get the Channel from. |
| channelId | The id of the Channel to get. |

[jvm]\
fun DiscordApi.[serverChannel](server-channel.md)(serverId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ServerChannel?

Gets the first Channel with the given [name](server-channel.md) on the server with the given [serverId](server-channel.md) searched discord-api wide.

#### Return

The Channel with the given [name](server-channel.md) or null if not found.

#### Author

Fruxz

## Parameters

jvm

| | |
|---|---|
| serverId | The id of the server to get the Channel from. |
| name | The name of the Channel to get. |
