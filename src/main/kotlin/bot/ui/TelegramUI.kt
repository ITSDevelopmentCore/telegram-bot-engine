package bot.ui

import org.telegram.telegrambots.meta.api.methods.ActionType
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import java.io.File

/**
 * Создает простое текстовое сообщение
 * @param text - Текст сообщения
 * @param chatId - ID Чата, которому предназначено сообщение
 */
fun text(text: String, chatId: Long): SendMessage = SendMessage
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

/**
 * Отсылает статус "Typing"
 */
fun typing(chatId: Long) : SendChatAction = SendChatAction
    .builder()
    .chatId(chatId)
    .action(ActionType.TYPING.toString())
    .build()


/**
 * Создает сообщение с фотографией
 * @param text - Текст сообщения
 * @param chatId - ID Чата, которому предназначено сообщение
 */
fun photo(text : String, photo : File, chatId: Long): SendPhoto = SendPhoto
    .builder()
    .caption(text)
    .photo(InputFile(photo))
    .parseMode(ParseMode.HTML)
    .chatId(chatId)
    .build()



