package bot.core.ui

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.util.concurrent.LinkedBlockingQueue
import java.util.stream.Collectors

/**
 * Добавление Inline клавиатуры к SendMessage
 * @param buttonsPerRow - Количество кнопок в ряду
 * @param buttons - Массив строк, каждая из которых преобразуется в кнопку.
 */
fun SendMessage.createInlineKeyboard(buttonsPerRow: Int = 1, buttons: List<String> = listOf("Button 1", "Button 2","Button 3")) {

    val buttons = transformStringsToButtons(buttons)
    val builder = InlineKeyboardMarkup.builder()

    do {
        val row = buttons
            .stream()
            .limit(buttonsPerRow.toLong())
            .collect(Collectors.toList())

        for (i in 0 until buttonsPerRow) {
            buttons.poll()
        }

        builder.keyboardRow(row)
    }
    while (buttons.size > 0)


    this.replyMarkup = builder.build()
}


/**
 * Добавление Bottom клавиатуры к SendMessage
 * @param buttonsPerRow - Количество кнопок в ряду
 * @param context - Массив строк, каждая из которых преобразуется в кнопку.
 */
fun SendMessage.createBottomKeyboard(
    buttonsPerRow: Int = 1,
    buttons: List<String> = listOf("Button 1", "Button 2","Button 3"),
    oneTime : Boolean = false) : SendMessage {

    val buttons = transformStringsToBottomButtons(buttons)
    val builder = ReplyKeyboardMarkup.builder()
    do {
        val row = buttons
            .stream()
            .limit(buttonsPerRow.toLong())
            .collect(Collectors.toList())

        for (i in 0 until buttonsPerRow) {
            buttons.poll()
        }

        val keyboardRow = KeyboardRow()
        keyboardRow.addAll(row)

        builder.keyboardRow(keyboardRow)
    }
    while (buttons.size > 0)

    this.replyMarkup = builder.resizeKeyboard(true).oneTimeKeyboard(oneTime).build()

    return this
}




private fun transformStringsToBottomButtons(context: List<String>): LinkedBlockingQueue<KeyboardButton> {
    return context.stream()
        .map { name ->
            KeyboardButton.builder()
                .text(name)
                .build()
        }
        .collect(
            Collectors.toCollection { LinkedBlockingQueue() }
        )
}


private fun transformStringsToButtons(context: List<String>): LinkedBlockingQueue<InlineKeyboardButton> {
    return context.stream()
        .map { name ->
            InlineKeyboardButton.builder()
                .text(name)
                .callbackData(name)
                .build()
        }
        .collect(
            Collectors.toCollection { LinkedBlockingQueue() }
        )
}

