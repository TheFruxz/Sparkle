# 🔩 InterchangeStructure

{% hint style="info" %}
**NOTE!** _InterchangeStructure_ is the definition of an Interchange and the _StructuredInterchange_ is the computational Interchange, that fully relies on the InterchangeStructure, as its complete definition.
{% endhint %}

{% hint style="warning" %}
This topic can look complex, but is really easy to understand if you want to get into this!
{% endhint %}

With the InterchangeStructure you simply define the structure and in some cases the content of an Interchange. This applies to the classic Interchanges, but also to the much more enhanced StructuredInterchanges.

What this is, is like the plan of the interchange:

<figure><img src="../.gitbook/assets/Ohne Titel 3.png" alt=""><figcaption><p>For example, you can use /&#x3C;command> list or /&#x3C;command> &#x3C;Player> info</p></figcaption></figure>

The InterchangeStructures are built like inverted trees, you have the root, where everything belongs, and the different branches, which you can use.

But how you can define such a structure? Here is a code example, representing the image above:

{% hint style="info" %}
This example is located in the completion parameter of an Interchange, see [Interchanges](interchanges.md)
{% endhint %}

```kotlin
buildInterchangeStructure {

   branch {
      addContent("list")
   }

      branch {
         addContent(CompletionAsset.ONLINE_PLAYER_NAME)

         branch {
            addContent("info")
         }
			
         branch {
            addContent("heal")
         }
			
         branch { 
            addContent("kill")
         }

      }

}
```

This code builds the interchange structure of the image above. You can use /... list or /... player info/heal or kill, to execute this interchange.

But there are different things, some content is specified via a String and another is specified via a CompletionAsset.

The content specified via the String is only that. This is a 'static' input, because it is, what it is. The CompletionAsset in comparison is an 'adaptive' input. It automatically caches, updates, and checks its content. In this example, every time the Completion gets rendered, this displays the names of all current online players.

Not only can this be used multiple times, to display and request player names, but also can be used, to transform input from the user to the provided type. In this example, you can transform the user input in this stage into a Player.

Transformations via CompletionAssets are more interesting, when you work with CompletionAssets on an interactive basis, read more in [StructuredInterchange](structuredinterchange.md)!

In general, this type of building, even in complex situations, allows you to build your completion with ease and cleaner then ever!
