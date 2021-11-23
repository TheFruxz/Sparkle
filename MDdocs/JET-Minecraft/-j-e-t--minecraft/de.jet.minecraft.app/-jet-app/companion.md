//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.app](../index.md)/[JetApp](index.md)/[companion](companion.md)

# companion

[jvm]\
open override val [companion](companion.md): [JetApp.Companion](-companion/index.md)

# App.companion (abstract-value)

##  Info

This value defines the Companion-Object of this class, which holds the instance variable *(lateinit recommended)* and other app-related special variables & functions.

##  Use

Use this value/companion to save the app-instance, and other important things, but not too much, it is only a place for very important every-time reachable stuff! Use your App-Class(-Type) as the input for the <T> at [AppCompanion](../../de.jet.minecraft.structure.app/-app-companion/index.md)<*>, so that the app-instance is from your class, but also use instance = this at the [register](register.md)() function

##  Relations

Because the [AppCompanion](../../de.jet.minecraft.structure.app/-app-companion/index.md)<T> class provides the same identity ([Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)<[App](../../de.jet.minecraft.structure.app/-app/index.md)>) as the app itself, you can use the [companion](companion.md) instead of the app, to check equalities and more!

#### Author

Fruxz (@TheFruxz)

#### Since

1.0-BETA-2 (preview)

## Samples

de.jet.minecraft.app.JetApp.companion

## See also

jvm

| | |
|---|---|
| [de.jet.minecraft.app.JetApp.Companion](-companion/index.md) |  |
