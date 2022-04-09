import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("com.github.johnrengelman.shadow")
    id("org.jetbrains.qodana")
    `maven-publish`
}

var host = "github.com/TheFruxz/JET"

repositories {

    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-public/") // PaperMC

}

dependencies {

    // Internal

    implementation(project(":JET-JVM"))
    implementation(project(":JET-Unfold"))

    shadow(project(":JET-JVM")) {
        isTransitive = false
    }
    shadow(project(":JET-Unfold")) {
        isTransitive = false
    }

    // Kotlin

    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    // External

    implementation("org.ktorm:ktorm-core:3.4.1") // KTorm

    implementation("io.ktor:ktor-client-core-jvm:2.0.0") // Ktor
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
    implementation("io.ktor:ktor-client-cio:2.0.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.0")

    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT") // PaperMC
    compileOnly("com.arcaniax:HeadDatabase-API:1.3.1") // Head-Database

    // Shadow

    shadow(kotlin("stdlib"))
    shadow(kotlin("stdlib-jdk8"))
    shadow(kotlin("reflect"))
    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    shadow("org.slf4j:slf4j-api:2.0.0-alpha7")
    shadow("org.jetbrains.exposed:exposed-core:0.37.3")
    shadow("org.jetbrains.exposed:exposed-dao:0.37.3")
    shadow("org.jetbrains.exposed:exposed-jdbc:0.37.3")

    shadow("io.ktor:ktor-client-core-jvm:2.0.0") // Ktor
    shadow("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
    shadow("io.ktor:ktor-client-cio:2.0.0")
    shadow("io.ktor:ktor-client-content-negotiation:2.0.0")

    shadow("net.kyori:adventure-api:4.10.1")
    shadow("net.kyori:adventure-text-serializer-legacy:4.10.1")
    shadow("net.kyori:adventure-text-minimessage:4.10.1")

}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
    withJavadocJar()
    withSourcesJar()
}

val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadocPartial)
    from(tasks.dokkaJavadocPartial.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtmlPartial)
    from(tasks.dokkaHtmlPartial.flatMap { it.outputDirectory })
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

    publications.create("JET-Paper", MavenPublication::class) {

        from(components["kotlin"])

        artifact(dokkaJavadocJar)
        artifact(dokkaHtmlJar)
        artifact(source)

        artifactId = "jet-paper"
        version = version.toLowerCase()

    }
}

tasks {

    build {
        dependsOn(shadowJar)
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    named<ShadowJar>("shadowJar") {
        archiveClassifier.set("Runnable")
        configurations = listOf(project.configurations.shadow.get())
    }

    test {
        useJUnitPlatform()
    }

    processResources {
        expand("version" to project.version, "name" to project.name, "website" to "https://$host")
    }

}
