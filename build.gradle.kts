import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.serialization") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("org.jetbrains.dokka") version "1.5.0"
    id("maven-publish")
}

group = "de.jet"
version = "1.0-BETA-1"

var host = "github.com/TheFruxz/JET"

repositories {

    mavenCentral()

    // PaperMC

    maven("https://papermc.io/repo/repository/maven-public/")

}

dependencies {

    // Kotlin

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.20")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    // PaperMC

    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")

    // External

    implementation("org.ktorm:ktorm-core:3.4.0") // KTorm

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

    publications.create("JET", MavenPublication::class) {

        from(components["kotlin"])
        artifactId = "jet"
        version = version.toLowerCase()

    }

}

tasks.shadowJar {
    archiveBaseName.set("JET")
    archiveClassifier.set("COMPLETE")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

tasks.processResources {
    expand("version" to project.version, "name" to project.name, "website" to "https://$host")
}