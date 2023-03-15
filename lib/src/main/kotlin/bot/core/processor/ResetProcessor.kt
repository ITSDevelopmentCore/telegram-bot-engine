package bot.core.processor

import bot.core.engine.Engine
import bot.core.engine.createSession
import bot.core.ui.text
import org.telegram.telegrambots.meta.api.objects.Update

class ResetProcessor(engine: Engine) : Processor(engine) {

    override fun processMessage(update: Update) : Boolean {
        if (update.message.text == "/start")
        {
            engine.pipeline.closeSession(createSession(update))
            engine.execute(
                text("Бот успешно перезапущен. Все данные очищены", update.message.chatId)
            )
            return false
        }
        return false
    }
}