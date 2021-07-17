# ![Quad - The Kotlin-Based Framework for Next-Gen Paper Plugins](https://user-images.githubusercontent.com/28064149/125201860-3dbe5e00-e271-11eb-8a14-0cb856dcfdee.gif)
[![forthebadge](https://forthebadge.com/images/badges/built-by-developers.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/open-source.svg)](https://forthebadge.com)

<br>

## QUAD

QUAD is a Kotlin based framework developed for the Paper platform, which is based on Bukkit & Spigot.
QUAD is designed to simplify development with both simple and complex steps, and provide various technologies for securing and structuring so that the final product is secure, stable, and of simpler quality.

## Version Policy

QUAD is about security, quality and stability. That's why we always use the latest Minecraft version to offer all this as soon as possible. We always use the latest paper version so that patches and fixes contribute to these factors.

## Setup

### Repository

How can I use QUAD in my own projects? For this you need to know what your project is based on, or should be based on.
We ourselves recommend that you use `Gradle Kotlin` in all your projects, but you can also use other systems like `Gradle` and `Maven`!

#### `Gradle Kotlin`
##### Repository 
```kotlin
maven("https://maven.pkg.github.com/QuadMC/QUAD") {
    credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
    }
}
```

##### Dependency
```kotlin
implementation("io.quad:quad:<version>")
```

## Version

Since we always try to use the latest versions as soon as possible, as already described in the point 'Version Policy', current versions quickly become obsolete after half a year, so we will soon release a list of versions, where it will be shown exactly how long a certain version is still supported.

## Contribution

Of course, you can also participate in QUAD and contribute to the development. However, please follow all community and general guidelines of GitHub and the repositories. You also have to respect the licenses set in this repository as well as in other repositories.

If you have any questions, suggestions or other items you would like to contribute to QUAD or just discuss, check out the Discussions section of this repository, where you will find the respective areas where you can create your own questions or join in discussions on other things. 
