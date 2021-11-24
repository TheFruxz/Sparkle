//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[serverTextChannels](server-text-channels.md)

# serverTextChannels

[jvm]\
fun DiscordApi.[serverTextChannels](server-text-channels.md)(serverId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;ServerTextChannel&gt;?

Gets every TextChannel on the server with the given [serverId](server-text-channels.md) searched discord-api wide as a list.

#### Return

Every TextChannel or an empty list if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| serverId | The id of the server to get the TextChannel from. |
