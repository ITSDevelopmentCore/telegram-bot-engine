package bot.engine

import bot.plugin.Plugin
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.api.objects.Update

class Engine(private val credentials: Credentials) : TelegramLongPollingBot(credentials.botToken) {

    val pipeline = Pipeline()

    override fun onUpdateReceived(update: Update) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO)
            {
                pipeline.process(update)
            }
        }
    }

    inline fun <reified T : Plugin> installPlugin(plugin: T, configuration: T.() -> Unit) {
        logger.info("Starting installation of ${T::class.java.simpleName} Plugin")

        plugin.apply(configuration)
        pipeline.installPlugin(plugin)

        logger.info("Plugin ${T::class.java.simpleName} installed successfully")
    }


    inline fun <reified T : Plugin> installPlugin(plugin: T) {
        logger.info("Starting installation of ${T::class.java.simpleName} Plugin" )

        pipeline.installPlugin(plugin)

        logger.info("Plugin ${T::class.java.simpleName} installed successfully")
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


}

val logger: Logger by lazy {
    LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
}


