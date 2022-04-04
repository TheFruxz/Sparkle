import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

repositories {

    mavenCentral()

}

dependencies {

    compileOnly(project(":JET-JVM"))
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))

    compileOnly("io.ktor:ktor-client-core:1.6.8")

    compileOnly("net.kyori:adventure-api:4.10.1")
    compileOnly("net.kyori:adventure-text-serializer-legacy:4.10.1")
    compileOnly("net.kyori:adventure-text-minimessage:4.10.1")

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

}