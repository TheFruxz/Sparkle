plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
    id("io.papermc.paperweight.userdev") version "1.7.3"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    `maven-publish`
}

var host = "github.com/TheFruxz/Sparkle"

version = "2024.4-dev"
group = "dev.fruxz"

repositories {

    mavenCentral()

    maven("https://repo.fruxz.dev/releases") {
        name = "fruxz.dev"
    }

    maven("https://jitpack.io") {
        name = "JitPack"
    }

    maven("https://libraries.minecraft.net") {
        name = "Minecraft Libraries"
    }

}

val includedDependencies = mutableListOf<String>()

fun Dependency?.deliver() = this?.apply {
    val computedVersion = version ?: kotlin.coreLibrariesVersion
    includedDependencies += "${group}:${name}:${computedVersion}"
}

dependencies {

    // Internal

    api("dev.fruxz:ascend:2024.2.2").deliver()
    api("dev.fruxz:stacked:2024.1.1").deliver()
    api("dev.fruxz:kojang:1.1.2").deliver()
    api("dev.fruxz:brigadikt:2024-indev-1").deliver()

    // Kotlin

    implementation(kotlin("stdlib")).deliver()
    implementation(kotlin("reflect")).deliver()
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    // External

    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation("com.mojang:brigadier:1.0.18")

    implementation("io.ktor:ktor-client-cio:2.3.9").deliver()
    implementation("io.ktor:ktor-client-core-jvm:2.3.9").deliver()
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0").deliver()
    implementation("io.ktor:ktor-client-content-negotiation:3.0.0").deliver()

}

tasks {

    processResources {
        expand(
            "version" to project.version,
            "name" to project.name,
            "website" to "https://$host",
            "delivery" to includedDependencies.joinToString("\n"),
        )
    }

    test {
        useJUnitPlatform()
    }

    runServer {
        this.minecraftVersion("1.20.4")
    }

}

publishing {

    repositories {
        mavenLocal()
    }

    publications.create("Sparkle", MavenPublication::class) {

        from(components["kotlin"])

        artifactId = "sparkle"
        version = version.lowercase()

    }

}

configure<SourceSetContainer> { // allowing java files appearing next to kotlin files
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}