package bot.ui

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
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
    buttonData: List<String?> = buttonLabels,
    buttonUrls: List<String?> = List(buttonLabels.size) { null }
) {

    val buttonRows = buttonLabels.chunked(buttonsPerRow).mapIndexed { rowIndex, row ->
        row.mapIndexed { buttonIndex, buttonText ->
            InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(buttonText)
                .callbackData(buttonData[rowIndex * buttonsPerRow + buttonIndex])
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
    oneTime: Boolean = false,
    isPersistent: Boolean = false
) {

    val buttonRows = buttonLabels.chunked(buttonsPerRow).map { row ->
        row.map { buttonText ->
            KeyboardButton.builder()
                .text(buttonText)
                .build()
        }
    }

    val replyKeyboardMarkupBuilder = ReplyKeyboardMarkup.builder()
        .resizeKeyboard(true)
        .isPersistent(isPersistent)
        .oneTimeKeyboard(oneTime)

    buttonRows.forEach { row ->
        val keyboardRow = KeyboardRow()
        keyboardRow.addAll(row)
        replyKeyboardMarkupBuilder.keyboardRow(keyboardRow)
    }

    this.replyMarkup = replyKeyboardMarkupBuilder.build()
}


/**
 * Добавление Inline клавиатуры к EditMessageText
 * @param buttonsPerRow - Количество кнопок в ряду
 * @param buttonLabels - Массив строк, каждая из которых преобразуется в кнопку.
 * @param buttonUrls - Массив URL-адресов для каждой кнопки (необязательно).
 */
fun EditMessageText.createInlineKeyboard(
    buttonsPerRow: Int = 1,
    buttonLabels: List<String> = listOf("Button 1", "Button 2", "Button 3"),
    buttonData: List<String?> = buttonLabels,
    buttonUrls: List<String?> = List(buttonLabels.size) { null }
) {

    val buttonRows = buttonLabels.chunked(buttonsPerRow).mapIndexed { rowIndex, row ->
        row.mapIndexed { buttonIndex, buttonText ->
            InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(buttonData[rowIndex * buttonsPerRow + buttonIndex])
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
 * Добавление Inline клавиатуры к SendMessage
 * @param buttonsPerRow - Количество кнопок в ряду
 * @param buttonLabels - Массив строк, каждая из которых преобразуется в кнопку.
 * @param buttonUrls - Массив URL-адресов для каждой кнопки (необязательно).
 */
fun SendPhoto.createInlineKeyboard(
    buttonsPerRow: Int = 1,
    buttonLabels: List<String> = listOf("Button 1", "Button 2", "Button 3"),
    buttonData: List<String?> = buttonLabels,
    buttonUrls: List<String?> = List(buttonLabels.size) { null }
) {

    val buttonRows = buttonLabels.chunked(buttonsPerRow).mapIndexed { rowIndex, row ->
        row.mapIndexed { buttonIndex, buttonText ->
            InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(buttonText)
                .callbackData(buttonData[rowIndex * buttonsPerRow + buttonIndex])
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
fun SendPhoto.createBottomKeyboard(
    buttonsPerRow: Int = 1,
    buttonLabels: List<String> = listOf("Button 1", "Button 2", "Button 3"),
    oneTime: Boolean = false,
    isPersistent: Boolean = false
) {

    val buttonRows = buttonLabels.chunked(buttonsPerRow).map { row ->
        row.map { buttonText ->
            KeyboardButton.builder()
                .text(buttonText)
                .build()
        }
    }

    val replyKeyboardMarkupBuilder = ReplyKeyboardMarkup.builder()
        .resizeKeyboard(true)
        .isPersistent(isPersistent)
        .oneTimeKeyboard(oneTime)

    buttonRows.forEach { row ->
        val keyboardRow = KeyboardRow()
        keyboardRow.addAll(row)
        replyKeyboardMarkupBuilder.keyboardRow(keyboardRow)
    }

    this.replyMarkup = replyKeyboardMarkupBuilder.build()
}
