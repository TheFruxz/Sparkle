# ![JET - The Kotlin-Based Framework for Next-Gen Paper Plugins - Just everything tweaked](https://user-images.githubusercontent.com/28064149/143691452-6bc94d3d-5815-49ba-a041-722af5aef580.gif)

[![JitPack](https://jitpack.io/v/TheFruxz/JET.svg?style=flat-square)](https://jitpack.io/#TheFruxz/JET)
[![JETBuild](https://github.com/TheFruxz/JET/actions/workflows/build-JET.yml/badge.svg)](https://github.com/TheFruxz/JET/actions/workflows/build-JET.yml)
[![JETTest](https://github.com/TheFruxz/JET/actions/workflows/test-JET.yml/badge.svg)](https://github.com/TheFruxz/JET/actions/workflows/test-JET.yml)
[![JETPublish](https://github.com/TheFruxz/JET/actions/workflows/publish-JET.yml/badge.svg)](https://github.com/TheFruxz/JET/actions/workflows/publish-JET.yml)

[![Open Source](https://forthebadge.com/images/badges/open-source.svg)](https://github.com/TheFruxz/JET/blob/main/LICENSE)
[![Built by developers](https://forthebadge.com/images/badges/built-by-developers.svg)](https://github.com/TheFruxz/JET/graphs/contributors)
[![Written in Kotlin](https://forthebadge.com/images/badges/makes-people-smile.svg)](https://github.com/JetBrains/kotlin)

<br>

## JET

JET is a multi-vector platform framework developed using the Kotlin programming language.
JET does as a framework provide supporting system, functions and variables for other Kotlin projects
and is additionally optimized for other sub-areas besides the general Kotlin JVM.

Among other things, there is a JET module for the development of Minecraft server paper plugins as well as a module for developing
of Discord bots via the JavaCord API.

JET's goal is to simplify and improve development with those platforms through its own, mostly easy to use systems, functions and variables.
and improve the development with those platforms.

## Version

Currently JET is working with Kotlin version 1.6, which is the latest Kotlin version. In the future, versions of both
Kotlin, as well as those of Minecraft & JavaCord will be constantly updated to provide the best API as possible.

## Modules

JET currently offers 3 different project modules, each with its own platform goals, but all of these are currently based on the
modules are based on Kotlin JVM.

### JET-JVM
JET-JVM is the main module, all other modules of JET, which also work on the JVM basis of [Kotlin](https://github.com/jetbrains/kotlin), use
this module. This is because this is where the JET libraries are located, which you can also use in your projects.
Here are the more general systems and structures that are compatible with any JVM project.

### JET-JavaCord
JET-JavaCord is the module for the development of Discord bots, which is based on the public [JavaCord API](https://github.com/Javacord/Javacord).
With JET-JavaCord especially this development area is supported, with functions & systems which are exactly
adapted for this system.

Example:
```kotlin
fun main() = runApp("test-app") {

	attachWith(DiscordBotExtension) {

		credentials {
			token = "OTA5OXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXIvDkM"
		}

		appearance {

			displayName = "JavaCord-Bot"
			status = ONLINE

			activity {
				name = "JET-JavaCord"
				activityType = STREAMING
				streamingUrl = url("https://www.youtube.com/watch?v=ow5kdhDa_pk")
			}

		}

		preProcess { println("PreProcess") }

		preSetup { println("PreSetup") }

		preLogin { println("PreLogin") }

		postLogin { println("PostLogin") }

		postProcess { println("PostProcess") }

	}

}
```

### JET-Paper
JET-Paper is the module for Minecraft server paper plugin development, which is based on the [PaperMC server API](https://github.com/PaperMC/Paper).
With JET-Paper specifically this development area is supported, with functions & systems which are precisely
adapted for this system.

## Setup

### Repository

How can I use JET in my own projects? For this you need to know what your project is based on, or should be based on.
We ourselves recommend that you use `Gradle Kotlin` in all your projects, but you can also use other systems like `Gradle` and `Maven`!

#### Using GitHub Packages (recommended)
##### Repository 
```kotlin
maven("https://maven.pkg.github.com/TheFruxz/JET") {
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
implementation("de.jet:jet-jvm:1.0-beta-12.2")
implementation("de.jet:jet-javacord:1.0-beta-12.2") // optional add JET-JavaCord
implementation("de.jet:jet-paper:1.0-beta-12.2") // or JET-Minecraft
```

#### Using JitPack (easy to setup)
##### Repository
```kotlin
maven("https://jitpack.io")
```

##### Dependency
```kotlin
implementation("com.github.TheFruxz.JET:jet-jvm:1.0-beta-12.2")
implementation("com.github.TheFruxz.JET:jet-javacord:1.0-beta-12.2") // optional add JET-JavaCord
implementation("com.github.TheFruxz.JET:jet-paper:1.0-beta-12.2") // or JET-Minecraft
```

#### Auth

You need to have set the system variables `USERNAME` and `TOKEN` to your GitHub-Username and GitHub-Personal-Access-Token,
to access the packages via the GitHub-Packages Feature. You can also use the project variables `gpr.user` and `grp.key`, but
don't publish them to the web!

#### INFO

Using JitPack as an alternative to GitHub Packages is also possible, but it is not recommended,
due to the lack of documentation transportation!

## Version

Since we always try to use the latest versions as soon as possible, as already described in the point 'Version Policy', current versions quickly become obsolete, so we will soon release a list of versions, where it will be shown exactly how long a certain version is still being supported.

## Contribution

Of course, you can also participate in JET and contribute to the development. However, please follow all community and general guidelines of GitHub and the repositories. You also have to respect the licenses set in this repository as well as in other repositories.

If you have any questions, suggestions or other items you would like to contribute to JET or just discuss, check out the Discussions section of this repository, where you will find the respective areas where you can create your own questions or join in discussions on other things. 

###### We build & use JET on Java 17 - [Eclipse Temurin](https://adoptium.net/).
###### Also build & run JET with [Eclipse Temurin](https://adoptium.net/) to get the best possible experience!
