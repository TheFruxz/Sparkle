---
description: A place to test your code with ease!
---

# 🏜 SandBox

Imagine a place, where code can be quickly inserted and tested, without having to create a technical structure, which will be stroyed, immediately, after the testing is done...

Let me introduce you to the Sparkle SandBox system, here you can test your code, without having to create complex structures or crazy mechanisms, to test your code.

## The control

<figure><img src="../.gitbook/assets/image (1).png" alt=""><figcaption><p>Output of the <code>/sandbox list</code> interchange</p></figcaption></figure>

Sandboxes can be viewed and managed via the /sandbox interchange.

With `/sandbox list` you can see a list full of sandboxes. All the registered sandboxes are listed here, with each having an indicator, showing their hosting app.

You can run every sandbox with `/sandbox run <sandbox>` to execute their stored code.

## The code

```kotlin
quickSandBox(SparkleApp.instance, "MySandbox") {
   executor.sendMessage("WOW! This is my experimental code!")
}
```

This code creates a new sandbox, which can be accessed, directly after this chunk of code was executed. You can do this anywhere you want, from the hello function of your app to a special interchange, which should register this sandbox, if it gets executed.

Now you can add experimental code testing areas from anywhere you want!
