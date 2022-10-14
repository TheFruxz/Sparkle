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

Apps are seperated in three different segments: The app, the AppCompanion and the AppCache.

