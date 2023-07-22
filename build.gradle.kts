plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    `java-library`
    `maven-publish`
    kotlin("plugin.serialization") version "1.8.21"
}

repositories {
    mavenCentral()
}

project.extra["version_telegram_bot_library"] = "6.5.0"
project.extra["version_kotlinx_serialization"] = "1.5.1"

dependencies {
    /**
     * Telegram Bot Libraries
     */
    implementation("org.telegram:telegrambots:${project.extra["version_telegram_bot_library"]}")
    implementation("org.telegram:telegrambots-meta:${project.extra["version_telegram_bot_library"]}")
    implementation("org.telegram:telegrambotsextensions:${project.extra["version_telegram_bot_library"]}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("ch.qos.logback:logback-core:1.4.5")

    /**
     * KotlinX
     */
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${project.extra["version_kotlinx_serialization"]}")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ITSDevelopmentCore/telegram-bot-engine")
            credentials {
                username = project.findProperty("github.user") as String
                password = project.findProperty("github.key") as String
            }
        }
    }

    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourceJar) {
                classifier = "sources"
            }
            groupId = "its.development.libraries"
            artifactId = "telegram-bot-engine"
            version = project.findProperty("version").toString()
        }
    }

}