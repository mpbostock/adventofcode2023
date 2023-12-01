package mpbostock

import mpbostock.Day01.calibrationValues
import mpbostock.Day01.convertWordsToDigits
import mpbostock.Day01.filterDigits
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day01Test {
    private val partOneTestData = listOf(
        "1abc2",
        "pqr3stu8vwx",
        "a1b2c3d4e5f",
        "treb7uchet"
    )

    private val expectedPartOneDigits = listOf(
        listOf(1, 2),
        listOf(3, 8),
        listOf(1, 2, 3, 4, 5),
        listOf(7),
    )

    private val partTwoTestData = listOf(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen"
    )

    private val expectedPartTwoDigits = listOf(
        listOf(2, 1, 9),
        listOf(8, 2, 3),
        listOf(1, 2, 3),
        listOf(2, 1, 3, 4),
        listOf(4, 9, 8, 7, 2),
        listOf(1, 8, 2, 3, 4),
        listOf(7, 6),
    )

    @Test
    fun `the first digit is returned from the string`() {
        for (i in expectedPartOneDigits.indices) {
            assertEquals(expectedPartOneDigits[i].first(), filterDigits(partOneTestData[i]).first())
        }
    }

    @Test
    fun `the first digit or word digit is returned from the string`() {
        for (i in expectedPartTwoDigits.indices) {
            assertEquals(expectedPartTwoDigits[i].first(), convertWordsToDigits(partTwoTestData[i]).first())
        }
    }

    @Test
    fun `the last digit is returned from the string`() {
        for (i in expectedPartOneDigits.indices) {
            assertEquals(expectedPartOneDigits[i].last(), filterDigits(partOneTestData[i]).last())
        }
    }

    @Test
    fun `the last digit or word digit is returned from the string`() {
        for (i in expectedPartTwoDigits.indices) {
            assertEquals(expectedPartTwoDigits[i].last(), convertWordsToDigits(partTwoTestData[i]).last())
        }
    }

    @Test
    fun `the first and last digits are combined into an integer from the string`() {
        val integers = listOf(12, 38, 15, 77)
        for (i in integers.indices) {
            assertEquals(
                integers[i],
                calibrationValues(partOneTestData[i]),
                "The combined integer did not equal ${integers[i]}"
            )
        }
    }

    @Test
    fun `the first and last digits or word digits are combined into an integer from the string`() {
        val integers = listOf(29, 83, 13, 24, 42, 14, 76)
        for (i in integers.indices) {
            assertEquals(
                integers[i],
                calibrationValues(partTwoTestData[i], ::convertWordsToDigits),
                "The combined integer did not equal ${integers[i]}"
            )
        }
    }

    @Test
    fun `part one gives expected results for test data`() {
        assertEquals(142, Day01.partOne(partOneTestData))
    }

    @Test
    fun `part two gives expected results for test data`() {
        assertEquals(281, Day01.partTwo(partTwoTestData))
    }

}