package its.telegram.bot.core.engine

import its.telegram.bot.core.plugin.Plugin
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.api.objects.Update

class Engine(private val credentials: Credentials) : TelegramLongPollingCommandBot() {

    val pipeline = Pipeline()

    override fun processNonCommandUpdate(update: Update) {
        pipeline.process(update)
    }

    inline fun <reified T : Plugin> installPlugin(plugin: T, configuration: T.() -> Unit) {
        println("Starting installation of ${T::class.java.simpleName} Plugin")

        plugin.apply(configuration)
        pipeline.installPlugin(plugin)

        println("Plugin ${T::class.java.simpleName} installed successfully")
    }


    inline fun <reified T : Plugin> installPlugin(plugin: T) {
        println("Starting installation of ${T::class.java.simpleName} Plugin" )

        pipeline.installPlugin(plugin)

        println("Plugin ${T::class.java.simpleName} installed successfully")
    }

    /**
     * Конфигурация бота
     */
    data class Credentials(
        val botName  : String,
        val botToken : String
    )

    override fun getBotUsername(): String {
        return credentials.botName
    }

    override fun getBotToken(): String {
        return credentials.botToken
    }

}

