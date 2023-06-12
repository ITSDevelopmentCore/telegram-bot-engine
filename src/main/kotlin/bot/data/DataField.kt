package bot.data

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

interface DataField {

    /**
     * Кодовое название DataField
     */
    val name : String

    /**
     * Проверяет, заполнен ли DataField
     * @return - true, если DataField заполнен
     */
    fun isFilled() : Boolean

    /**
     * Заполняет DataField
     */
    fun dispatchValue(value : String) : Boolean

    /**
     * Создает сопровождающее сообщение
     */
    fun createMessage(chatId : Long) : SendMessage

    /**
     * Очищает DataField
     */
    fun clear()

}