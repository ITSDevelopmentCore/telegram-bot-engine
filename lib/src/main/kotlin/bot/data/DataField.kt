package bot.data

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

interface DataField {

    val name : String

    fun isFilled() : Boolean

    fun dispatchValue(value : String) : Boolean

    fun createMessage(chatId : Long) : SendMessage

    fun clear()
}