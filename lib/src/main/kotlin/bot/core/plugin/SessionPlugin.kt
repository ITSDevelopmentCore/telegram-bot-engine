package its.telegram.bot.core.plugin

import its.telegram.bot.core.engine.Engine
import its.telegram.bot.core.engine.BotUser

/**
 * Сессионный плаги. Отличие от базового плагина - наличие сессий.
 * Наследники этого класса в любой момент могут вызвать метод startSession,
 * после чего этот плагин будет перехватывать все апдейты от этого пользователя.
 */
abstract class SessionPlugin<T>(engine: Engine) : Plugin(engine) {

    protected val activeUsers : HashMap<BotUser, T> = HashMap()

    /**
     * Создает пользовательскую сессию, оповещает Pipeline о том,
     * что все апдейты от текущего пользователя должны поступать в эту сессию
     */
    protected fun startSession(botUser : BotUser, data : T)
    {
        engine.pipeline.pluginSessions[this]!!.add(botUser)
        activeUsers[botUser] = data
        println("Session plugin ${this::class.java.simpleName} lock user with id ${botUser.telegramUser.id}")
    }

    /**
     * Завершает пользовательскую сессию, оповещает пайплайн о том,
     * что данный плагин больше не держит пользователя.
     */
    protected fun endSession(botUser : BotUser)
    {
        engine.pipeline.pluginSessions[this]!!.remove(botUser)
        activeUsers.remove(botUser)
        println("Session plugin ${this::class.java.simpleName} unlock user with id ${botUser.telegramUser.id}")
    }


}

