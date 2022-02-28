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

    maven("https://papermc.io/repo/repository/maven-public/") // PaperMC
    maven("https://mvn.intellectualsites.com/content/repositories/thirdparty/")

}

dependencies {

    // Internal

    implementation(project(":JET-JVM"))
    shadow(project(":JET-JVM"))

    // Kotlin

    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    shadow(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    // External

    implementation("org.ktorm:ktorm-core:3.4.1") // KTorm
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT") // PaperMC

    compileOnly("com.arcaniax:HeadDatabase-API:1.3.1") // Head-Database

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
