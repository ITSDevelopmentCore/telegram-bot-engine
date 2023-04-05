package bot.plugin

import bot.engine.Engine
import bot.engine.Session
import bot.engine.createSession
import bot.engine.logger
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.*
import kotlin.collections.HashMap

/**
 * Сессионный плаги. Отличие от базового плагина - наличие сессий.
 * Наследники этого класса в любой момент могут вызвать метод startSession,
 * после чего этот плагин будет перехватывать все апдейты от этого пользователя.
 */
abstract class SessionPlugin<T>(engine: Engine) : Plugin(engine) {

    val activeUsers : HashMap<Session, T> = HashMap()

    override fun process(update: Update): Boolean {
        val session = createSession(update)
                      startSession(session)

        return super.process(update)
    }

    /**
     * Создает пользовательскую сессию, оповещает Pipeline о том,
     * что все апдейты от текущего пользователя должны поступать в эту сессию
     */
    protected fun startSession(session : Session)
    {
        if (Objects.isNull(activeUsers[session])) {
            engine.pipeline.pluginSessions[this]!!.add(session)
            activeUsers[session] = provideData()
            logger.debug("Session plugin ${this::class.java.simpleName} lock user with name ${session.telegramUsername}")
        }
    }

    /**
     * Создает пользовательскую сессию, оповещает Pipeline о том,
     * что все апдейты от текущего пользователя должны поступать в эту сессию
     */
    protected fun startSessionWithReset(session : Session)
    {
        if (Objects.nonNull(activeUsers[session])) {
            engine.pipeline.pluginSessions[this]!!.remove(session)
            engine.pipeline.pluginSessions[this]!!.add(session)
            activeUsers[session] = provideData()
            logger.debug("Session plugin ${this::class.java.simpleName} lock user with name ${session.telegramUsername}")
        }
    }

    /**
     * Завершает пользовательскую сессию, оповещает пайплайн о том,
     * что данный плагин больше не держит пользователя.
     */
    fun endSession(session : Session)
    {
        if (Objects.nonNull(activeUsers[session]))
        {
            engine.pipeline.pluginSessions[this]!!.remove(session)
            activeUsers.remove(session)
            logger.debug("Session plugin ${this::class.java.simpleName} unlock user with name ${session.telegramUsername}")
        }
    }

    protected fun getData(session: Session) : T
    {
        if (Objects.nonNull(activeUsers[session]))
            return activeUsers[session]!!
        else
            throw IllegalAccessException("Плагин ${this::class.java.simpleName} попытался получить данные сессии пользователя до открытия сессии")
    }

    abstract fun provideData() : T
}

const val PERIOD_TWO_DAYS = 172_800
const val PERIOD_ONE_DAY = 86_400

