package mpbostock

object Day08 {
    fun partOne(input: List<String>): Long {
        return WastelandMap.fromInput(input).navigate()
    }

    fun partTwo(input: List<String>): Long {
        return WastelandMap.fromInput(input).navigateSimultaneously()
    }

    private val input = FileIO.readInput("day08input.txt") { s -> s }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }

    enum class Instruction(val symbol: Char, val follow: (Pair<String, String>) -> String) {
        Left('L', { pair -> pair.first }),
        Right('R', { pair -> pair.second });
        companion object {
            fun fromSymbol(symbol: Char): Instruction {
                return Instruction.values().single { it.symbol == symbol }
            }
        }
    }

    data class Network(private val nodes: Map<String, Pair<String, String>>) {
        val start = "AAA"
        fun getNode(key: String) = nodes[key]!!
        fun getSimultaneousStartingNodes(): List<String> {
            return nodes.keys.filter { it.endsWith("A") }
        }
        companion object {
            private val nodeRegex = """(\w+) = \((\w+), (\w+)\)""".toRegex()
            fun fromInput(input: List<String>): Network {
                val nodes = input.associate {
                    val match = nodeRegex.find(it)!!
                    val (name, left, right) = match.destructured
                    name to Pair(left, right)
                }
                return Network(nodes)
            }
        }
    }

    class WastelandMap(private val instructions: Sequence<Instruction>, private val network: Network) {
        fun navigate(): Long {
            return followInstructions(network.start)
        }

        fun navigateSimultaneously(): Long {
            val stepsForEachPath = network.getSimultaneousStartingNodes().map { followInstructions(it) }
            return stepsForEachPath.drop(1).fold(stepsForEachPath.first()) {acc, i -> lowestCommonMultiple(acc, i) }
        }

        private fun followInstructions(start: String): Long {
            val steps = instructions.fold(Pair(start, 0L)) { acc, instruction ->
                when {
                    acc.first.endsWith("Z") -> return acc.second
                    else -> Pair(instruction.follow(network.getNode(acc.first)), acc.second + 1)
                }
            }
            return steps.second
        }

        companion object {
            fun fromInput(input: List<String>): WastelandMap {
                val instructions = input.first().map { Instruction.fromSymbol(it) }.asSequence().indefinitely()
                val network = Network.fromInput(input.drop(2))
                return WastelandMap(instructions, network)
            }
        }
    }
}