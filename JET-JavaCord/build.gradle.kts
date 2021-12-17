import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    maven("https://mvnrepository.com/artifact/org.javacord/javacord")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")

    // internal

    implementation(project(":JET-JVM"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    implementation("org.javacord:javacord:3.3.2")

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

    publications.create("JET-JAVACORD", MavenPublication::class) {

        from(components["kotlin"])

        artifact(dokkaJavadocJar)
        artifact(dokkaHtmlJar)

        artifactId = "jet-javacord"
        version = version.toLowerCase()

    }

}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
