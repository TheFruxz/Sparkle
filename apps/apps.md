---
description: Plugins, but cooler 😎
---

# 📱 Apps

## What are Apps?

Apps are basically plugins, but with many systems and features in the background, making the Apps a great foundation for your ideas and production code!

The apps are also powering the Components, Services, Interchanges, and Coroutine-based architecture, surrounding everything.

## The control

<figure><img src="../.gitbook/assets/image (2).png" alt=""><figcaption><p>Output of the <code>/app list</code> interchange</p></figcaption></figure>

The apps can be viewed and managed via the /app interchange.

With `/app list` you can see a list full of apps. All the running and currently not running apps are listed here, with each having multiple indicators, showing their current status and compatibility.

## The code

```kotlin
class MyApp : App() {

   override val appIdentity = "MyApp"

   override val label: String = "WOW; the App!"

   override val companion: AppCompanion<out App> = Companion

   override val appCache: AppCache = MyCache

   override suspend fun hello() {
      // Hello!
   }

   companion object : AppCompanion<MyApp>() {

   override val predictedIdentity = "MyApp"

   }

}

object MyCache : AppCache {

   override fun dropEntityData(entityIdentity: UUID, dropDepth: CacheDepthLevel) = empty()

   override fun dropEverything(dropDepth: CacheDepthLevel) = empty()

}
```

Apps are separated into three different segments: The app, the AppCompanion, and the AppCache.

## The `AppCache`

A cleaning service is regularly cleaning the caches of every app, so if you put a cleaning instruction inside your AppCache, the cleaning processing can clean your app caches automatically.

You can also choose to override every cleaning function with empty()!

## The `AppCompanion`

The app companion automatically creates a public instance property, a coroutineScope, and an `identityKey`.

You only have to provide a `predictedIdentity`, which has to be the same as the identity of the app itself!

## The App

### Start creating

To create an app, you have to define some stuff, firstly give your app an identity!

```kotlin
override val appIdentity = "MyApp"
```

Additionally, you have to define a display name for your app:&#x20;

```kotlin
override val label: String = "WOW; the App!"
```

After that, add your companion, which has to be an AppCompanion, out of your own app:

```kotlin
override val companion: AppCompanion<out App> = Companion
```

And your app cache too:

```kotlin
override val appCache: AppCache = MyCache
```

After all of that, you can now write your app, there are different overrideable functions available:

* hello() which is the onEnable equivalent,
* preHello(), which is onLoad
* and bye(), which is onDisable!

Only hello() is forced, to be overridden, because in hello() you can add interchanges, components, services, and more!

### Add content

To add things like Components and Interchanges, you have to use the hello() function body, to add them as you would do in normal plugins.

<details>

<summary>The add(...) function</summary>

With the add(...) function you can add:

* Components
* Interchanges
* and EventHandlers

</details>

<details>

<summary>The register(...) function</summary>

With the register(...) function you can register:

* Services

</details>

<details>

<summary>The start(...) function</summary>

With the start(...) function you can start:

* Services
* Components

</details>

<details>

<summary>The stop(...) function</summary>

With the stop(...) function, you can stop:

* Services
* Components

</details>

<details>

<summary>The unregister(...) function</summary>

With the unregister(...) function, you can unregister:

* Services
* Components

</details>

### Example

Look at the code of the SparkleApp.kt, to see the Sparkle app in action and learn a bit from the code, how it works in a real use case!
