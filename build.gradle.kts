import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("org.jetbrains.dokka") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("org.jetbrains.qodana") version "0.1.13"
    id("maven-publish")
}

allprojects {

    group = "de.jet"
    version = "1.0-BETA-7"

    repositories {
        mavenCentral()
    }

}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("../docs/"))
}