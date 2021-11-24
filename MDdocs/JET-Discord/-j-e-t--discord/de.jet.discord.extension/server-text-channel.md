//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[serverTextChannel](server-text-channel.md)

# serverTextChannel

[jvm]\
fun DiscordApi.[serverTextChannel](server-text-channel.md)(serverId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), channelId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): ServerTextChannel?

Gets the TextChannel with the given [channelId](server-text-channel.md) on the server with the given [serverId](server-text-channel.md) searched discord-api wide.

#### Return

The TextChannel with the given [channelId](server-text-channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| serverId | The id of the server to get the TextChannel from. |
| channelId | The id of the TextChannel to get. |

[jvm]\
fun DiscordApi.[serverTextChannel](server-text-channel.md)(serverId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): ServerTextChannel?

Gets the first TextChannel with the given [name](server-text-channel.md) on the server with the given [serverId](server-text-channel.md) searched discord-api wide.

#### Return

The TextChannel with the given [name](server-text-channel.md) or null if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| serverId | The id of the server to get the TextChannel from. |
| name | The name of the TextChannel to get. |
