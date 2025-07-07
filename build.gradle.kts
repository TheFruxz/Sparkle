import io.papermc.paperweight.util.path

plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    `maven-publish`
}

var host = "github.com/TheFruxz/Sparkle"

version = "2025.7-dev"
group = "dev.fruxz"

repositories {

    mavenCentral()

    maven("https://nexus.fruxz.dev/repository/public/") {
        name = "fruxz.dev"
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

    api("dev.fruxz:ascend:2025.7-8af65e5").deliver()
    api("dev.fruxz:stacked:2025.5-3733615").deliver()
    api("dev.fruxz:brigadikt:2025.4-48276a1-preview").deliver()
    api("dev.fruxz:kojang:1.1.2").deliver()

    // Kotlin

    implementation(kotlin("stdlib")).deliver()
    implementation(kotlin("reflect")).deliver()
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // External

    paperweight.paperDevBundle("1.21.7-R0.1-SNAPSHOT")
    implementation("com.mojang:brigadier:1.0.18")

    implementation("io.ktor:ktor-client-cio:3.1.1").deliver()
    implementation("io.ktor:ktor-client-core-jvm:3.1.1").deliver()
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.1").deliver()
    implementation("io.ktor:ktor-client-content-negotiation:3.1.1").deliver()

}

tasks {

    val generateDependenciesFile = register("generateDependenciesFile") {
        doLast {
            val outputFile = file("src/main/resources/dependencies.txt")
            outputFile.writeText(includedDependencies.joinToString("\n"))
        }
    }

    processResources {
        dependsOn(generateDependenciesFile)
        expand(
            "version" to project.version,
            "name" to project.name,
            "website" to "https://$host",
        )
    }

    test {
        useJUnitPlatform()
    }

    runServer {
        this.minecraftVersion("1.21.7")
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