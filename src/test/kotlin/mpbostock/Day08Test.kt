package mpbostock

import mpbostock.Day08.WastelandMap
import mpbostock.Day08.partOne
import mpbostock.Day08.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day08Test {
    private val testData = listOf(
        "RL",
        "",
        "AAA = (BBB, CCC)",
        "BBB = (DDD, EEE)",
        "CCC = (ZZZ, GGG)",
        "DDD = (DDD, DDD)",
        "EEE = (EEE, EEE)",
        "GGG = (GGG, GGG)",
        "ZZZ = (ZZZ, ZZZ)",
    )

    private val testDataNotFoundFirstIteration = listOf(
        "LLR",
        "",
        "AAA = (BBB, BBB)",
        "BBB = (AAA, ZZZ)",
        "ZZZ = (ZZZ, ZZZ)",
    )

    private val testDataRunningSimultaneously = listOf(
        "LR",
        "",
        "11A = (11B, XXX)",
        "11B = (XXX, 11Z)",
        "11Z = (11B, XXX)",
        "22A = (22B, XXX)",
        "22B = (22C, 22C)",
        "22C = (22Z, 22Z)",
        "22Z = (22B, 22B)",
        "XXX = (XXX, XXX)",
    )

    @Test
    fun `navigating the wasteland map returns the amount of steps to reach the end`() {
        val wastelandMap = WastelandMap.fromInput(testData)
        assertEquals(2, wastelandMap.navigate())
    }

    @Test
    fun `navigating the wasteland map tries the instructions again until it is found`() {
        val wastelandMap = WastelandMap.fromInput(testDataNotFoundFirstIteration)
        assertEquals(6, wastelandMap.navigate())
    }

    @Test
    fun `part one gives number of steps to navigate the wasteland map for test data`() {
        assertEquals(2, partOne(testData))
        assertEquals(6, partOne(testDataNotFoundFirstIteration))
    }

    @Test
    fun `part two gives number of steps to navigate the wasteland map simultaneously for test data`() {
        assertEquals(6, partTwo(testDataRunningSimultaneously))
    }

}