import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    `maven-publish`
}

var host = "github.com/TheFruxz/MoltenKT"

repositories {
    mavenCentral()
}

dependencies {

    // Kotlin

    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.slf4j:slf4j-api:2.0.0")

    implementation("org.jetbrains.exposed:exposed-core:0.39.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.39.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.39.2")

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

    publications.create("MoltenKT-Core", MavenPublication::class) {

        from(components["kotlin"])

        artifact(dokkaJavadocJar)
        artifact(dokkaHtmlJar)
        artifact(source)

        artifactId = "moltenkt-core"
        version = version.toLowerCase()

    }

}

tasks {

    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.time.ExperimentalTime"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.ExperimentalStdlibApi"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.io.path.ExperimentalPathApi"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    }

}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
    withJavadocJar()
    withSourcesJar()
}