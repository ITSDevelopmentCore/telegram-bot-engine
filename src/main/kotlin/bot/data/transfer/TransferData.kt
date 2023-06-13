package bot.data.transfer

/**
 * @author its_razex
 * Класс, реализующий объект данных, помещающийся в Callback Data в Telegram Button.
 *
 * Экземпляр TransferData состоит из двух элементов:
 * 1. Command - команда, по которой Бот отличает, какой плагин должен обработать этот пакет данных
 * 2. Payload - полезная нагрузка, содержащаяся в пакете данных.
 *
 * Размер сериализованного вида объекта TransferData не должен превышать 64 байта
 */
class TransferData(val commandIndex: Byte) {

    private var _payload: String

    init {
        _payload = ""
    }

    val payload: String
        get() = _payload

    constructor(command: Byte, payload: String) : this(command) {
        this._payload = payload
    }

    fun serialize() = buildString {
        append(commandIndex)
        append("|")
        append(payload)
    }

    fun hasPayload() = _payload.isNotBlank()

    companion object {

        fun deserialize(raw: String) : TransferData {
            val array = raw.split("|")

            return if (array.size == 2)
                TransferData(array.first().toByte(), array.last())
            else
                TransferData(array.first().toByte())
        }

    }

}