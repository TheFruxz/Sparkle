# ![MoltenKT](https://user-images.githubusercontent.com/28064149/174038731-aea205d3-e7aa-4c69-8ae2-f2016ece3653.png)

[![JitPack](https://jitpack.io/v/TheFruxz/MoltenKT.svg?style=flat-square)](https://jitpack.io/#TheFruxz/MoltenKT)
[![MoltenKT-Build](https://github.com/TheFruxz/MoltenKT/actions/workflows/build-MoltenKT.yml/badge.svg)](https://github.com/TheFruxz/MoltenKT/actions/workflows/build-MoltenKT.yml)
[![MoltenKT-Test](https://github.com/TheFruxz/MoltenKT/actions/workflows/test-MoltenKT.yml/badge.svg)](https://github.com/TheFruxz/MoltenKT/actions/workflows/test-MoltenKT.yml)
[![MoltenKT-Publish](https://github.com/TheFruxz/MoltenKT/actions/workflows/publish-MoltenKT.yml/badge.svg)](https://github.com/TheFruxz/MoltenKT/actions/workflows/publish-MoltenKT.yml)

[![Open Source](https://forthebadge.com/images/badges/open-source.svg)](https://github.com/TheFruxz/MoltenKT/blob/main/LICENSE)
[![Built by developers](https://forthebadge.com/images/badges/built-by-developers.svg)](https://github.com/TheFruxz/MoltenKT/graphs/contributors)
[![Written in Kotlin](https://forthebadge.com/images/badges/makes-people-smile.svg)](https://github.com/JetBrains/kotlin)

<br>

## 👋 Welcome to MoltenKT

MoltenKT is a multi-vector platform framework developed using the Kotlin programming language.
MoltenKT does as a framework provide supporting systems, functions and properties for other Kotlin projects
and is additionally optimized for other sub-areas besides the general Kotlin JVM.

MoltenKT's goal is to simplify and improve development with different platforms through its own, mostly easy to use systems, functions and properties.

## 📈 Version

Currently, MoltenKT is working with Kotlin version 1.7, which is the latest Kotlin version. In the future, versions of both
Kotlin, and those of Minecraft / Paper will be constantly updated to provide the best API as possible.

## 👻 Modules

MoltenKT currently offers 4 different project modules, each with its own platform goals, but all of these are based on the base-plate module MoltenKT-Core.
This integrated system allows to easily adapt new features across all of the modules, and also allows for easy integration of new features into the base-plate module.

### 💾 MoltenKT-Core 
MoltenKT-Core is the main module, all other modules of MoltenKT, which also work on the JVM basis of [Kotlin](https://github.com/jetbrains/kotlin), is using this module.
This is because this is where the most MoltenKT library-content is located, which you can also use in your projects.
Here int this module is the more general system and structure located, that is compatible with any JVM project based on Kotlin and/or Java.

### 🤯 MoltenKT-Paper
MoltenKT-Paper is the module for Minecraft server paper plugin development, which is based on the [PaperMC server API](https://github.com/PaperMC/Paper).
With MoltenKT-Paper specifically this development area is supported, with functions & systems which are precisely adapted for this system & plattform.

### 🎓 MoltenKT-Unfold
MoltenKT-Unfold is the module for the development of displayable content via the [Adventure](https://github.com/KyoriPowered/adventure)-Game-API.
This module allows special ways to communicate to the Adventure API and build structure and custom content with it.
Because it's based on Adventure, it is also cross-plattform compatible at its target, so Minecraft-Servers and cloud structure based on minecraft technology can use it.

## ⚙️ Setup

### Repository

How can I use MoltenKT in my own projects? For this you need to know what your project is based on, or should be based on.
We ourselves recommend that you use `Gradle Kotlin` in all your projects, but you can also use other systems like `Gradle` and `Maven`!

#### Using JitPack
##### Repository
```kotlin
maven("https://jitpack.io")
```

##### Dependency
```kotlin
implementation("com.github.TheFruxz.MoltenKT:moltenkt-core:$moltenVersion")
implementation("com.github.TheFruxz.MoltenKT:moltenkt-paper:$moltenVersion") // optionally add MoltenKT-Paper
implementation("com.github.TheFruxz.MoltenKT:moltenkt-unfold:$moltenVersion") // or MoltenKT-Unfold
```

#### Using GitHub Packages
##### Repository 
```kotlin
maven("https://maven.pkg.github.com/TheFruxz/MoltenKT") {
        credentials {
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
```

##### Dependency
```kotlin
implementation("de.moltenKT:moltenkt-core:$moltenVersion")
implementation("de.moltenKT:moltenkt-paper:$moltenVersion") // optionally add MoltenKT-Paper
implementation("de.moltenKT:moltenkt-unfold:$moltenVersion") // or MoltenKT-Unfold
```

#### 🔐 Auth

You need to have set the system variables `USERNAME` and `TOKEN` to your GitHub-Username and GitHub-Personal-Access-Token,
to access the packages via the GitHub-Packages Feature. You can also use the project variables `gpr.user` and `gpr.key`, but
don't publish them to the web!

## 🗞 Version

Since we always try to use the latest versions as soon as possible, as already described in the point 'Version Policy', current versions quickly become obsolete, so we will soon release a list of versions, where it will be shown exactly how long a certain version is still being supported.

## 👥 Contribution

Of course, you can also participate in MoltenKT and contribute to the development. However, please follow all community and general guidelines of GitHub and the repositories. You also have to respect the licenses set in this repository as well as in other repositories.

If you have any questions, suggestions or other items you would like to contribute to MoltenKT or just discuss, check out the Discussions' section of this repository, where you will find the respective areas where you can create your own questions or join in discussions on other things. 

## 🗒 Sidenotes

The MoltenKT-Paper-Runnable jar includes (/ shadowed) these small amount of dependencies, so you don't have to provide them:

  - MoltenKT-Core
  - MoltenKT-Paper
  - MoltenKT-Unfold
  - Kotlin Standard Library
  - Kotlin Standard Library JDK8
  - Kotlin Reflect
  - KotlinX Serialization JSON
  - KotlinX Coroutines Core
  - SLF4J-API
  - JetBrains Exposed Core
  - JetBrains Exposed DAO
  - JetBrains Exposed JDBC
  - Ktor Client Core JVM
  - Ktor Serialization KotlinX JSON
  - Ktor Client CIO
  - Ktor Client Content Negotiation
  - Adventure API
  - Adventure text Serializer Legacy
  - Adventure text MiniMessage

###### We build & use MoltenKT on Java 17 - [Eclipse Temurin](https://adoptium.net/).
###### Also build & run MoltenKT with [Eclipse Temurin](https://adoptium.net/) to get the best possible experience!
