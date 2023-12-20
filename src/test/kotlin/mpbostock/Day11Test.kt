package mpbostock

import mpbostock.Day11.Universe
import mpbostock.Day11.partOne
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day11Test {
    private val testData = listOf(
        "...#......",
        ".......#..",
        "#.........",
        "..........",
        "......#...",
        ".#........",
        ".........#",
        "..........",
        ".......#..",
        "#...#.....",
    )

    private val expectedExpandedGalaxies = listOf(
        Coordinate(4, 0),
        Coordinate(9, 1),
        Coordinate(0, 2),
        Coordinate(8, 5),
        Coordinate(1, 6),
        Coordinate(12, 7),
        Coordinate(9, 10),
        Coordinate(0, 11),
        Coordinate(5, 11),
    )

    @Test
    fun `empty rows are correct for universe for test data`() {
        assertEquals(setOf(3, 7), Universe(testData).emptyRowIndices)
    }

    @Test
    fun `empty columns are correct for universe for test data`() {
        assertEquals(setOf(2, 5, 8), Universe(testData).emptyColumnIndices)
    }

    @Test
    fun `galaxies are expanded by the expansion amount in the test data`() {
        val galaxies = Universe(testData).galaxies
        for (i in expectedExpandedGalaxies.indices) {
            assertEquals(expectedExpandedGalaxies[i], galaxies[i])
        }
    }

    @Test
    fun `there are nine galaxies in the test data`() {
        val galaxies = Universe(testData).galaxies
        assertEquals(9, galaxies.size)
    }

    @Test
    fun `there are 9 steps between galaxy 5 and galaxy 9 in the test data`() {
        val galaxies = Universe(testData).galaxies
        val line = Line(galaxies[4], galaxies[8])
        assertEquals(9, line.stepsBetween())
    }

    @Test
    fun `there are 15 steps between galaxy 1 and galaxy 7 in the test data`() {
        val galaxies = Universe(testData).galaxies
        val line = Line(galaxies[0], galaxies[6])
        assertEquals(15, line.stepsBetween())
    }

    @Test
    fun `there are 17 steps between galaxy 3 and galaxy 6 in the test data`() {
        val galaxies = Universe(testData).galaxies
        val line = Line(galaxies[2], galaxies[5])
        assertEquals(17, line.stepsBetween())
    }

    @Test
    fun `there are 5 steps between galaxy 8 and galaxy 9 in the test data`() {
        val galaxies = Universe(testData).galaxies
        val line = Line(galaxies[7], galaxies[8])
        assertEquals(5, line.stepsBetween())
    }

    @Test
    fun `if the spaces in the galaxies is expanded ten times the sum of the shortest distances is 1030`() {
        val total = Universe(testData, 10).sumOfShortestPaths
        assertEquals(1030, total)
    }

    @Test
    fun `if the spaces in the galaxies is expanded one hundred times the sum of the shortest distances is 8410`() {
        val total = Universe(testData, 100).sumOfShortestPaths
        assertEquals(8410, total)
    }

    @Test
    fun `part one gives 374 as the sum of the shortest distances between all galaxies in the test data`() {
        assertEquals(374, partOne(testData))
    }

}