package mpbostock

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

object Day05 {
    fun partOne(input: List<String>): Long {
        return Almanac.fromInput(input).lowestLocation()
    }

    fun partTwo(input: List<String>): Long {
        return Almanac.fromInput(input).lowestLocationSeedRanges()
    }

    private val input = FileIO.readInput("day05input.txt") { s -> s }

    @OptIn(ExperimentalTime::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val partOneTime = measureTime {
            val partOneSolution = partOne(input)
            println(partOneSolution)
        }
        println(partOneTime)

        val partTwoTime = measureTime {
            val partTwoSolution = partTwo(input)
            println(partTwoSolution)
        }
        println(partTwoTime)
    }

    class Almanac(private val seeds: Sequence<Long>, private val maps: Sequence<CategoryMap>) {
        fun lowestLocation(seeds: Sequence<Long> = this.seeds): Long {
            return seeds.map { maps.fold(it) { source, map -> map.convert(source) } }.min()
        }

        fun lowestLocationSeedRanges(): Long {
            return lowestLocation(seeds.chunked(2) { range(it.first(), it.last()) }.flatMap { it.asSequence() })
        }

        companion object {
            fun fromInput(input: List<String>): Almanac {
                return Almanac(parseSeeds(input), parseMaps(input))
            }

            private fun parseSeeds(input: List<String>) =
                input.first().split(":").last().trim().split(" ").map { it.toLong() }.asSequence()

            private fun parseMaps(input: List<String>) =
                input.drop(2).joinToString("\n").split("\n\n").map { section ->
                    CategoryMap(with(section) {
                        split("\n").drop(1).map { mapValues ->
                            val values = mapValues.trim().split(" ").map { it.toLong() }
                            CategoryMapper.fromStartsAndLength(values[0], values[1], values[2])
                        }.asSequence()
                    })
                }.asSequence()
        }
    }

    data class CategoryMapper(val destinationRange: LongRange, val sourceRange: LongRange) {
        fun convert(source: Long) = destinationRange.first + (source - sourceRange.first)

        operator fun contains(source: Long): Boolean = sourceRange.contains(source)

        companion object {
            fun fromStartsAndLength(destinationStart: Long, sourceStart: Long, length: Long): CategoryMapper {
                return CategoryMapper(range(destinationStart, length), range(sourceStart, length))
            }
        }
    }

    data class CategoryMap(val categoryMappers: Sequence<CategoryMapper>) {
        fun convert(source: Long): Long {
            return categoryMappers.find { source in it }?.convert(source) ?: source
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun range(start: Long, length: Long) = start..<(start + length)
}