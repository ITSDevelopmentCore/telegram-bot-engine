package bot.data.transfer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TransferDataTest {

    @Test
    fun `result of hasPayload(), where payload is not initialized`() {
        val transferData = TransferData(0)
        assertEquals(transferData.hasPayload(), false)
    }

}