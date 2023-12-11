package mpbostock

object Day09 {
    fun partOne(input: List<ValueHistory>): Int {
        return input.sumOf { it.predictNextValue() }
    }

    fun partTwo(input: List<ValueHistory>): Int {
        return input.sumOf { it.predictPreviousValue() }
    }

    private val input = FileIO.readInput("day09input.txt") { s -> ValueHistory.fromString(s) }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }

    data class ValueHistory(private val historicValues: List<Int>) {
        private val differentials: List<List<Int>> by lazy { differences(historicValues, emptyList()) }
        private tailrec fun differences(values: List<Int>, acc: List<List<Int>>): List<List<Int>> {
            return if ( values.all { it == 0 }) {
                acc
            } else {
                val diffs = values.zipWithNext().map { it.second - it.first }
                differences(diffs, acc.plus<List<Int>>(diffs))
            }
        }

        fun predictNextValue(): Int {
            return historicValues.last() + differentials.reversed().fold(0) {acc, diffs ->
                acc + diffs.last()
            }
        }

        fun predictPreviousValue(): Int {
            return historicValues.first() - differentials.reversed().fold(0) {acc, diffs ->
                diffs.first() - acc
            }
        }

        companion object {
            fun fromString(s: String): ValueHistory {
                return ValueHistory(s.toListOfInts())
            }
        }
    }
}