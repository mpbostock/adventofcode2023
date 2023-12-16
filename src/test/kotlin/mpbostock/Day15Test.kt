package mpbostock

import mpbostock.Day15.hash
import mpbostock.Day15.partOne
import mpbostock.Day15.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day15Test {
    private val testData = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

    @Test
    fun `HASH is hashed as 52`() {
        assertEquals(52, hash("HASH"))
    }

    @Test
    fun `part one is the sum of the hashes of the comma separated strings`() {
        assertEquals(1320, partOne(testData))
    }

    @Test
    fun `part two is the focussing power for the test data`() {
        assertEquals(145, partTwo(testData))
    }

}