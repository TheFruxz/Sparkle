# ![Sparkle Banner](https://user-images.githubusercontent.com/28064149/193403722-69ddfc53-95b7-4123-b974-308d9360cdb3.jpg)

<br>

> Sparkle is currently being reworked. Check out the `rework/2023` branch for the latest dev-build.

## 👋 Welcome to Sparkle

Sparkle is a framework, developed for the Kotlin programming language and Paper as the Minecraft-Server platform.
This project provides a feature-rich architecture for Minecraft-Plugins, which is powerful and filled with rocket science.

## ⚙️ Setup

### Repository

How can I use Sparkle in my own projects? For this you need to know what your project is based on, or should be based on.
We ourselves recommend that you use `Gradle Kotlin` in all your projects, but you can also use other systems like `Gradle` and `Maven`!

#### Using JitPack
##### Repository
```kotlin
maven("https://jitpack.io")
```

##### Dependency
```kotlin
implementation("com.github.TheFruxz:sparkle:$sparkleVersion")
```

#### Using GitHub Packages
##### Repository 
```kotlin
maven("https://maven.pkg.github.com/TheFruxz/Sparkle") {
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
implementation("de.fruxz:sparkle:$sparkleVersion")
```

#### 🔐 Auth

You need to have set the system variables `USERNAME` and `TOKEN` to your GitHub-Username and GitHub-Personal-Access-Token,
to access the packages via the GitHub-Packages Feature. You can also use the project variables `gpr.user` and `gpr.key`, but
don't publish them to the web!

## 🗞 Version

Since we always try to use the latest versions as soon as possible, as already described in the point 'Version Policy', current versions quickly become obsolete, so we will soon release a list of versions, where it will be shown exactly how long a certain version is still being supported.

## 👥 Contribution

Of course, you can also participate in Sparkle and contribute to the development. However, please follow all community and general guidelines of GitHub and the repositories. You also have to respect the licenses set in this repository as well as in other repositories.

If you have any questions, suggestions or other items you would like to contribute to Sparkle or just discuss, check out the Discussions' section of this repository, where you will find the respective areas where you can create your own questions or join in discussions on other things. 

## 🗒 Sidenotes

The Sparkle-Runnable jar includes (/ shadowed) these small amount of dependencies, so you don't have to provide them:

  - [Ascend](https://www.github.com/TheFruxz/Ascend)
  - [Stacked](https://www.github.com/TheFruxz/Stacked)
  - [Sparkle](https://www.github.com/TheFruxz/Sparkle)
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

###### We build & use Sparkle on Java 17 - [Eclipse Temurin](https://adoptium.net/).
###### Also build & run Sparkle with [Eclipse Temurin](https://adoptium.net/) to get the best possible experience!

[![Open Source](https://forthebadge.com/images/badges/open-source.svg)](https://github.com/TheFruxz/Sparkle/blob/main/LICENSE)
[![Built by developers](https://forthebadge.com/images/badges/built-by-developers.svg)](https://github.com/TheFruxz/Sparkle/graphs/contributors)
[![Written in Kotlin](https://forthebadge.com/images/badges/makes-people-smile.svg)](https://github.com/JetBrains/kotlin)
