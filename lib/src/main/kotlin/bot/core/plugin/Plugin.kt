package bot.core.plugin

import bot.core.engine.Engine
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * Базовый класс для подключаемых плагинов.
 * Каждый плагин обладает рядом триггеров.
 * Реализация метода canProcess определяет, имеет ли этот плагин триггер на поступивший апдейт.
 */
abstract class Plugin(val engine : Engine){

    /**
     * Добавление триггеров на отправление сообщений
     */
    protected val startTriggers = hashSetOf<String>()

    fun addPluginTrigger(vararg textTrigger : String)
    {
        this.startTriggers.addAll(textTrigger)
    }

    /**
     * Базовый механизм обработки
     */
    abstract fun canProcess(update: Update) : Boolean

    fun process(update: Update) : Boolean
    {
        if (update.message != null) {
            return processMessage(update)
        }
        if (update.callbackQuery != null) {
            return processCallbackQuery(update)
        }
        return true
    }

    open fun processCallbackQuery(update: Update) : ShouldPassToNext
    {
        return true
    }

    open fun processMessage(update: Update) : ShouldPassToNext
    {
        return true
    }

}

typealias ShouldPassToNext = Boolean

