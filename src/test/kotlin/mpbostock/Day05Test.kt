package mpbostock

import mpbostock.Day05.CategoryMapper
import mpbostock.Day05.CategoryMap
import mpbostock.Day05.partOne
import mpbostock.Day05.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day05Test {
    private val testData = listOf(
        "seeds: 79 14 55 13",
        "",
        "seed-to-soil map:",
        "50 98 2",
        "52 50 48",
        "",
        "soil-to-fertilizer map:",
        "0 15 37",
        "37 52 2",
        "39 0 15",
        "",
        "fertilizer-to-water map:",
        "49 53 8",
        "0 11 42",
        "42 0 7",
        "57 7 4",
        "",
        "water-to-light map:",
        "88 18 7",
        "18 25 70",
        "",
        "light-to-temperature map:",
        "45 77 23",
        "81 45 19",
        "68 64 13",
        "",
        "temperature-to-humidity map:",
        "0 69 1",
        "1 0 69",
        "",
        "humidity-to-location map:",
        "60 56 37",
        "56 93 4",
    )

    @Test
    fun `source is converted to destination if in source range`() {
        val categoryMap = CategoryMap(
            sequenceOf(
                CategoryMapper.fromStartsAndLength(50, 98, 2),
                CategoryMapper.fromStartsAndLength(52, 50, 48)
            )
        )
        assertEquals(50, categoryMap.convert(98))
        assertEquals(51, categoryMap.convert(99))
        assertEquals(52, categoryMap.convert(50))
        assertEquals(99, categoryMap.convert(97))
    }

    @Test
    fun `source is just returned if not in source range`() {
        val categoryMap = CategoryMap(
            sequenceOf(
                CategoryMapper.fromStartsAndLength(50, 98, 2),
                CategoryMapper.fromStartsAndLength(52, 50, 48)
            )
        )
        assertEquals(0, categoryMap.convert(0))
        assertEquals(1, categoryMap.convert(1))
        assertEquals(48, categoryMap.convert(48))
        assertEquals(49, categoryMap.convert(49))
    }


    @Test
    fun `part one gives 35 as lowest location for test data`() {
        assertEquals(35, partOne(testData))
    }

    @Test
    fun `part two gives 46 as lowest location for test data`() {
        assertEquals(46, partTwo(testData))
    }

}