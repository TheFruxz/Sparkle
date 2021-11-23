//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.structure.app](../index.md)/[App](index.md)/[appLabel](app-label.md)

# appLabel

[jvm]\
abstract val [appLabel](app-label.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

# App.appLabel

##  Info

This value defines the display-name of this app, which will be displayed in app-lists and information with the app included. Can be duplicated, but try to avoid duplicated display names with other apps!

##  Use

Use this value to tell your customers & visitors that your app is running, there some possible ways to do that:

- 
   On Minecraft-Networks: Tell the players the name of your network
- 
   On Publishing: Tell the players the name of your app (where they can find it)
- 
   On private use: Tell yourself, that this app is this app

But you can use this like you like!

##  Relations

The label is not related to some crucial read/save system or something else, in this cases [appIdentity](app-identity.md) is used!

#### Author

Fruxz (@TheFruxz)

#### Since

1.0-BETA-2 (preview)

## Samples

de.jet.minecraft.app.JetApp.appLabel

## See also

jvm

| | |
|---|---|
| [de.jet.minecraft.app.JetApp](../../de.jet.minecraft.app/-jet-app/app-label.md) |  |
