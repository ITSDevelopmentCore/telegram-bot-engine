package bot.engine

import bot.plugin.BasePlugin
import bot.plugin.SessionPlugin
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.concurrent.ConcurrentHashMap

/**
 * Класс - обертка над TelegramLongPollingBot
 */
open class Engine(
     val name: String,
     val token: String
) : TelegramLongPollingBot(token) {

    /**
     * Сессионные плагины
     */
    val sessionPlugins = ConcurrentHashMap<BasePlugin, HashSet<Session>>()

    fun installPlugin(plugin: BasePlugin) {
        sessionPlugins[plugin] = HashSet()
    }

    /**
     * Препроцессоры
     */
    val preprocessors = HashSet<BasePlugin>()

    fun installPreprocessor(plugin: BasePlugin) {
        preprocessors.add(plugin)
    }


    private fun process(update: Update) {
        preprocessors.forEach { preprocessor ->
            if (preprocessor.canProcess(update) && !preprocessor.process(update))
                return
        }

        val session = createSession(update)

        if (session.isEmpty() || !passToSession(update, session))
            passToPlugin(update)
    }

    private fun passToSession(update: Update, session: Session) : Boolean {
        for (plugin in sessionPlugins.keys)
            if (sessionPlugins[plugin]!!.contains(session))
            {
                plugin.process(update)
                return true
            }
        return false
    }

    private fun passToPlugin(update: Update) {
        for (plugin in sessionPlugins.keys.sortedBy { it.priority } )
            if (plugin.canProcess(update))
                if (!plugin.process(update))
                    break
    }


    fun <T : SessionPlugin<*>> pushToPlugin(update : Update, pluginClass : Class<T>){
        val session = createSession(update)
        sessionPlugins
            .filter { entry -> entry.value.contains(session) }
            .firstNotNullOf { entry -> (entry.key as SessionPlugin<*>).endSession(session) }
        sessionPlugins
            .filter { entry -> entry.key::class.java == pluginClass }
            .firstNotNullOf { entry ->
                entry.value.add(session)
                entry.key.processForce(update)
            }
        process(update)
    }

    override fun onUpdateReceived(update: Update) {
        GlobalScope.launch {
            process(update)
        }
    }

    override fun getBotUsername() = name

}

val logger: Logger by lazy {
    LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
}


