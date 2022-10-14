# 🗻 StructuredInterchange

Sparkle already introduces some game-changing technologies and systems, which help to create plugins, which are thin in their code, but huge on their productive impact.

Things like the Interchanges, InterchangeStructures, and the strong coroutines build inside the system itself, are helping, to build a solid foundation for this technology, the StructuredInterchanges.

StructuredInterchanges are Interchanges, but instead of relying on a completion and a execution part side-by-side, the StructuredInterchanges are combining both sides into one single, easy-to-use, system, which helps to create huge structures, with small code.&#x20;

## Create StructuredInterchange

There are 3 different kinds of StructuredInterchanges:

* StructuredInterchange
* StructuredPlayerInterchange
* StructuredConsoleInterchange

All of them are full-blown StructuredInterchanges, the difference is at the executor type.

If you access the `executor` property inside the StructuredInterchange, you will get a InterchangeExecutor. If you access it inside a StructuredPlayerInterchange, you will get an Player and of course, if you access it inside a StructuredConsoleInterchange, you will get a ConsoleCommandSender.



## InterchangeStructure

{% hint style="info" %}
This section builds on top of the knowledge, presented in InterchangeStructure
{% endhint %}

Now the challenge is, to make the InterchangeStructure work, like an execution-based completion tree. To do that, some additional functions and properties have been added to the whole system.

Here are the most presented:

### execution(...)

If you want to make a branch 'executable', so that the user can choose to execute it with the command, you can use the execution function.

This function defines the code, which will be executed if everything is entered correctly, according to the used CompletionAssets.

The code block, which the execution function needs, have to return an InterchangeResult, which indicates the result of executing this branch.

### concludedExecution(...)

This function is like the execution function, but with one additional parameter inside the function: the result parameter.

This parameter is by default set to SUCCESS. This replaces the need of returning the result inside an execution block because now it is concluded directly at the start because the return is always the value of the result parameter.

### label(...)

The label function is a way, to give your input parameter(s) a grouping name, which will be displayed inside the usage information. A name, which indicates, what your wanting from the command executer, would be extremely helpful if the amount or complexity is getting out of hand.

Here is an small example:

```kotlin
buildInterchangeStructure {
   branch {
      addContent(CompletionAsset.DOUBLE, CompletionAsset.LONG)
   }
}
```

Now the syntax is looking like:

```
/command <sparkle:DOUBLE>|<sparkle:LONG>
```

But, because we want to help our customers, understand our needs, we use the label function:

```kotlin
buildInterchangeStructure {
   branch {
      addContent(CompletionAsset.DOUBLE, CompletionAsset.LONG)
      label("Number")
   }
}
```

Now the syntax is looking like this:

```
/command <Number>
```

This quite simply describes our needs, and is making the whole process, of understanding the process of the command, way easier!

### cooldown(...)

Sometimes, you want, that a user is only capable of executing your interchange again, after some time. For example, if huge computational power is happening, when the player is executing this, or the player is receiving something, due to the execution of the interchange.

The duration is defined, via a Kotlin duration, so you can use simple code like `5.seconds` as the parameter input, to define, that a user has to wait 5 seconds until he can execute the interchange again.

Here is a small example:

```kotlin
buildInterchangeStructure {
   branch {
      cooldown(5.seconds)
   }
}
```

