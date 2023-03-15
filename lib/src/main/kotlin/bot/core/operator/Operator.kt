package bot.core.operator

import org.telegram.telegrambots.meta.api.objects.Update

abstract class Operator {
    abstract fun process(update: Update): Boolean
}