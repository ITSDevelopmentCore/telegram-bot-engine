package bot.util

import java.util.concurrent.atomic.AtomicInteger

fun createTrigger() = currentIndex.getAndIncrement().toByte()

private val currentIndex = AtomicInteger(-127)
