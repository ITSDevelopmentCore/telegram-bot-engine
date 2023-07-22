package bot.data.transfer

import java.util.concurrent.atomic.AtomicInteger

object TriggerGenerator {
    val currentIndex = AtomicInteger(-127)
}

fun generateTrigger(): Byte = TriggerGenerator.currentIndex.getAndIncrement().toByte()