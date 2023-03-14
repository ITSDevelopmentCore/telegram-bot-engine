package its.telegram.bot.core.engine

import its.telegram.bot.core.plugin.Plugin
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.concurrent.ConcurrentHashMap

class Pipeline {

    val pluginSessions = ConcurrentHashMap<Plugin, HashSet<BotUser>>()

    fun installPlugin(plugin: Plugin) {
        pluginSessions[plugin] = HashSet()
    }

    fun process(update: Update) {

        val user = createUser(update)

        pluginSessions.keys.forEach { plugin ->
            if (pluginSessions[plugin]!!.contains(user)) {
                plugin.process(update)
                return
            }
        }

        if (update.message != null && update.message.text.equals("/start")) {
            pluginSessions.values.forEach {
                it.remove(user)
            }
        }

        pluginSessions
            .keys
            .first { plugin -> plugin.canProcess(update) }
            .process(update)

    }


}