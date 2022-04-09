import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

repositories {

    mavenCentral()

}

dependencies {

    api(project(":JET-JVM"))

    api("net.kyori:adventure-api:4.10.1")
    api("net.kyori:adventure-text-serializer-legacy:4.10.1")
    api("net.kyori:adventure-text-minimessage:4.10.1")
    api("io.ktor:ktor-client-core-jvm:2.0.0")

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withJavadocJar()
    withSourcesJar()
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

}