import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.jetbrains.qodana") version "0.1.13"
    `maven-publish`
}

allprojects {

    version = "1.0-PRE-3.3.2"
    group = "de.jet"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("../docs/"))
}