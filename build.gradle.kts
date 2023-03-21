import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
    id("org.jetbrains.dokka") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
}

var host = "github.com/TheFruxz/Sparkle"

version = "1.0.0-PRE-22"
group = "de.fruxz"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-public/") // PaperMC
    maven("https://libraries.minecraft.net") // Minecraft (Brigadier)
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PlaceholderAPI
}

val ascendVersion = "20.0.0"
val stackedVersion = "4.0.0"

dependencies {

    // Internal

    implementation("com.github.TheFruxz:Ascend:$ascendVersion")
    implementation("com.github.TheFruxz:Stacked:$stackedVersion")

    // Kotlin

    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // External
    implementation(kotlin("stdlib"))

    implementation("com.mojang:brigadier:1.0.500")
    implementation("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT") // PaperMC

    setOf(
        "io.ktor:ktor-client-cio:2.2.4",
        "io.ktor:ktor-client-core-jvm:2.2.4",
        "io.ktor:ktor-serialization-kotlinx-json:2.2.4",
        "io.ktor:ktor-client-content-negotiation:2.2.4",

        "net.kyori:adventure-api:4.12.0",
        "net.kyori:adventure-text-serializer-legacy:4.12.0",
        "net.kyori:adventure-text-minimessage:4.12.0",
        "net.kyori:adventure-text-serializer-gson:4.12.0",
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

    shadow(kotlin("stdlib"))
    shadow(kotlin("reflect"))

    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    shadow("net.kyori:adventure-text-serializer-plain:4.12.0")

    shadow("org.jetbrains.exposed:exposed-core:0.41.1")
    shadow("org.jetbrains.exposed:exposed-dao:0.41.1")
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