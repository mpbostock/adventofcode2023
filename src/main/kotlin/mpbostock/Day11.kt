package mpbostock

object Day11 {
    fun partOne(input: List<String>): Long {
        return Universe(input).sumOfShortestPaths
    }

    fun partTwo(input: List<String>): Long {
        return Universe(input, 1_000_000).sumOfShortestPaths
    }

    private val input = FileIO.readInput("day11input.txt") { s -> s }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }

    class Universe(private val input: List<String>, private val expansion: Int = 2) {
        val emptyRowIndices: Set<Int> by lazy {
            input.indices.filter { input[it].all { c -> c == '.' } }.toSet()
        }
        val emptyColumnIndices: Set<Int> by lazy {
            input.map { line -> line.indices.filter { line[it] == '.' }.toSet() }.reduce(Set<Int>::intersect)
        }

        private fun expand(coordinate: Coordinate): Coordinate {
            val deltaX = emptyColumnIndices.count { it <= coordinate.x } * (expansion - 1)
            val deltaY = emptyRowIndices.count { it <= coordinate.y } * (expansion - 1)
            return Coordinate(coordinate.x + deltaX, coordinate.y + deltaY)
        }

        val galaxies: List<Coordinate> by lazy {
            input.foldIndexed(emptyList()) {y, galaxies, line ->
                galaxies + line.indices.filter { line[it] == '#' }.map { x -> expand(Coordinate(x, y)) }
            }
        }

        val sumOfShortestPaths: Long by lazy {
            pathsBetweenGalaxies().sumOf { it.stepsBetween().toLong() }
        }

        private fun pathsBetweenGalaxies(): List<Line> {
            tailrec fun getPaths(index: Int, otherIndices: List<Int>, acc: List<Line>): List<Line> {
                return when {
                    otherIndices.isEmpty() -> acc
                    else -> {
                        getPaths(index + 1, otherIndices.drop(1), acc + otherIndices.map { Line(galaxies[index], galaxies[it]) })
                    }
                }
            }
            return getPaths(0, galaxies.indices.drop(1), emptyList())
        }

    }

}