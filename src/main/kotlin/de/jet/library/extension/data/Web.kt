package de.jet.library.extension.data

import java.net.URL

fun getWebText(url: String) = URL(url).openStream().bufferedReader().readText()