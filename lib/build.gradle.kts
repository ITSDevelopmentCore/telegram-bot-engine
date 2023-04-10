plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

project.extra["version_telegram_bot_library"] = "6.5.0"

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

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {

    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])

            groupId = "its.development.libraries"
            artifactId = "telegram-bot-engine"
            version = "1.0.4"
        }
    }

    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/ITSDevelopmentCore/telegram-bot-engine")
            credentials {
                username = project.findProperty("github.user") as String
                password = project.findProperty("github.key") as String
            }
        }
    }

}