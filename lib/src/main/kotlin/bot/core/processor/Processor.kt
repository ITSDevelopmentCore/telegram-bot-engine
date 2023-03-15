package bot.core.processor

import bot.core.engine.Engine
import org.telegram.telegrambots.meta.api.objects.Update

abstract class Processor(val engine: Engine) {

    fun process(update: Update) : Boolean
    {
        if (update.message != null) {
            return processMessage(update)

        }
        if (update.callbackQuery != null) {
            return processCallbackQuery(update)
        }

        else return false
    }

    open fun processCallbackQuery(update: Update) : Boolean
    {
        return true
    }

    open fun processMessage(update: Update) : Boolean
    {
        return true
    }
}