---
description: How to install Sparkle
---

# 🔩 Installation

## Install it on your Server

This section is about, how you can install the Sparkle Plugin on your Server.

### Requirements

Firstly, we have to check, that your server-installation is matching the requirements, your server has to:

* Be a Paper server or a server, that is based on Paper, like Purpur!
* Run on the Minecraft-Version 1.19.2 or newer

### Download the Plugin

Now you have to download the required Sparkle Plugin, which you can install on your server.

Currently, you have 2 main platforms available, which you can choose from if you want to download the plugin. The [GitHub](https://github.com/TheFruxz/Sparkle) and the [Modrinth](https://modrinth.com/plugin/sparkle-kt) platform.

There are generally 2 different files available for you. The <mark style="background-color:orange;">Sparkle-\*.jar</mark> and the <mark style="background-color:orange;">Sparkle-\*-Runnable.jar</mark>&#x20;

Both of these plugin files have Sparkle, but the file without the Runnable suffix doesn't contain the required libraries. What? Yes, the Sparkle comes with the Libraries included, which Sparkle needs to run. You can see a full list of included dependencies on the GitHub page.

So, the Runnable contains all of these dependencies and the file without the Runnable does not. <mark style="color:red;">Normally you WANT the Runnable</mark>, but in some cases, you have to choose the Sparkle file without the Runnable. If this case is true, you have to provide the missing libraries on your own!

Now you only have to move the Sparkle file into your plugins folder and you're ready to go!

## Install it in your Project

You want to develop your plugin on the basis of Sparkle? Then we have to start with the setup process!

You can find a detailed description of the setup process on the GitHub page, but in the most cases you want to add it to a Gradle project via jitpack.

### Setup

Firstly, add the maven repository of JitPack to your Gradle project:

```groovy
repositories {
   maven { url 'https://jitpack.io' }
}
```

After we did that, you need to add the required dependencies to your project:

```groovy
dependencies {
   implementation 'com.github.TheFruxz:Ascend:<ascend-version>'
   implementation 'com.github.TheFruxz:Stacked:<stacked-version>'
   implementation 'com.github.TheFruxz:Sparkle:<sparkle-version>'
}
```

### What is Ascend & Stacked?

&#x20;In the example above you can see, that we use Ascend and Stacked beside Sparkle, these are libraries, that are used in Sparkle and can also help you to develop Stuff.

## Configuration

Every file, created/stored/managed via Sparkle is contained inside the `/SparkleApps` folder, which is located inside the root folder, of your Minecraft-server instance.

In this folder, each application and component has its own folder, which contains data stored and managed by them.

The folder of the Sparkle framework itself is called `main@sparkle`, and in it are the files contained, to modify the behavior of the Sparkle framework, like managing which components are allowed to load, or which prefix is used to display at transmissions.
