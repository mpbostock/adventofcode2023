package mpbostock

object Day03 {
    fun partOne(input: List<String>): Int {
        return EngineSchematic.fromInput(input).getValidPartNumbers().sumOf { it }
    }

    fun partTwo(input: List<String>): Int {
        return EngineSchematic.fromInput(input).getValidGearRatios().sumOf { it }
    }

    private val input = FileIO.readInput("day03input.txt") { s -> s }

    enum class SchematicElementType(val rowMapper: (y: Int, s: String) -> List<SchematicElement>) {
        PartNumber({ y, s ->
            val regex = """(\d+)""".toRegex()
            regex.findAll(s).map {
                SchematicElement(PartNumber, y..y, it.range, it.value)
            }.toList()
        }),
        Symbol({ y, s ->
            val regex = """[^a-zA-Z\d\s:]""".toRegex()
            // extend the symbol's x and y range by one in each direction, so it can intersect with part numbers
            val yRange = IntRange(y - 1, y + 1)
            regex.findAll(s).map {
                val xRange = IntRange(it.range.first - 1, it.range.last + 1)
                SchematicElement(Symbol, yRange, xRange, it.value)
            }.toList()
        })
    }

    data class SchematicElement(
        val type: SchematicElementType,
        val yRange: IntRange,
        val xRange: IntRange,
        val value: String
    ) {
        fun intersects(other: SchematicElement): Boolean {
            return xRange.intersects(other.xRange) && yRange.intersects(other.yRange)
        }

        companion object {
            fun fromRow(y: Int, s: String): List<SchematicElement> {
                val strippedPeriods = s.replace('.', ' ')
                val partNumbers = SchematicElementType.PartNumber.rowMapper(y, strippedPeriods)
                val symbols = SchematicElementType.Symbol.rowMapper(y, strippedPeriods)
                return partNumbers + symbols
            }
        }
    }

    data class EngineSchematic(private val schematicElements: List<SchematicElement>) {
        fun getValidPartNumbers(): List<Int> {
            return schematicElements.filter { it.type == SchematicElementType.Symbol }
                .flatMap { symbol ->
                    schematicElements.filter {
                        it.type == SchematicElementType.PartNumber && symbol.intersects(it)
                    }
                }
                .map { it.value.toInt() }
        }

        fun getValidGearRatios(): List<Int> {
            return schematicElements.filter { it.type == SchematicElementType.Symbol && it.value == "*" }
                .map { gear ->
                    schematicElements.filter {
                        it.type == SchematicElementType.PartNumber && gear.intersects(it)
                    }
                }
                .filter { it.size == 2 }
                .map { it[0].value.toInt() * it[1].value.toInt() }
        }

        companion object {
            fun fromInput(input: List<String>): EngineSchematic {
                val schematicElements = input.flatMapIndexed { y: Int, s: String ->
                    SchematicElement.fromRow(y, s)
                }
                return EngineSchematic(schematicElements)
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }

}