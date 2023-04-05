package bot.data

class Collector(val fields: MutableList<DataField>) {

    var currentField: DataField = fields
        .first { field -> !field.isFilled() }
    get() = fields
        .first { field -> !field.isFilled() }

    fun isCollected() = fields
        .map { field -> field.isFilled() }
        .all { isFilled -> isFilled }

    fun canCollect() = !isCollected()

    fun dispatchValue(value: String) : Boolean {
        return currentField.dispatchValue(value)
    }

    fun getMessage(chatId: Long) = fields
        .first {
                !it.isFilled()
        }
        .createMessage(chatId)

    fun getByName(name: String) = fields
        .first { field -> field.name == name }


}
