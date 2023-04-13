package bot.data

import bot.ui.createInlineKeyboard
import bot.ui.text

class SimpleDataField(override val name: String, val message: String) : DataField {

    constructor(name: String, message: String, variants: List<String>, variantsData: List<String>) : this(name, message) {
        this.variants = variants
        this.variantsData = variantsData
    }

    private var variants: List<String> = mutableListOf()
    private var variantsData: List<String> = mutableListOf()


    var value: String? = null

    val canAcceptNewValue: Boolean = false

    fun hasVariants() = variants.isNotEmpty()

    override fun isFilled() = value != null

    override fun createMessage(chatId: Long) = text(message, chatId)
        .apply {
            createInlineKeyboard(
                buttonsPerRow = 1,
                buttonLabels = if (canAcceptNewValue) variants.plus(NEW_VALUE) else variants,
                buttonUrls = MutableList(variants.size + 1){ null },
                buttonData = if (canAcceptNewValue) variantsData.plus(NEW_VALUE) else variantsData,
            )
        }

    override fun clear() {
        value = null
    }

    override fun dispatchValue(value: String) : Boolean {
        if (value.isNotBlank()) {
            this.value = value
            return true
        }
        return false
    }

    companion object {
        const val NEW_VALUE = "Другое значение"
    }

    override fun toString(): String {
        return value.toString()
    }
}