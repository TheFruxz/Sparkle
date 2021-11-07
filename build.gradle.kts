import org.gradle.api.JavaVersion.VERSION_16

plugins {
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
    id("org.jetbrains.dokka") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("maven-publish")
}

allprojects {

    group = "de.jet"
    version = "1.0-BETA-5"

    repositories {
        mavenCentral()
    }

}

java {
    sourceCompatibility = VERSION_16
    targetCompatibility = VERSION_16
}