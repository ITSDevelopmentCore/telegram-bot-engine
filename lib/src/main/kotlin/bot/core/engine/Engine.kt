package bot.core.engine

import bot.core.plugin.Plugin
import bot.core.processor.Processor
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.api.objects.Update

class Engine(private val credentials: Credentials) : TelegramLongPollingCommandBot() {

    val pipeline = Pipeline()

    override fun processNonCommandUpdate(update: Update) {
        runBlocking {
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

    inline fun <reified P : Processor> installProcessor(processor: P) {
        logger.info("Starting installation of ${P::class.java.simpleName} Processor" )
        pipeline.processors.add(processor)
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

val logger by lazy {
    LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
}


