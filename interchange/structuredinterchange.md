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
This section builds ontop of the knowledge, presented in InterchangeStructure
{% endhint %}

Now the challange is, to make the InterchangeStructure work, like an execution based completion tree. To do that, some additional functions and properties have been added to the whole system.

Here are the most presented:

### execution(...)

If you are getting into a branch by using the interchange
