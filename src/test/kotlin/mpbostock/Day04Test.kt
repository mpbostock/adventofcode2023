package mpbostock

import mpbostock.Day04.ScratchCard
import mpbostock.Day04.partOne
import mpbostock.Day04.partTwo
import org.junit.jupiter.api.Test

import kotlin.test.assertEquals

internal class Day04Test {
    private val testData = listOf(
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
    )

    private val expectedScratchCards = listOf(
        ScratchCard(1, listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53)),
        ScratchCard(2, listOf(13, 32, 20, 16, 61), listOf(61, 30, 68, 82, 17, 32, 24, 19),),
        ScratchCard(3, listOf(1, 21, 53, 59, 44), listOf(69, 82, 63, 72, 16, 21, 14, 1)),
        ScratchCard(4, listOf(41, 92, 73, 84, 69), listOf(59, 84, 76, 51, 58, 5, 54, 83)),
        ScratchCard(5, listOf(87, 83, 26, 28, 32), listOf(88, 30, 70, 12, 93, 22, 82, 36)),
        ScratchCard(6, listOf(31, 18, 13, 56, 72), listOf(74, 77, 10, 23, 35, 67, 36, 11)),
    )

    private val expectedPoints = listOf(8, 2, 2, 1, 0, 0)

    @Test
    fun `a scratch card is read from a string in the test data`() {
        for (i in expectedScratchCards.indices) {
            assertEquals(expectedScratchCards[i], ScratchCard.fromString(testData[i]))
        }
    }

    @Test
    fun `a scratch cards points is 1 for the first match then doubled for further matches`() {
        for (i in expectedPoints.indices) {
            assertEquals(expectedPoints[i], ScratchCard.fromString(testData[i]).points)
        }
    }

    @Test
    fun `part one gives 13 as the total score for all scratch cards in the test data`() {
        val input = testData.map { ScratchCard.fromString(it) }
        assertEquals(13, partOne(input))
    }

    @Test
    fun `part two copies winning cards and gives a total of 30 cards for the test data`() {
        val input = testData.map { ScratchCard.fromString(it) }
        assertEquals(30, partTwo(input))
    }

}