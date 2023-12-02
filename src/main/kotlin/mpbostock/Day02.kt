package mpbostock

object Day02 {
    fun partOne(input: List<Game>): Int {
        return input.filter { it.isPossible }.sumOf { it.id }
    }

    fun partTwo(input: List<Game>): Int {
        return input.map { it.minimumPossibleCubeSet }.sumOf { it.power }
    }

    private val input = FileIO.readInput("day02input.txt") { s -> Game.fromString(s) }

    data class Game(val id: Int, private val cubeSets: List<CubeSet>) {
        val isPossible get() = cubeSets.all { it.isPossible }

        val minimumPossibleCubeSet: CubeSet get() {
            val redCubesNeeded = cubeSets.maxBy { it.redCubeCount }.redCubeCount
            val greenCubesNeeded = cubeSets.maxBy { it.greenCubeCount }.greenCubeCount
            val blueCubesNeeded = cubeSets.maxBy { it.blueCubeCount }.blueCubeCount
            return CubeSet.fromCubeCounts(redCubesNeeded, greenCubesNeeded, blueCubesNeeded)
        }

        companion object {
            private val gameIdRegex = """Game (\d+)""".toRegex()
            fun fromString(s: String): Game {
                val gameCubesSplit = s.split(':')
                val gameId = gameCubesSplit[0]
                val matchResult = gameIdRegex.find(gameId)!!
                val (id) = matchResult.destructured
                val cubeSets = gameCubesSplit[1].split(';').map {
                    CubeSet.fromString(it)
                }
                return Game(id.toInt(), cubeSets)
            }
        }
    }

    enum class Cube(val maxAllowed: Int) {
        Red(12),
        Green(13),
        Blue(14),
        Unknown(0);

        companion object {
            fun fromString(s: String): Cube {
                return when (s) {
                    "red" -> Red
                    "green" -> Green
                    "blue" -> Blue
                    else -> Unknown
                }
            }
        }
    }

    data class CubeSet(private val cubes: List<Cube>) {
        val power get() = redCubeCount * greenCubeCount * blueCubeCount
        val isPossible get() = redCubeCount <= Cube.Red.maxAllowed && greenCubeCount <= Cube.Green.maxAllowed && blueCubeCount <= Cube.Blue.maxAllowed
        val redCubeCount get() = cubes.count { it == Cube.Red }
        val greenCubeCount get() = cubes.count { it == Cube.Green }
        val blueCubeCount get() = cubes.count { it == Cube.Blue }

        companion object {
            private val cubeRegex = """(\d+) (red|green|blue)""".toRegex()

            fun fromCubeCounts(redCount: Int, greenCount: Int, blueCount: Int): CubeSet {
                val cubes = Cube.Red.repeat(redCount) + Cube.Green.repeat(greenCount) + Cube.Blue.repeat(blueCount)
                return CubeSet(cubes)
            }

            fun fromString(s: String): CubeSet {
                val cubes = s.trim().split(',').flatMap {
                    val matchResult = cubeRegex.find(it)!!
                    val (count, colour) = matchResult.destructured
                    Cube.fromString(colour).repeat(count.toInt())
                }
                return CubeSet(cubes)
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