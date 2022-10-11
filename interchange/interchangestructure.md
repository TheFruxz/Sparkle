# 🔩 InterchangeStructure

{% hint style="info" %}
**NOTE!** _InterchangeStructure_ is the definition of an Interchange and the _StructuredInterchange_ is the computational Interchange, that fully relies on the InterchangeStructure, as its complete definition.
{% endhint %}

{% hint style="warning" %}
This topic can look complex, but is really easy to understand if you want to get into this!
{% endhint %}

## The system

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

## The construction

### Start a new InterchangeStructure

To start a new InterchangeStructure, you can create, there are 3 ways

* Use the InterchangeStructure constructor
* Use the global buildInterchangeStructure function
* or use the global emptyInterchangeStructure function

The last one allows you, to create an empty one, without the helping construction/build process.

### Build your InterchangeStructure

{% hint style="info" %}
The first layer of the InterchangeStructure, also known as the root layer, is also a valid layer. You can apply the same things on the root layer, as you do on other branches.
{% endhint %}

By building an InterchangeStructure, you can choose to keep the default settings, or set it up, the way, you need it.

In the generation of the InterchangeStructure itself, you can pass your needed configurations as the function parameters, or like in the normal branches, later, in the builder block.

#### Sub-Branches

The key element, of working with the StructuredInterchange, is the use of other branches. You want to create multiple possible ways, with which the user could interact.

You can do so, by using the branch function. It accepts multiple parameters:



| Parameter     | Default                             | Description                               |
| ------------- | ----------------------------------- | ----------------------------------------- |
| identity      | way-\<indexByParent>                | The technical name of the branch          |
| path          | \<parentAddress> / \<this identity> | The path of the new branch, to be located |
| configuration | Default configuration               | The configuration state of the Branch     |
| process       | nothing                             | The builder process, to set the branch up |

Here is the code, that powers the branch function

```kts
fun branch(
   identity: String = (parent?.identity ?: "") + "/way-${parent?.subBranches?.size ?: 0}",
   path: Address<InterchangeStructure<EXECUTOR>> = this.address / identity,
   configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
   process: InterchangeStructure<EXECUTOR>.() -> Unit,
)
```

Inside the builder process of the branch function, you can define, what the branch setup is looking like.

#### Branch setup

Besides the possibility, to define another sub-branch inside your branch, you can do so much more, but on this page, only the general Completion stuff is talked about. Additional information, related to the execution part, which is used by the StructuredInterchange, is talked about in the StructuredInterchange section.

#### Branch content

One key thing is currently missing, to let the system work and it is the content.

The content defines, what the user is getting presented as the possible completion and in StructuredInterchanges, what can be the allowed input of the user.

There are multiple ways, to define, which content is used inside the branch. One is to replace the property value of content, like that:

```kts
this.content = emptyList()
```

But this is a bit clunky and can definitely lead to confusion. Another thing that is available to choose from, is the content function. It allows you to define the content of the branch in a functional way, but is in the background the same thing.

```kotlin
this.content(...)
```

The possibly best solution for defining content, if you're generating it inside your build process, is by using the add content function. There you do not have to directly submit the correct type, but you can use the type you're most aware of.

The other benefit of using it, it just simply adds content without replacing the current one, giving you more flexibility.

For the use of the content, you can supply Strings as static content, which will not automatically adapt to the current situation, like that:

```kts
this.addContent("This", "is", "some", "static", "content")
```

But it is also possible to completely adapt the content shown and accepted automatically on the situation, where your customer is currently in:

```kts
this.addContent(CompletionAsset.ONLINE_PLAYER_NAME)
```

This is done, using the [`CompletionAsset`](../apps/components.md) system, which is also its own chapter, you can read through. If you're not interested, in reading it right now, here is the short form:

> CompletionAssets are components of your InterchangeStructure, which adaptivly generates content, based of the current situtation. It is possible to generate it on demand, everytime it gets called, or only one time, if it should be cached.

The default CompletionAssets are directly accessible on the companion object and can be used.

#### Branch configuration

Define, what is allowed and what is not. By using the configure function, you can edit the configuration of the edit branch.

```kotlin
buildInterchangeStructure {
   configure { 
		
   }
}
```

Inside the configure code block you can set the rules and behavior, which the user have to follow, so that the input is accepted as "yes, you followed this branch".

Because if the user's input does not match the requirements, it does not count toward the following branch, because the system tracks, which branch the user is in.

{% hint style="info" %}
The user branch following works like User is _**root -> way-1 -> way-0 -> way-1**_if the user's input at way-0 does not match the requirements, set in the configuration or content, the path would be **root -> way-1** instead of the full one. If the branch is set to infinite, the parameters used for the try of following the other paths, but failed, are used as input parameters for the first way-1 path. **(only for StructuredInterchanges)**
{% endhint %}
