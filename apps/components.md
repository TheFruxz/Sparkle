---
description: Package your code in individual sections!
---

# 🚞 Components

## Purpose

Sometimes you have to disable one or more features because you currently don't need/want them, or they currently do not work like expected and it is better to disable them completely.

Removing them immediately is often the best solution, but in normal code, this can be quite hard, or even impossible, on runtime!

Here are the components, which are allowing you, to put your code inside little parts of the plugin, which can be individually managed and viewed, so you can disable specific parts of the plugin, without having to update the plugin immediately.

It can also help, to provide features, that only a specific group of people need, so that the people, which want the specific features, can choose to enable these features.

## The control

<figure><img src="../.gitbook/assets/image (1).png" alt=""><figcaption><p>Output of the <code>/component list</code> interchange</p></figcaption></figure>

The components can be viewed and managed via the /component interchange.

With `/component list` you can see a list full of components. All the running and currently not running components are listed here, with each having multiple indicators, showing their current status and behavior.

## The code

```kotlin
class MyComponent : Component(
   behaviour = RunType.DISABLED,
   isExperimental = false,
   preferredVendor = null,
) {

   override val label = "MyComponent"

   override suspend fun start() {
      // start logic
   }

   override suspend fun stop() {
      // stop logic
   }

}
```

### #behavior

The behavior constructor parameter defines, how the component is reacting to a start & stop!

These are the available RunType behaviors:

{% tabs %}
{% tab title="DISABLED" %}

{% endtab %}

{% tab title="AUTOSTART_IMMUTABLE" %}

{% endtab %}

{% tab title="AUTOSTART_MUTABLE" %}

{% endtab %}

{% tab title="ENABLED" %}

{% endtab %}
{% endtabs %}
