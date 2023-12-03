package mpbostock

import mpbostock.Day03.EngineSchematic
import mpbostock.Day03.SchematicElement
import mpbostock.Day03.SchematicElementType.PartNumber
import mpbostock.Day03.SchematicElementType.Symbol
import mpbostock.Day03.partOne
import mpbostock.Day03.partTwo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class Day03Test {
    val testData = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598.."
    )

    val expectedSchematicElements = listOf(
        listOf(
            SchematicElement(PartNumber, 0..0, 0..2, "467"),
            SchematicElement(PartNumber, 0..0, 5..7, "114")
        ),
        listOf(
            SchematicElement(Symbol, 0..2, 2..4, "*")
        ),
        listOf(
            SchematicElement(PartNumber, 2..2, 2..3, "35"),
            SchematicElement(PartNumber, 2..2, 6..8, "633")
        ),
        listOf(
            SchematicElement(Symbol, 2..4, 5..7, "#")
        ),
        listOf(
            SchematicElement(PartNumber, 4..4, 0..2, "617"),
            SchematicElement(Symbol, 3..5, 2..4, "*")
        ),
        listOf(
            SchematicElement(PartNumber, 5..5, 7..8, "58"),
            SchematicElement(Symbol, 4..6, 4..6, "+")
        ),
        listOf(
            SchematicElement(PartNumber, 6..6, 2..4, "592")
        ),
        listOf(
            SchematicElement(PartNumber, 7..7, 6..8, "755")
        ),
        listOf(
            SchematicElement(Symbol, 7..9, 2..4, "$"),
            SchematicElement(Symbol, 7..9, 4..6, "*")
        ),
        listOf(
            SchematicElement(PartNumber, 9..9, 1..3, "664"),
            SchematicElement(PartNumber, 9..9, 5..7, "598")
        ),
    )

    @Test
    fun `schematic elements are read from a string in the test data`() {
        testData.onEachIndexed { y, s ->
            assertEquals(expectedSchematicElements[y], SchematicElement.fromRow(y, s))
        }
    }

    @Test
    fun `get valid part numbers returns part numbers that are adjacent to a symbol`() {
        val engineSchematic = EngineSchematic.fromInput(testData)
        val validPartNumbers = engineSchematic.getValidPartNumbers()
        assertEquals(8, validPartNumbers.size)
        assertFalse(validPartNumbers.contains(58))
        assertFalse(validPartNumbers.contains(114))
    }

    @Test
    fun `get valid gear ratios returns gear ratios of two part numbers that are adjacent to a gear symbol`() {
        val engineSchematic = EngineSchematic.fromInput(testData)
        val validPartNumbers = engineSchematic.getValidGearRatios()
        assertEquals(16345, validPartNumbers[0])
        assertEquals(451490, validPartNumbers[1])
    }

    @Test
    fun `part one returns the sum of all the valid part numbers for the test data`() {
        assertEquals(4361, partOne(testData))
    }

    @Test
    fun `part two returns the sum of all the valid gear ratios for the test data`() {
        assertEquals(467835, partTwo(testData))
    }

}