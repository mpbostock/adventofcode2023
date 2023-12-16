package mpbostock

object Day15 {
    fun partOne(input: String): Int {
        return input.split(",").sumOf { hash(it) }
    }

    fun partTwo(input: String): Int {
        return Boxes.fromString(input).also { it.initialise() }.focussingPower()
    }

    private val input = FileIO.readInput("day15input.txt") { s -> s }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input.first())
        println(partOneSolution)

        val partTwoSolution = partTwo(input.first())
        println(partTwoSolution)
    }

    fun hash(s: String): Int {
        return s.fold(0) { acc, c ->
            (acc + c.code) * 17 % 256
        }
    }

    sealed interface Step {
        val label: String
        val boxId: Int get() = hash(label)
        companion object {
            fun fromString(s: String): Step {
                return if (s.contains('=')) {
                    AddLens.fromString(s)
                } else {
                    RemoveLens.fromString(s)
                }
            }
        }
    }

    class AddLens(override val label: String, val focalLength: Int) : Step {
        companion object {
            fun fromString(s: String): AddLens {
                val split = s.split("=")
                return AddLens(split.first(), split.last().toInt())
            }
        }
    }
    class RemoveLens(override val label: String) : Step {
        companion object {
            fun fromString(s: String): RemoveLens {
                val split = s.split("-")
                return RemoveLens(split.first())
            }
        }
    }

    data class Lens(val label: String, val focalLength: Int)
    data class Boxes(val steps: List<Step>, private val boxes: List<ArrayDeque<Lens>> = List(256) { ArrayDeque() }) {
        fun initialise() {
            steps.onEach {
                val box = boxes[it.boxId]
                when (it) {
                    is AddLens -> {
                        val indexOfLens = box.indexOfFirst { lens -> lens.label == it.label }
                        if (indexOfLens != -1) {
                            box[indexOfLens] = box[indexOfLens].copy(focalLength = it.focalLength)
                        } else {
                            box.add(Lens(it.label, it.focalLength))
                        }
                    }
                    is RemoveLens -> {
                        box.removeIf { lens -> lens.label == it.label }
                    }
                }
            }
        }
        fun focussingPower(): Int {
            return boxes.foldIndexed(0) {boxIndex, acc, lenses ->
                acc + lenses.mapIndexed { lensIndex, lens ->
                    (boxIndex + 1) * (lensIndex + 1) * lens.focalLength
                }.sum()
            }
        }
        companion object {
            fun fromString(s: String): Boxes {
                val steps = s.split(",").map { Step.fromString(it) }
                return Boxes(steps)
            }
        }
    }
}