package bot.engine

import bot.plugin.Plugin
import bot.plugin.Plugin.Companion.PRIORITY_PREPROCESSOR
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.concurrent.ConcurrentHashMap

class Pipeline {

    val pluginSessions = ConcurrentHashMap<Plugin, HashSet<Session>>()

    fun installPlugin(plugin: Plugin) {
        pluginSessions[plugin] = HashSet()
    }

    fun process(update: Update) {
        pluginSessions.keys
            .filter { it.priority == PRIORITY_PREPROCESSOR }
            .forEach { it.process(update) }

        val session = createSession(update)

        if (!passToSession(update, session))
            passToPlugin(update)
    }

    private fun passToSession(update: Update, session: Session) : Boolean {
        var isConsumed = false
        for (plugin in pluginSessions.keys)
            if (pluginSessions[plugin]!!.contains(session))
            {
                plugin.process(update)
                isConsumed = true
            }
        return isConsumed
    }

    private fun passToPlugin(update: Update) {
        for (plugin in pluginSessions.keys
            .filter { it.priority != PRIORITY_PREPROCESSOR }
            .sortedBy { it.priority }
            .reversed())
            if (plugin.canProcess(update))
                if (!plugin.process(update))
                    break
    }

}