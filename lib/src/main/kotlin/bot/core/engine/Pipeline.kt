package bot.core.engine

import bot.core.plugin.Plugin
import bot.core.processor.Processor
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.concurrent.ConcurrentHashMap

class Pipeline {

    val pluginSessions = ConcurrentHashMap<Plugin, HashSet<Session>>()
    val processors = mutableListOf<Processor>()

    fun installPlugin(plugin: Plugin) {
        pluginSessions[plugin] = HashSet()
    }

    fun process(update: Update) {
        val session = createSession(update)

        runProcessors(update)

        passToSession(update, session)
        passToPlugin(update)
    }

    private fun runProcessors(update: Update)
    {
        for (item in processors)
        {
            if (!item.processMessage(update))
                return
        }
    }

    private fun passToSession(update: Update, session: Session) {
        pluginSessions.keys.forEach { plugin ->
            if (pluginSessions[plugin]!!.contains(session)) {
                plugin.process(update)
                return
            }
        }
    }

    private fun passToPlugin(update: Update) {
        pluginSessions
            .keys
            .first { plugin -> plugin.canProcess(update) }
            .process(update)
    }

    fun closeSession(session: Session)
    {
        pluginSessions.forEach{
            it.value.remove(session)
        }
    }

}