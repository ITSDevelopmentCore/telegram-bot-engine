package bot.standartPlugins

import bot.engine.Engine
import bot.engine.createSession
import bot.plugin.Plugin
import bot.plugin.SessionPlugin
import bot.ui.createBottomKeyboard
import bot.ui.text
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class ResetPlugin(engine: Engine) : Plugin(engine){

    val message : SendMessage = text(TEXT_START, 0).apply {
        createBottomKeyboard(
            buttonLabels = listOf("ResetPlugin", "ResetPlugin", "ResetPlugin"),
            oneTime = true,
            isPersistent = true)
    }

    init {
        addPluginTrigger("/start", "/reset")
    }

    override fun processMessage(update: Update): Boolean {
        when(update.message.text)
        {
            COMMAND_RESET, COMMAND_START ->  processReset(update)
        }
        return super.processMessage(update)
    }

    private fun processReset(update: Update) {
        val session = createSession(update)

        engine.sessionPlugins.keys.forEach {
            if (it is SessionPlugin<*>)
                it.endSession(session)
        }

        engine.execute(message.apply {
            chatId = session.chatId.toString()
        })
    }


    companion object {
        const val COMMAND_RESET = "/reset"
        const val COMMAND_START = "/start"
        const val TEXT_START = "Это сообщение генерируется стандартным плагином ResetPlugin. Задайте свое собственное сообщение с помощью свойства message"
    }
}