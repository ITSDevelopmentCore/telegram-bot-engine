package bot.data

import bot.ui.createInlineKeyboard
import bot.ui.text
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class SimpleDataField(override val name: String, val message: String) : DataField {

    private var value: String = ""

    /**
     * Работа с вариантами
     */
    fun applyVariants(variants: List<String>, variantsData: List<String>?) {
        this.variants = variants
        if (variantsData == null)
            this.variantsData = variants
        else
            this.variantsData = variantsData
    }

    private var variants: List<String> = listOf()
    private var variantsData: List<String> = listOf()
    /**
     * Работа с дополнительным значением
     */
    fun applyAdditional(buttonLabel: String, descriptionText: String) {
        if (buttonLabel.isNotBlank()) {
            this.extraLabel = buttonLabel
            this.extraMessage = descriptionText
        }
    }

    private var extraLabel: String? = null
    private var extraMessage: String? = null
    private var waitingForExtra = false

    override fun isFilled() = value.isNotBlank()


    override fun createMessage(chatId: Long): SendMessage {
        return if (waitingForExtra && extraMessage != null)
            text(extraMessage!!, chatId)
        else text(message, chatId)
            .apply {
                createInlineKeyboard(
                    buttonLabels = if (extraLabel != null) variants.plus(extraLabel!!) else variants,
                    buttonData = if (extraLabel != null) variantsData.plus(extraLabel) else variantsData
                )
            }

    }

    override fun clear() {
        value = ""
    }


    override fun dispatchValue(value: String): Boolean {
        if (waitingForExtra) {
            this.value = value
            return true
        }
        if (value == extraLabel) {
            waitingForExtra = true
            return false
        }
        if (value.isNotBlank()) {
            this.value = value
            return true
        }
        return false
    }


    override fun toString() = value

}