# ![JET - The Kotlin-Based Framework for Next-Gen Paper Plugins - Just everything tweaked](https://user-images.githubusercontent.com/28064149/126283274-71633d1c-95ec-407d-ab71-c7db9e96a3d5.gif)

[![Open Source](https://forthebadge.com/images/badges/open-source.svg)](https://github.com/JustEverythingTweaked/JET/blob/main/LICENSE)
[![Built by developers](https://forthebadge.com/images/badges/built-by-developers.svg)](https://github.com/JustEverythingTweaked/JET/graphs/contributors)
[![Written in Kotlin](https://forthebadge.com/images/badges/makes-people-smile.svg)](https://github.com/JetBrains/kotlin)
<br>
[![JETBuild](https://github.com/JustEverythingTweaked/JET/actions/workflows/build-JET.yml/badge.svg)](https://github.com/JustEverythingTweaked/JET/actions/workflows/build-JET.yml)
[![JETTest](https://github.com/JustEverythingTweaked/JET/actions/workflows/test-JET.yml/badge.svg)](https://github.com/JustEverythingTweaked/JET/actions/workflows/test-JET.yml)
[![JETPublish](https://github.com/JustEverythingTweaked/JET/actions/workflows/publish-JET.yml/badge.svg)](https://github.com/JustEverythingTweaked/JET/actions/workflows/publish-JET.yml)

<br>

## JET

JET is a Kotlin based framework developed for the Paper platform, which is based on Bukkit & Spigot.
JET is designed to simplify development with both simple and complex steps, and provide various technologies for securing and structuring so that the final product is secure, stable, and of simpler quality.

## Version Policy

JET is about security, quality and stability. That's why we always use the latest Minecraft version to offer all this as soon as possible. We always use the latest paper version so that patches and fixes contribute to these factors.

## Setup

### Repository

How can I use JET in my own projects? For this you need to know what your project is based on, or should be based on.
We ourselves recommend that you use `Gradle Kotlin` in all your projects, but you can also use other systems like `Gradle` and `Maven`!

#### `Gradle Kotlin`
##### Repository 
```kotlin
maven("https://maven.pkg.github.com/JustEverythingTweaked/JET") {
    credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
    }
}
```

##### Dependency
```kotlin
implementation("de.jet:jet:<version>")
```

## Version

Since we always try to use the latest versions as soon as possible, as already described in the point 'Version Policy', current versions quickly become obsolete after half a year, so we will soon release a list of versions, where it will be shown exactly how long a certain version is still supported.

## Contribution

Of course, you can also participate in JET and contribute to the development. However, please follow all community and general guidelines of GitHub and the repositories. You also have to respect the licenses set in this repository as well as in other repositories.

If you have any questions, suggestions or other items you would like to contribute to JET or just discuss, check out the Discussions section of this repository, where you will find the respective areas where you can create your own questions or join in discussions on other things. 
