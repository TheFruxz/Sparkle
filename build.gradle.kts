plugins {
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
    id("org.jetbrains.dokka") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("maven-publish")
}

allprojects {

    group = "de.jet"
    version = "1.0-BETA-4"

    repositories {
        mavenCentral()
    }

}