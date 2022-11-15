import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
    id("org.jetbrains.dokka") version "1.7.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
}

var host = "github.com/TheFruxz/Sparkle"

version = "1.0.0-PRE-20"
group = "de.fruxz"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-public/") // PaperMC
    maven("https://libraries.minecraft.net") // Minecraft (Brigadier)
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PlaceholderAPI
}

val ascendVersion = "4.0.0"
val stackedVersion = "3.0.1"

dependencies {

    // Internal

    implementation("com.github.TheFruxz:Ascend:$ascendVersion")
    implementation("com.github.TheFruxz:Stacked:$stackedVersion")

    // Kotlin

    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // External

    @Suppress("DependencyOnStdlib") implementation(kotlin("stdlib"))

    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation("com.mojang:brigadier:1.0.500")
    implementation("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT") // PaperMC

    setOf(
        "io.ktor:ktor-client-cio:2.1.3",
        "io.ktor:ktor-client-core-jvm:2.1.3",
        "io.ktor:ktor-serialization-kotlinx-json:2.1.3",
        "io.ktor:ktor-client-content-negotiation:2.1.3",

        "net.kyori:adventure-api:4.11.0",
        "net.kyori:adventure-text-serializer-legacy:4.11.0",
        "net.kyori:adventure-text-minimessage:4.11.0",
        "net.kyori:adventure-text-serializer-gson:4.11.0",
    ).forEach {
        implementation(it)
        shadow(it)
    }

    // Shadow

    shadow("com.github.TheFruxz:Ascend:$ascendVersion") {
        isTransitive = false
    }
    shadow("com.github.TheFruxz:Stacked:$stackedVersion") {
        isTransitive = false
    }

    @Suppress("DependencyOnStdlib") shadow(kotlin("stdlib"))
    shadow(kotlin("reflect"))

    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    shadow("org.slf4j:slf4j-api:2.0.3")
    shadow("net.kyori:adventure-text-serializer-plain:4.11.0")

    shadow("org.jetbrains.exposed:exposed-core:0.41.1")
    shadow("org.jetbrains.exposed:exposed-dao:0.40.1")
    shadow("org.jetbrains.exposed:exposed-jdbc:0.41.1")

}

tasks {

    build {
        dependsOn(shadowJar)
    }

    named<ShadowJar>("shadowJar") {
        archiveClassifier.set("Runnable")
        configurations = listOf(project.configurations.shadow.get())
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    dokkaHtml.configure {
        outputDirectory.set(buildDir.resolve("../docs/"))
    }

    processResources {
        expand(
            "version" to project.version,
            "name" to project.name,
            "website" to "https://$host"
        )
    }

    test {
        useJUnitPlatform()
    }

}

val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-doc")
}

val source by tasks.register<Jar>("sourceJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

publishing {
    repositories {
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.$host")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications.create("Sparkle", MavenPublication::class) {

        from(components["kotlin"])

        artifact(dokkaJavadocJar)
        artifact(dokkaHtmlJar)
        artifact(source)

        artifactId = "sparkle"
        version = version.toLowerCase()

    }
}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
    withJavadocJar()
    withSourcesJar()
}