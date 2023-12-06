package mpbostock

object Day06 {
    fun partOne(input: List<String>): Int {
        return BoatRaces.fromInputPartOne(input).waysToWinEachRaceMultiple()
    }

    fun partTwo(input: List<String>): Int {
        return BoatRaces.fromInputPartTwo(input).waysToWinEachRaceMultiple()
    }

    private val input = FileIO.readInput("day06input.txt") { s -> s }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }

    data class BoatRace(val time: Long, val recordDistance: Long) {
        fun numberOfWaysToWin(): Int {
            return (0..time).map { speed -> (time - speed) * speed }.count { it > recordDistance }
        }
    }
    data class BoatRaces(val races: Sequence<BoatRace>) {
        fun waysToWinEachRaceMultiple(): Int {
            return races.map { it.numberOfWaysToWin() }.fold(1) {acc, i -> acc * i }
        }
        companion object {
            fun fromInputPartOne(input: List<String>): BoatRaces {
                val times = parseLinePartOne(input.first())
                val distances = parseLinePartOne(input.drop(1).first())
                return BoatRaces(times.zip(distances).map { BoatRace(it.first, it.second) }.asSequence())
            }

            fun fromInputPartTwo(input: List<String>): BoatRaces {
                val time = parseLinePartTwo(input.first())
                val distance = parseLinePartTwo(input.drop(1).first())
                return BoatRaces(sequenceOf(BoatRace(time, distance)))
            }

            private fun parseLinePartOne(line: String) = line.split(":").last().squashAndTrim().toListOfLongs()
            private fun parseLinePartTwo(line: String) = line.split(":").last().squashAndTrim().replace(" ", "").toLong()
        }
    }
}