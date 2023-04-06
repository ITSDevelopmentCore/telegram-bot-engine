package bot.ui

import org.jetbrains.annotations.NotNull
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Message

/**
 * Создает простое текстовое сообщение
 * @param text - Текст сообщения
 * @param chatId - ID Чата, которому предназначено сообщение
 */
fun text(@NotNull text: String, @NotNull chatId: Long): SendMessage = SendMessage
    .builder()
    .chatId(chatId.toString())
    .parseMode(ParseMode.HTML)
    .text(text)
    .build()


/**
 * Удаляет сообщение
 * @param message - Сообщение к удалению
 */
fun delete(message: Message): DeleteMessage = DeleteMessage
    .builder()
    .chatId(message.chatId)
    .messageId(message.messageId)
    .build()

/**
 * Изменяет текстовое сообщение
 * @param message - Сообщение к удалению
 */
fun edit(message: Message, text: String): EditMessageText = EditMessageText
    .builder()
    .chatId(message.chatId)
    .messageId(message.messageId)
    .text(text)
    .parseMode(ParseMode.HTML)
    .build()



