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

    fun process(update: Update)
    {
        if (update.message != null) {
            processMessage(update)
            return
        }
        if (update.callbackQuery != null) {
            processCallbackQuery(update)
            return
        }
    }

    open fun processCallbackQuery(update: Update)
    {

    }

    open fun processMessage(update: Update)
    {

    }


}
