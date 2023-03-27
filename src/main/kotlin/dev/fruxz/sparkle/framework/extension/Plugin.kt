package dev.fruxz.sparkle.framework.extension

import dev.fruxz.sparkle.framework.SparklePlugin

fun SparklePlugin(plugin: SparklePlugin.() -> Unit) = object : SparklePlugin(plugin) { }