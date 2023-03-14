package its.telegram.bot.core.engine

import org.jetbrains.annotations.NotNull
import org.telegram.telegrambots.meta.api.objects.Update

data class BotUser(
    @NotNull val chatId : Long,
    @NotNull val telegramUser : org.telegram.telegrambots.meta.api.objects.User
)

/**
 * Создает пользотеля по имеющемуся апдейт
 */
fun createUser(update : Update) : BotUser
{
    return if (update.hasCallbackQuery())
        BotUser(update.callbackQuery.message.chatId, update.callbackQuery.from)
    else
        BotUser(update.message.chatId, update.message.from)
}
