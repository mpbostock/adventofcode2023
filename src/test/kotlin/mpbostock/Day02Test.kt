package mpbostock

import mpbostock.Day02.Cube
import mpbostock.Day02.Game
import mpbostock.Day02.partOne
import mpbostock.Day02.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

internal class Day02Test {

    val testData = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    )

    val minimumPossibleCubeSets = listOf(
        Day02.CubeSet.fromCubeCounts(4, 2, 6),
        Day02.CubeSet.fromCubeCounts(1, 3, 4),
        Day02.CubeSet.fromCubeCounts(20, 13, 6),
        Day02.CubeSet.fromCubeCounts(14, 3, 15),
        Day02.CubeSet.fromCubeCounts(6, 3, 2)
    )

    val expectedCubeSetsPower = listOf(48, 12, 1560, 630, 36)

    @Test
    fun `game id with single digit is read from string`() {
        val game = Game.fromString(testData[0])
        assertEquals(1, game.id)
    }

    @Test
    fun `game id with double digits is read from string`() {
        val game = Game.fromString("Game 10: 2 red")
        assertEquals(10, game.id)
    }

    @Test
    fun `game id with three digits is read from string`() {
        val game = Game.fromString("Game 100: 2 red")
        assertEquals(100, game.id)
    }

    @Test
    fun `a red cube is read from a string`() {
        val cube = Cube.fromString("red")
        assertEquals(Cube.Red, cube)
    }

    @Test
    fun `a green cube is read from a string`() {
        val cube = Cube.fromString("green")
        assertEquals(Cube.Green, cube)
    }

    @Test
    fun `a blue cube is read from a string`() {
        val cube = Cube.fromString("blue")
        assertEquals(Cube.Blue, cube)
    }

    @Test
    fun `a cube set is read from a string including all cube colours`() {
        val cube = Day02.CubeSet.fromString(" 1 red, 2 green, 6 blue")
        assertEquals(1, cube.redCubeCount)
        assertEquals(2, cube.greenCubeCount)
        assertEquals(6, cube.blueCubeCount)
    }

    @Test
    fun `a cube set is read from a string including only 2 cube colours`() {
        val cube = Day02.CubeSet.fromString(" 3 blue, 4 red")
        assertEquals(4, cube.redCubeCount)
        assertEquals(0, cube.greenCubeCount)
        assertEquals(3, cube.blueCubeCount)
    }

    @Test
    fun `a cube set is read from a string including only 1 cube colour`() {
        val cube = Day02.CubeSet.fromString(" 2 green")
        assertEquals(0, cube.redCubeCount)
        assertEquals(2, cube.greenCubeCount)
        assertEquals(0, cube.blueCubeCount)
    }

    @Test
    fun `a game is possible if the total number of each cubes are less than or equal to the maximum allowed`() {
        val game = Game.fromString(testData[0])
        assertTrue(game.isPossible)
    }

    @Test
    fun `a game is not possible if the total number of each cubes are more than the maximum allowed`() {
        val game = Game.fromString(testData[2])
        assertFalse(game.isPossible)
    }

    @Test
    fun `a cube set power is the product of each number of cubes`() {
        for (i in minimumPossibleCubeSets.indices) {
            assertEquals(expectedCubeSetsPower[i], minimumPossibleCubeSets[i].power)
        }
    }

    @Test
    fun `a games minimum possible cube set is the maximum of each cube colour count`() {
        for (i in testData.indices) {
            assertEquals(minimumPossibleCubeSets[i], Game.fromString(testData[i]).minimumPossibleCubeSet)
        }
    }

    @Test
    fun `part one gives 8 as the sum of game ids that are possible with test data`() {
        val games = testData.map { Game.fromString(it) }
        assertEquals(8, partOne(games))
    }


    @Test
    fun `part two gives 2286 as the power of the minimum cube ste for each game in the test data`() {
        val games = testData.map { Game.fromString(it) }
        assertEquals(2286, partTwo(games))
    }

}