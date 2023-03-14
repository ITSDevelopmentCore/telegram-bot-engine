plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    `java-library`
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

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
