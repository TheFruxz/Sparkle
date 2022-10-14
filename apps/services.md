---
description: Timed code execution
---

# 📦 Services

## Purpose

One additional thing, Sparkle provides, is the service system.

Sometimes you want to schedule code on a regular basis, like a simple cleaning task, but you do not want to waste some code with multiple uncontrollable Bukkit tasks.

A small control center, where you can see all your little tasks, manage them with ease and stay in control, would be extremely nice, right?

## The control

<figure><img src="../.gitbook/assets/image.png" alt=""><figcaption><p>Output of the <code>/service list</code> interchange</p></figcaption></figure>

Services can be viewed and managed via the /service interchange.

With `/service list` you can see a list full of services. All the running and currently not running services are listed here, with each having an indicator, showing their current status and time, how long they have been running.

## The code

```kotlin
class MyService : Service {

   override val label = "MyService"

   override val vendor: App = SparkleApp.instance

   override val serviceTimes: ServiceTimes = ServiceTimes()

   override val serviceActions: ServiceActions = ServiceActions()

   override val iteration: ServiceIteration = {
      // executed code
   }

}
```

### #label

Every service has to own a label, this is the name, which will be displayed in lists and will be used, to create the unique identifier key, so do not use any special characters!

The label should also be helpful, to know, what this service does!

### #vendor

You have to also submit your app instance, under which the service will be executed. It is generally recommended, that you are putting the vendor inside the constructor, so that you will be able to change it, inside your plugin itself, if you want.

```kotlin
class MyService(override val vendor: App = SparkleApp.instance) : Service {

   override val label = "MyService"

   override val serviceTimes: ServiceTimes = ServiceTimes()

   override val serviceActions: ServiceActions = ServiceActions()

   override val iteration: ServiceIteration = {
      // executed code
   }

}
```

### #serviceTimes

How long should it all take? With the `serviceTimes` property, you define exactly that!

The default configuration is like this:

```kotlin
override val serviceTimes: ServiceTimes = ServiceTimes(
   delay = Duration.ZERO,
   interval = Duration.ZERO,
   isAsync = true,
)
```

#### The delay

The delay specifies the time, that is used to wait before the first (and maybe only) run is executed.

If the delay is not positive (greater than ZERO), it just doesn't wait, before the first run is getting executed.

#### The interval

The interval specifies the time, that is used to wait for a new run to be executed after a run is executed, basically the time difference between run-1-end and run-2-start.

If the interval is not positive (greater than ZERO), it just doesn't repeat the execution, so the code, specified in iteration, will only be executed once!

#### isAsync

The isAsync only specifies, if the code, specified in iteration, should be executed asynchronously or synchronously. To do that, the system, described in [Scheduling & Deferral](../timing/scheduling-and-deferral.md), is used!

### #serviceActions

Sometimes, you want to prepare your plugin, before something like a cleaning service is executing its code. But sometimes you want to do something too, if your service is about to crash!

With the serviceActions, you can define the actions executed, on these special events!

```kotlin
override val serviceActions: ServiceActions = ServiceActions(
   onStart = { serviceLogger.info("I, (${key()}), have been started!") },
   onCrash = { _, _ -> serviceLogger.warning("Oh no! I have made an crash! O.O") },
   onStop = { serviceLogger.info("I, (${key()}), have been stopped!") }
)
```

### #iteration

And finally, the most important part, the executed code itself!



