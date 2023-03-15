package bot.core.engine

import org.jetbrains.annotations.NotNull
import org.telegram.telegrambots.meta.api.objects.Update

data class Session(
    @NotNull val chatId : Long,
    @NotNull val telegramUsername : String,
    val sessionStart : Long = System.currentTimeMillis()
)

/**
 * Создает пользотеля по имеющемуся апдейт
 */
fun createSession(update : Update) : Session
{
    return if (update.hasCallbackQuery())
        Session(update.callbackQuery.message.chatId, update.callbackQuery.from.userName)
    else
        Session(update.message.chatId, update.message.from.userName)
}
