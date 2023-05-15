package bot.data

import bot.ui.text
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class ArrayDataField(
    override val name: String,
    val messageConstructor: (ArrayDataField) -> String,
    val size: Int
) : DataField {

    private val values = MutableList<String?>(size) { null }

    fun currentItem() = values.indexOfFirst { element -> element == null } + 1

    override fun isFilled(): Boolean {
        return values
            .all { element -> element != null }
    }

    override fun dispatchValue(value: String): Boolean {
        if (value.isNotBlank()) {
            val index = values.indexOfFirst { element -> element == null }
            values[index] = value
            return true
        }
        return false
    }

    override fun createMessage(chatId: Long): SendMessage {
        val textMessage = messageConstructor(this)
        return text(textMessage, chatId)
    }

    override fun clear() {
        values.clear()
    }

    override fun toString(): String {
        return values.joinToString("|")
    }

}