package mpbostock

import kotlin.math.pow

object Day04 {
    fun partOne(input: List<ScratchCard>): Int {
        return input.sumOf { it.points }
    }

    fun partTwo(input: List<ScratchCard>): Int {
        return ScratchCard.totalWinningCards(input)
    }

    private val input = FileIO.readInput("day04input.txt") { s -> ScratchCard.fromString(s) }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }

    data class ScratchCard(val cardNumber: Int, val winningNumbers: List<Int>, val givenNumbers: List<Int>) {
        val points: Int get() {
            return if (numbersMatchedCount > 0) 2.0.pow(numbersMatchedCount - 1).toInt() else 0
        }

        private val numbersMatchedCount: Int by lazy { winningNumbers.intersect(givenNumbers.toSet()).size }

        companion object {
            private val cardIdRegex = """Card (\d+)""".toRegex()
            fun fromString(s: String): ScratchCard {
                val winningGivenSplit = s.split('|')
                val cardIdNumberSplit = winningGivenSplit[0].split(':')
                val cardIdMatch = cardIdRegex.find(cardIdNumberSplit[0].squashAndTrim())!!
                val (id) = cardIdMatch.destructured
                val winningNumbers = cardIdNumberSplit[1].squashAndTrim().toListOfInts()
                val givenNumbers = winningGivenSplit[1].squashAndTrim().toListOfInts()
                return ScratchCard(id.toInt(), winningNumbers, givenNumbers)
            }

            fun totalWinningCards(cards: List<ScratchCard>) : Int {
                fun Map<Int, Int>.getNumCards(cardNumber: Int) = this.getOrDefault(cardNumber, 0)
                val cardNumberCounts = cards.fold(cards.associate { it.cardNumber to 1 }) { acc, card ->
                    val cardNumber = card.cardNumber
                    val numSameCard = acc.getNumCards(cardNumber)
                    when (val numbersMatchedCount = card.numbersMatchedCount) {
                        0 -> acc
                        else -> {
                            acc + (1..numbersMatchedCount).map {
                                val winningCardNumber = cardNumber + it
                                Pair(winningCardNumber, acc.getNumCards(winningCardNumber) + numSameCard)
                            }
                        }
                    }
                }
                return cardNumberCounts.values.sum()
            }
        }
    }
}