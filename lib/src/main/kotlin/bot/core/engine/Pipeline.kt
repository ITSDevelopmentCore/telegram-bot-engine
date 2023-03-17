package bot.core.engine

import bot.core.plugin.Plugin
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.concurrent.ConcurrentHashMap

class Pipeline {

    val pluginSessions = ConcurrentHashMap<Plugin, HashSet<Session>>()

    fun installPlugin(plugin: Plugin) {
        pluginSessions[plugin] = HashSet()
    }

    fun process(update: Update) {
        val session = createSession(update)
        passToSession(update, session)
        passToPlugin(update)
    }


    private fun passToSession(update: Update, session: Session) {
        for (plugin in pluginSessions.keys)
            if (pluginSessions[plugin]!!.contains(session))
                plugin.process(update)
    }

    private fun passToPlugin(update: Update) {
        for (plugin in pluginSessions.keys)
            if (plugin.canProcess(update) && plugin.process(update))
                break
    }

}