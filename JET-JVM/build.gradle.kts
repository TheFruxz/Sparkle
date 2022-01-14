import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("org.jetbrains.qodana")
    id("com.github.johnrengelman.shadow")
    id("maven-publish")
}

var host = "github.com/TheFruxz/JET"

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.0-alpha6")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
    kotlinOptions.freeCompilerArgs += "-Xunrestricted-builder-inference"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.io.path.ExperimentalPathApi"
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

    publications.create("JET-JVM", MavenPublication::class) {

        from(components["kotlin"])

        artifact(dokkaJavadocJar)
        artifact(dokkaHtmlJar)
        artifact(source)

        artifactId = "jet-jvm"
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
