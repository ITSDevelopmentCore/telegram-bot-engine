package bot.core.plugin

import bot.core.engine.Engine
import bot.core.engine.Session
import bot.core.engine.logger

/**
 * Сессионный плаги. Отличие от базового плагина - наличие сессий.
 * Наследники этого класса в любой момент могут вызвать метод startSession,
 * после чего этот плагин будет перехватывать все апдейты от этого пользователя.
 */
abstract class SessionPlugin<T>(engine: Engine) : Plugin(engine) {

    private val activeUsers : HashMap<Session, T> = HashMap()

    /**
     * Создает пользовательскую сессию, оповещает Pipeline о том,
     * что все апдейты от текущего пользователя должны поступать в эту сессию
     */
    protected fun startSession(session : Session, data : T)
    {
        engine.pipeline.pluginSessions[this]!!.add(session)
        activeUsers[session] = data
        logger.debug("Session plugin ${this::class.java.simpleName} lock user with name ${session.telegramUsername}")
    }

    /**
     * Завершает пользовательскую сессию, оповещает пайплайн о том,
     * что данный плагин больше не держит пользователя.
     */
    protected fun endSession(session : Session)
    {
        engine.pipeline.pluginSessions[this]!!.remove(session)
        activeUsers.remove(session)
        logger.debug("Session plugin ${this::class.java.simpleName} unlock user with name ${session.telegramUsername}")
    }

    /**
     * Очищение всех пользовательских сессий, которые существуют
     * более указанного периода в секундах
     */
    fun clearSessions(period : Int = PERIOD_TWO_DAYS)
    {
        activeUsers.keys.removeIf {
            (it.sessionStart / 1000) - (System.currentTimeMillis() / 1000) > period
        }
    }


    fun abortSessions() = clearSessions(0)
}

const val PERIOD_TWO_DAYS = 172_800
const val PERIOD_ONE_DAY = 86_400

