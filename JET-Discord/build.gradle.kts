import org.gradle.api.JavaVersion.VERSION_16

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("org.jetbrains.qodana")
    id("maven-publish")
}

var host = "github.com/TheFruxz/JET"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // internal

    implementation(project(":JET-Native"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    
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

    publications.create("JET-DISCORD", MavenPublication::class) {

        from(components["kotlin"])
        artifactId = "jet-discord"
        version = version.toLowerCase()

    }

}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = VERSION_16
    targetCompatibility = VERSION_16
}
