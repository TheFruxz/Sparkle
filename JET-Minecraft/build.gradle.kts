import org.gradle.api.JavaVersion.VERSION_16

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("com.github.johnrengelman.shadow")
    id("org.jetbrains.qodana")
    id("maven-publish")
}

var host = "github.com/TheFruxz/JET"

repositories {

    // PaperMC

    maven("https://papermc.io/repo/repository/maven-public/")

    maven("https://mvn.intellectualsites.com/content/repositories/thirdparty/")

}

dependencies {

    // Internal

    implementation(project(":JET-Native"))

    // PaperMC

    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")

    // Kotlin
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    // External

    implementation("org.ktorm:ktorm-core:3.4.1") // KTorm

    compileOnly("com.arcaniax:HeadDatabase-API:1.3.0") // Head-Database

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
    kotlinOptions.freeCompilerArgs += "-Xunrestricted-builder-inference"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.io.path.ExperimentalPathApi"
}

java {
    sourceCompatibility = VERSION_16
    targetCompatibility = VERSION_16
}

tasks.processResources {
    expand("version" to project.version, "name" to project.name, "website" to "https://$host")
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

    publications.create("JET-Minecraft", MavenPublication::class) {

        from(components["kotlin"])
        artifactId = "jet-minecraft"
        version = version.toLowerCase()

    }

}

tasks.shadowJar {
    archiveBaseName.set("JET-Minecraft")
    archiveClassifier.set("Runnable")
}

tasks.test {
    useJUnitPlatform()
}
