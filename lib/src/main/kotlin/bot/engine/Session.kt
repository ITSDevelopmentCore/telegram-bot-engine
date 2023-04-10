package bot.engine

import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

data class Session(
    val chatId : Long,
    val telegramId : Long,
    val telegramName : String?,
    val telegramUsername : String?,
    val lastMessage: Message?,
    private val timestamp : Long = System.currentTimeMillis()
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Session

        if (chatId != other.chatId) return false
        if (telegramId != other.telegramId) return false
        if (telegramName != other.telegramName) return false
        if (telegramUsername != other.telegramUsername) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chatId.hashCode()
        result = 31 * result + telegramId.hashCode()
        result = 31 * result + (telegramName?.hashCode() ?: 0)
        result = 31 * result + (telegramUsername?.hashCode() ?: 0)
        return result
    }
}

/**
 * Создает пользотеля по имеющемуся Update
 */
fun createSession(update : Update) : Session
{
    if (update.hasCallbackQuery())
    {
        return Session(
            update.callbackQuery.message.chatId,
            update.callbackQuery.from.id,
            update.callbackQuery.from.firstName,
            update.callbackQuery.from.userName,
            null
        )
    }
    else
    {
        return Session(
            update.message.chatId,
            update.message.from.id,
            update.message.from.firstName,
            update.message.from.userName,
            update.message
        )
    }
}

