import org.gradle.api.JavaVersion.VERSION_17

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("maven-publish")
}

var host = "github.com/JustEverythingTweaked/JET"

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
    kotlinOptions.freeCompilerArgs += "-Xunrestricted-builder-inference"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.io.path.ExperimentalPathApi"
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

    publications.create("JET-NATIVE", MavenPublication::class) {

        from(components["kotlin"])
        artifactId = "jet-native"
        version = version.toLowerCase()

    }

}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
}