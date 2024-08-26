plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.10"
    id("io.papermc.paperweight.userdev") version "1.7.2"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    `maven-publish`
}

var host = "github.com/TheFruxz/Sparkle"

version = "2023.4-dev"
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

val minecraftVersion: String by project
val ascendVersion: String by project
val stackedVersion: String by project
val kojangVersion: String by project
val brigadiktVersion: String by project
val serializationVersion: String by project
val coroutinesVersion: String by project
val brigadierVersion: String by project
val ktorVersion: String by project

val includedDependencies = mutableListOf<String>()

fun Dependency?.deliver() = this?.apply {
    val computedVersion = version ?: kotlin.coreLibrariesVersion
    includedDependencies += "${group}:${name}:${computedVersion}"
}

dependencies {

    // Internal

    api("dev.fruxz:ascend:$ascendVersion").deliver()
    api("dev.fruxz:stacked:$stackedVersion").deliver()
    api("dev.fruxz:kojang:$kojangVersion").deliver()
    api("dev.fruxz:brigadikt:$brigadiktVersion").deliver()

    // Kotlin

    implementation(kotlin("stdlib")).deliver()
    implementation(kotlin("reflect")).deliver()
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // External

    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")
    implementation("com.mojang:brigadier:$brigadierVersion")

    implementation("io.ktor:ktor-client-cio:$ktorVersion").deliver()
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion").deliver()
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion").deliver()
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion").deliver()

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
        this.minecraftVersion(minecraftVersion)
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