package mpbostock

object Day01 {
    fun partOne(input: List<String>): Int {
        return input.sumOf { calibrationValues(it) }
    }

    fun partTwo(input: List<String>): Int {
        return input.sumOf { calibrationValues(it) { convertWordsToDigits(it) } }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }

    private val input = FileIO.readInput("day01input.txt") { s -> s }
    private val wordDigits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )
    private val digitRegex = "one|two|three|four|five|six|seven|eight|nine".toRegex()

    fun calibrationValues(s: String, digitsConverter: (String) -> List<Int> = ::filterDigits): Int {
        val digits = digitsConverter(s)
        return "${digits.first()}${digits.last()}".toInt()
    }

    fun filterDigits(s: String): List<Int> {
        return s.mapNotNull { it.digitToIntOrNull() }
    }

    fun convertWordsToDigits(s: String): List<Int> {
        return s.foldIndexed(emptyList()) { index, acc, c ->
            when {
                c.isDigit() -> acc + c.digitToInt()
                else -> {
                    digitRegex.find(s, index)?.let {
                        if (it.range.first == index) acc + wordDigits[it.value]!! else acc
                    } ?: acc
                }
            }
        }
    }


}