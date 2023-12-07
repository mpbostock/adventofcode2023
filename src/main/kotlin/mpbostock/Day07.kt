package mpbostock

object Day07 {
    fun partOne(input: List<String>): Int {
        return Hands.fromInput(input).totalWinnings
    }

    fun partTwo(input: List<String>): Int {
        return Hands.fromInput(input, jokersInPlay = true).totalWinnings
    }

    private val input = FileIO.readInput("day07input.txt") { s -> s }

    @JvmStatic
    fun main(args: Array<String>) {
        val partOneSolution = partOne(input)
        println(partOneSolution)

        val partTwoSolution = partTwo(input)
        println(partTwoSolution)
    }

    enum class Card(val symbol: Char, val strength: Int) {
        Ace('A', 14),
        King('K', 13),
        Queen('Q', 12),
        Jack('J', 11),
        Ten('T', 10),
        Nine('9', 9),
        Eight('8', 8),
        Seven('7', 7),
        Six('6', 6),
        Five('5', 5),
        Four('4', 4),
        Three('3', 3),
        Two('2', 2),
        Joker('J', 1);

        companion object {
            fun fromSymbol(symbol: Char, jokersInPlay: Boolean = false): Card {
                return when (symbol) {
                    'J' -> if (jokersInPlay) Joker else Jack
                    else -> Card.values().single { it.symbol == symbol }
                }
            }

        }
    }

    enum class HandType(val strength: Int) {
        FiveOfAKind(7),
        FourOfAKind(6),
        FullHouse(5),
        ThreeOfAKind(4),
        TwoPair(3),
        OnePair(2),
        HighCard(1);

        companion object {
            fun fromCards(cards: List<Card>): HandType {
                val cardGroups = cards.replaceJokers().groupBy { it }
                return when (cardGroups.size) {
                    1 -> FiveOfAKind
                    2 -> if (cardGroups.any { it.value.size == 4 }) FourOfAKind else FullHouse
                    3 -> if (cardGroups.any { it.value.size == 3 }) ThreeOfAKind else TwoPair
                    4 -> OnePair
                    else -> HighCard
                }
            }
        }
    }

    data class Hand(val type: HandType, val cards: List<Card>, val bid: Int) : Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            return compareValuesBy(
                this,
                other,
                { it.type.strength },
                { it.cards.component1().strength },
                { it.cards.component2().strength },
                { it.cards.component3().strength },
                { it.cards.component4().strength },
                { it.cards.component5().strength },
            )
        }

        companion object {
            fun fromString(s: String, jokersInPlay: Boolean = false): Hand {
                val cardsBidSplit = s.split(" ")
                val cards = cardsBidSplit.first().map { Card.fromSymbol(it, jokersInPlay) }
                val type = HandType.fromCards(cards)
                val bid = cardsBidSplit.last().toInt()
                return Hand(type, cards, bid)
            }
        }
    }

    class Hands(val hands: List<Hand>) {
        val totalWinnings: Int by lazy { hands.sorted().mapIndexed { index, hand -> (index + 1) * hand.bid }.sum() }

        companion object {
            fun fromInput(input: List<String>, jokersInPlay: Boolean = false): Hands {
                return Hands(input.map { Hand.fromString(it, jokersInPlay) })
            }
        }
    }


    fun List<Card>.replaceJokers(): List<Card> {
        val bestReplacement = this.filterNot { it == Card.Joker }
            .groupBy { it }
            .maxByOrNull { it.value.size }?.key
            ?: this.maxBy { it.strength }
        return this.map { if (it == Card.Joker) bestReplacement else it }
    }
}