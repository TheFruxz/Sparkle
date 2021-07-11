![Quad - The Kotlin-Based Framework for Next-Gen Paper Plugins](https://user-images.githubusercontent.com/28064149/125201860-3dbe5e00-e271-11eb-8a14-0cb856dcfdee.gif)

# Setup

## Repository
```kotlin
maven("https://maven.pkg.github.com/QuadMC/QUAD") {
    credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
    }
}
```

## Dependency
```kotlin
implementation("io.quad:quad:<version>")
```
