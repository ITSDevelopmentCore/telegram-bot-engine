package bot.plugin

import bot.data.transfer.TransferData
import bot.engine.Engine
import org.telegram.telegrambots.meta.api.objects.Update

open class DefaultPlugin(engine: Engine) : BasePlugin(engine) {

    /**
     * Добавление триггеров на callback команды
     */
    private val triggerSet = hashSetOf<Byte>()

    fun addTrigger(trigger: Byte) = triggerSet.add(trigger)


    override fun canProcess(update: Update) =
        update.callbackQuery != null && triggerSet.contains(TransferData.deserialize(update.callbackQuery.data).trigger)
                || super.canProcess(update)

}