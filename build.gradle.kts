import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.serialization") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("org.jetbrains.dokka") version "1.5.0"
    id("maven-publish")
}

group = "io.quad"
version = "1.0-BETA-1"

repositories {

    mavenCentral()

    // PaperMC

    maven("https://papermc.io/repo/repository/maven-public/")

}

dependencies {

    // Kotlin

    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    // PaperMC

    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")

    // External

    implementation("org.ktorm:ktorm-core:[3.4.1,)") // KTorm

}

publishing {

    repositories {

        mavenLocal()

        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/TheFruxz/QUAD")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }

    }

    publications.create("QUAD", MavenPublication::class) {

        from(components["kotlin"])
        artifactId = "quad"
        version = version.toLowerCase()

    }

}

tasks.shadowJar {
    archiveBaseName.set("QUAD")
    archiveClassifier.set("COMPLETE")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

tasks.processResources {
    expand("version" to project.version)
}