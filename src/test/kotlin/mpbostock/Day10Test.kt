package mpbostock

import mpbostock.Day10.Coordinate
import mpbostock.Day10.partOne
import mpbostock.Day10.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day10Test {
    private val simpleTestData = listOf(
        "-L|F7",
        "7S-7|",
        "L|7||",
        "-L-J|",
        "L|-JF",
    )

    private val complexTestData = listOf(
        "7-F7-",
        ".FJ|7",
        "SJLL7",
        "|F--J",
        "LJ.LJ",
    )

    private val testForInsideLoop1 = listOf(
        "...........",
        ".S-------7.",
        ".|F-----7|.",
        ".||.....||.",
        ".||.....||.",
        ".|L-7.F-J|.",
        ".|..|.|..|.",
        ".L--J.L--J.",
        "...........",
    )

    private val testForInsideLoop2 = listOf(
        ".F----7F7F7F7F-7....",
        ".|F--7||||||||FJ....",
        ".||.FJ||||||||L7....",
        "FJL7L7LJLJ||LJ.L-7..",
        "L--J.L7...LJS7F-7L7.",
        "....F-J..F7FJ|L7L7L7",
        "....L7.F7||L7|.L7L7|",
        ".....|FJLJ|FJ|F7|.LJ",
        "....FJL-7.||.||||...",
        "....L---J.LJ.LJLJ...",
    )

    private val testForInsideLoop3 = listOf(
        "FF7FSF7F7F7F7F7F---7",
        "L|LJ||||||||||||F--J",
        "FL-7LJLJ||||||LJL-77",
        "F--JF--7||LJLJ7F7FJ-",
        "L---JF-JLJ.||-FJLJJ7",
        "|F|F-JF---7F7-L7L|7|",
        "|FFJF7L7F-JF7|JL---7",
        "7-L-JL7||F7|L7F-7F7|",
        "L.L7LFJ|||||FJL7||LJ",
        "L7JLJL-JLJLJL--JLJ.L",
    )

    private val expectedSimpleLoopCoordinates = setOf(
        Coordinate(x = 1, y = 1),
        Coordinate(x = 1, y = 2),
        Coordinate(x = 2, y = 1),
        Coordinate(x = 1, y = 3),
        Coordinate(x = 3, y = 1),
        Coordinate(x = 2, y = 3),
        Coordinate(x = 3, y = 2),
        Coordinate(x = 3, y = 3),
    )

    private val expectedComplexLoopCoordinates = setOf(
        Coordinate(x=0, y=2),
        Coordinate(x=0, y=3),
        Coordinate(x=1, y=2),
        Coordinate(x=0, y=4),
        Coordinate(x=1, y=1),
        Coordinate(x=1, y=4),
        Coordinate(x=2, y=1),
        Coordinate(x=1, y=3),
        Coordinate(x=2, y=0),
        Coordinate(x=2, y=3),
        Coordinate(x=3, y=0),
        Coordinate(x=3, y=3),
        Coordinate(x=3, y=1),
        Coordinate(x=4, y=3),
        Coordinate(x=3, y=2),
        Coordinate(x=4, y=2),
    )

    @Test
    fun `loop coordinates are expected for simple loop`() {
        val grid = Day10.Grid.fromInput(simpleTestData)
        assertEquals(expectedSimpleLoopCoordinates, grid.loop)
    }

    @Test
    fun `loop coordinates are expected for complex loop`() {
        val loopCoordinates = Day10.Grid.fromInput(complexTestData).loop
        assertEquals(expectedComplexLoopCoordinates, loopCoordinates)
    }

    @Test
    fun `part one gives furthest step away from start of loop for test data`() {
        assertEquals(4, partOne(Day10.Grid.fromInput(simpleTestData)))
        assertEquals(8, partOne(Day10.Grid.fromInput(complexTestData)))
    }

    @Test
    fun `part two gives number of tiles inside loop`() {
        assertEquals(1, partTwo(Day10.Grid.fromInput(simpleTestData)))
        assertEquals(1, partTwo(Day10.Grid.fromInput(complexTestData)))
        assertEquals(4, partTwo(Day10.Grid.fromInput(testForInsideLoop1)))
        assertEquals(8, partTwo(Day10.Grid.fromInput(testForInsideLoop2)))
        assertEquals(10, partTwo(Day10.Grid.fromInput(testForInsideLoop3)))
    }
}