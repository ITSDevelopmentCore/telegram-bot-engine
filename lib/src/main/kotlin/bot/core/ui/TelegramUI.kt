package its.telegram.bot.core.ui

import org.jetbrains.annotations.NotNull
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

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


