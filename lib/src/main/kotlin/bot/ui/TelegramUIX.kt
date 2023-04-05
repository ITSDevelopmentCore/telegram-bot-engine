package bot.ui

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

/**
 * Добавление Inline клавиатуры к SendMessage
 * @param buttonsPerRow - Количество кнопок в ряду
 * @param buttonLabels - Массив строк, каждая из которых преобразуется в кнопку.
 * @param buttonUrls - Массив URL-адресов для каждой кнопки (необязательно).
 */
fun SendMessage.createInlineKeyboard(
    buttonsPerRow: Int = 1,
    buttonLabels: List<String> = listOf("Button 1", "Button 2", "Button 3"),
    buttonUrls: List<String?> = listOf(null, null, null)
) {

    val buttonRows = buttonLabels.chunked(buttonsPerRow).mapIndexed { rowIndex, row ->
        row.mapIndexed { buttonIndex, buttonText ->
            InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(buttonText)
                .url(buttonUrls[rowIndex * buttonsPerRow + buttonIndex])
                .build()
        }
    }

    val inlineKeyboardMarkupBuilder = InlineKeyboardMarkup.builder()

    buttonRows.forEach { row ->
        inlineKeyboardMarkupBuilder.keyboardRow(row)
    }

    this.replyMarkup = inlineKeyboardMarkupBuilder.build()
}


/**
 * Добавление Bottom клавиатуры к SendMessage
 * @param buttonsPerRow - Количество кнопок в ряду
 * @param buttonLabels - Массив строк, каждая из которых преобразуется в кнопку.
 * @param oneTime - Будет ли клавиатура действительна только 1 нажатие на нее.
 */
fun SendMessage.createBottomKeyboard(
    buttonsPerRow: Int = 1,
    buttonLabels: List<String> = listOf("Button 1", "Button 2", "Button 3"),
    oneTime: Boolean = false
): SendMessage {

    val buttonRows = buttonLabels.chunked(buttonsPerRow).map { row ->
        row.map { buttonText ->
            KeyboardButton.builder()
                .text(buttonText)
                .build()
        }
    }

    val replyKeyboardMarkupBuilder = ReplyKeyboardMarkup.builder()
        .resizeKeyboard(true)
        .oneTimeKeyboard(oneTime)

    buttonRows.forEach { row ->
        val keyboardRow = KeyboardRow()
        keyboardRow.addAll(row)
        replyKeyboardMarkupBuilder.keyboardRow(keyboardRow)
    }

    this.replyMarkup = replyKeyboardMarkupBuilder.build()
    return this
}

