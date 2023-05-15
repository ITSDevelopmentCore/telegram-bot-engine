package bot.engine

import org.telegram.telegrambots.meta.api.objects.Update

data class Session(
    val chatId: Long,
    val telegramId: Long,
    val telegramName: String?,
    val telegramUsername: String?,
) {
    override fun hashCode(): Int = telegramId.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Session) return false
        return telegramId == other.telegramId
    }

    override fun toString() = "User ID : ${telegramId}. Username : $telegramUsername.\n"
}

/**
 * Создает пользотеля по имеющемуся Update
 */
fun createSession(update: Update) =
    if (!update.hasCallbackQuery())
        Session(
            update.message.chatId,
            update.message.from.id,
            update.message.from.firstName,
            update.message.from.userName,
        )
    else
        Session(
            update.callbackQuery.message.chatId,
            update.callbackQuery.from.id,
            update.callbackQuery.from.firstName,
            update.callbackQuery.from.userName,
        )


