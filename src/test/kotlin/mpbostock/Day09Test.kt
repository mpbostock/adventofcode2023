package mpbostock

import mpbostock.Day09.partOne
import mpbostock.Day09.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day09Test {
    private val testData = listOf(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45",
    )

    private val expectedNextValue = listOf(18, 28, 68)
    private val expectedPreviousValue = listOf(-3, 0, 5)

    @Test
    fun `value history predicts the correct next value for the test data`() {
        for(i in testData.indices) {
            assertEquals(expectedNextValue[i], Day09.ValueHistory.fromString(testData[i]).predictNextValue())
        }
    }

    @Test
    fun `value history predicts the correct previous value for the test data`() {
        for(i in testData.indices) {
            assertEquals(expectedPreviousValue[i], Day09.ValueHistory.fromString(testData[i]).predictPreviousValue())
        }
    }

    @Test
    fun `part one gives the sum of all the next predicted values for the test data`() {
        assertEquals(114, partOne(testData.map { Day09.ValueHistory.fromString(it) }))
    }

    @Test
    fun `part two gives the sum of all the previous predicted values for the test data`() {
        assertEquals(2, partTwo(testData.map { Day09.ValueHistory.fromString(it) }))
    }

}