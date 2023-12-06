package mpbostock

import mpbostock.Day06.BoatRace
import mpbostock.Day06.BoatRaces
import mpbostock.Day06.partOne
import mpbostock.Day06.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class Day06Test {
    private val testData = listOf (
        "Time:      7  15   30",
        "Distance:  9  40  200"
    )

    private val expectedBoatRacesPartOne = sequenceOf(
        BoatRace(7, 9),
        BoatRace(15, 40),
        BoatRace(30, 200),
    )

    private val expectedBoatRacePartTwo = BoatRace(71530, 940200)

    private val expectedNumberOfWaysToWin = listOf(4, 8, 9)

    @Test
    fun `boat races get parsed for part one from the test input`() {
        val boatRaces = BoatRaces.fromInputPartOne(testData)
        assertContentEquals(expectedBoatRacesPartOne, boatRaces.races)
    }

    @Test
    fun `boat has expected ways to win for part one for each race in the test data`() {
        for (i in expectedNumberOfWaysToWin.indices) {
            assertEquals(expectedNumberOfWaysToWin[i], expectedBoatRacesPartOne.toList()[i].numberOfWaysToWin())
        }
    }

    @Test
    fun `boat races get parsed for part two from the test input`() {
        val boatRaces = BoatRaces.fromInputPartTwo(testData)
        assertContentEquals(sequenceOf(expectedBoatRacePartTwo), boatRaces.races)
    }

    @Test
    fun `part one returns 288 as ways to win multiple for tests data`() {
        assertEquals(288, partOne(testData))
    }

    @Test
    fun `part two returns 71503 as ways to win for big race in test data`() {
        assertEquals(71503, partTwo(testData))
    }

}