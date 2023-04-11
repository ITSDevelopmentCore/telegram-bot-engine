package bot.plugin

import bot.engine.Engine
import bot.engine.logger
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * Базовый класс для подключаемых плагинов.
 * Каждый плагин обладает рядом триггеров.
 * Реализация метода canProcess определяет, имеет ли этот плагин триггер на поступивший апдейт.
 */
abstract class Plugin(val engine : Engine) : Comparable<Plugin> {

    override fun compareTo(other: Plugin) = this.priority - other.priority

    var priority : Byte = PRIORITY_NORMAL
        set(value) {
            logger.debug("Для плагина ${this::class.java.simpleName} был установлен приоритет $value")
            field = value
        }

    /**
     * Добавление триггеров на отправление сообщений
     */
    val startTriggers = hashSetOf<String>()

    fun addPluginTrigger(vararg textTrigger : String)
    {
        this.startTriggers.addAll(textTrigger)
    }

    /**
     * Базовый механизм обработки
     */
    open fun canProcess(update: Update) = update.message != null && startTriggers.contains(update.message.text)

    open fun process(update: Update) : Boolean
    {
        if (update.message.photo != null) {
            return processPhoto(update)
        }
        if (update.message != null) {
            return processMessage(update)
        }
        if (update.callbackQuery != null) {
            return processCallbackQuery(update)
        }
        return true
    }

    open fun processPhoto(update: Update) = false

    open fun processCallbackQuery(update: Update) = false

    open fun processMessage(update: Update) = false

    companion object {
        const val PRIORITY_MINIMUM : Byte = 120
        const val PRIORITY_LOW : Byte = 100
        const val PRIORITY_NORMAL : Byte = 75
        const val PRIORITY_HIGH : Byte = 50
        const val PRIORITY_MAXIMUM : Byte = 25
        const val PRIORITY_PREPROCESSOR : Byte = -1
    }
}


