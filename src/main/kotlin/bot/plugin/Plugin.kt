package bot.plugin

import bot.engine.Engine
import bot.engine.logger
import org.telegram.telegrambots.meta.api.objects.Update
import java.nio.ByteBuffer
import kotlin.Nothing as Nothing1

/**
 * Базовый класс для подключаемых плагинов.
 * Каждый плагин обладает рядом триггеров.
 * Реализация метода canProcess определяет, имеет ли этот плагин триггер на поступивший апдейт.
 */
abstract class Plugin(val engine: Engine) : Comparable<Plugin> {

    override fun compareTo(other: Plugin) = this.priority - other.priority

    var priority: Byte = PRIORITY_NORMAL
        set(value) {
            logger.debug("Для плагина ${this::class.java.simpleName} был установлен приоритет $value")
            field = value
        }

    /**
     * Добавление триггеров на отправление сообщений
     */
    val startTriggers = hashSetOf<String>()

    fun addPluginTrigger(vararg textTrigger: String) {
        this.startTriggers.addAll(textTrigger)
    }

    /**
     * Базовый механизм обработки
     */
    open fun canProcess(update: Update) =
        update.message != null && startTriggers.contains(update.message.text) ||
        update.callbackQuery != null && startTriggers.contains(update.callbackQuery.data.split("|")[0])

    open fun process(update: Update) =
        when {
            update.message != null && update.message.photo != null -> processPhoto(update)
            update.message != null && update.message.text != null -> processMessage(update)
            update.callbackQuery != null -> processCallbackQuery(update)
            else -> processOther(update)
        }

    open fun processPhoto(update: Update) = false

    open fun processCallbackQuery(update: Update) = false

    open fun processOther(update: Update) = false

    open fun processMessage(update: Update) = false

    open fun processForce(update: Update) = Any()

    companion object {
        const val PRIORITY_MINIMUM: Byte = 120
        const val PRIORITY_LOW: Byte = 100
        const val PRIORITY_NORMAL: Byte = 75
        const val PRIORITY_HIGH: Byte = 50
        const val PRIORITY_MAXIMUM: Byte = 25
        const val PRIORITY_PREPROCESSOR: Byte = -1
    }
}


