package mpbostock

import mpbostock.Day07.Card.*
import mpbostock.Day07.Card
import mpbostock.Day07.Hand
import mpbostock.Day07.HandType.*
import mpbostock.Day07.HandType
import mpbostock.Day07.Hands
import mpbostock.Day07.partOne
import mpbostock.Day07.partTwo
import mpbostock.Day07.replaceJokers
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day07Test {
    private val testData = listOf(
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483",
    )

    private val expectedHands = listOf(
        Hand(OnePair, listOf(Three, Two, Ten, Three, King), 765),
        Hand(ThreeOfAKind, listOf(Ten, Five, Five, Jack, Five), 684),
        Hand(TwoPair, listOf(King, King, Six, Seven, Seven), 28),
        Hand(TwoPair, listOf(King, Ten, Jack, Jack, Ten), 220),
        Hand(ThreeOfAKind, listOf(Queen, Queen, Queen, Jack, Ace), 483)
    )

    private val expectedSortedHands = listOf (
        Hand(OnePair, listOf(Three, Two, Ten, Three, King), 765),
        Hand(TwoPair, listOf(King, Ten, Jack, Jack, Ten), 220),
        Hand(TwoPair, listOf(King, King, Six, Seven, Seven), 28),
        Hand(ThreeOfAKind, listOf(Ten, Five, Five, Jack, Five), 684),
        Hand(ThreeOfAKind, listOf(Queen, Queen, Queen, Jack, Ace), 483)
    )

    @Test
    fun `cards are converted from their symbol when jokers aren't in play`() {
        val symbols = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
        val expectedCards = listOf(Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace)
        for (i in symbols.indices) {
            assertEquals(expectedCards[i], Card.fromSymbol(symbols[i]))
        }
    }

    @Test
    fun `a J gives a joker when jokers are in play`() {
        assertEquals(Joker, Card.fromSymbol('J', true))
    }

    @Test
    fun `ace is stronger than king`() {
        assertTrue(Ace.strength > King.strength)
    }

    @Test
    fun `king is stronger than queen`() {
        assertTrue(King.strength > Queen.strength)
    }

    @Test
    fun `queen is stronger than jack`() {
        assertTrue(Queen.strength > Jack.strength)
    }

    @Test
    fun `jack is stronger than ten`() {
        assertTrue(Jack.strength > Ten.strength)
    }

    @Test
    fun `ten is stronger than nine`() {
        assertTrue(Ten.strength > Nine.strength)
    }

    @Test
    fun `nine is stronger than eight`() {
        assertTrue(Nine.strength > Eight.strength)
    }

    @Test
    fun `eight is stronger than seven`() {
        assertTrue(Eight.strength > Seven.strength)
    }

    @Test
    fun `seven is stronger than six`() {
        assertTrue(Seven.strength > Six.strength)
    }

    @Test
    fun `six is stronger than five`() {
        assertTrue(Six.strength > Five.strength)
    }

    @Test
    fun `five is stronger than four`() {
        assertTrue(Five.strength > Four.strength)
    }

    @Test
    fun `four is stronger than three`() {
        assertTrue(Four.strength > Three.strength)
    }

    @Test
    fun `three is stronger than two`() {
        assertTrue(Three.strength > Two.strength)
    }

    @Test
    fun `two is stronger than joker`() {
        assertTrue(Two.strength > Joker.strength)
    }

    @Test
    fun `five of a kind is stronger than four of a kind`() {
        assertTrue(FiveOfAKind.strength > FourOfAKind.strength)
    }

    @Test
    fun `four of a kind is stronger full house`() {
        assertTrue(FourOfAKind.strength > FullHouse.strength)
    }

    @Test
    fun `full house is stronger than three of a kind`() {
        assertTrue(FullHouse.strength > ThreeOfAKind.strength)
    }

    @Test
    fun `three of a kind is stronger two pair`() {
        assertTrue(ThreeOfAKind.strength > TwoPair.strength)
    }

    @Test
    fun `two pair is stronger than one pair`() {
        assertTrue(TwoPair.strength > OnePair.strength)
    }

    @Test
    fun `one pair is stronger than high card`() {
        assertTrue(OnePair.strength > HighCard.strength)
    }

    @Test
    fun `five of a kind is given when all cards match`() {
        assertEquals(FiveOfAKind, HandType.fromCards(King.repeat(5)))
    }

    @Test
    fun `four of a kind is given when 4 cards match`() {
        assertEquals(FourOfAKind, HandType.fromCards(King.repeat(4) + Queen))
    }

    @Test
    fun `full house is given when 3 cards the same and 2 cards the same`() {
        assertEquals(FullHouse, HandType.fromCards(King.repeat(3) + Queen.repeat(2)))
    }

    @Test
    fun `three of a kind is given when 3 cards the same and 2 cards not the same`() {
        assertEquals(ThreeOfAKind, HandType.fromCards(King.repeat(3) + Queen + Jack))
    }

    @Test
    fun `two pair is given when 2 sets of 2 cards`() {
        assertEquals(TwoPair, HandType.fromCards(King.repeat(2) + Queen.repeat(2) + Jack))
    }

    @Test
    fun `one pair is given when 2 sets of 2 cards`() {
        assertEquals(OnePair, HandType.fromCards(King.repeat(2) + Queen + Jack + Ten))
    }

    @Test
    fun `high card is given when all cards are different`() {
        assertEquals(HighCard, HandType.fromCards(listOf(Ace, King, Queen, Jack, Ten)))
    }

    @Test
    fun `hands and bids are read from test input`() {
        val hands = Hands.fromInput(testData)
        assertEquals(expectedHands, hands.hands)
    }

    @Test
    fun `hands are sorted by type strengthv then card strength in order from first to fifth`() {
        val hands = Hands.fromInput(testData)
        assertEquals(expectedSortedHands, hands.hands.sorted())
    }

    @Test
    fun `total winnings is the sum of the sorted cards rank multiplied by the bid`() {
        val hands = Hands.fromInput(testData)
        assertEquals(6440, hands.totalWinnings)
    }

    @Test
    fun `jokers are replaced with most frequent card if less or equal jokers than others`() {
        val cardsWithJokers = listOf(
            listOf(Ten, Five, Five, Joker, Five),
            listOf(King, Ten, Joker, Joker, Ten),
            listOf(Queen, Queen, Queen, Joker, Ace)
        )
        val expectedReplacedCards = listOf(
            listOf(Ten, Five, Five, Five, Five),
            listOf(King, Ten, Ten, Ten, Ten),
            listOf(Queen, Queen, Queen, Queen, Ace)
        )
        for (i in expectedReplacedCards.indices) {
            assertEquals(expectedReplacedCards[i], cardsWithJokers[i].replaceJokers())
        }
    }

    @Test
    fun `jokers are replaced with strongest card if more jokers than others`() {
        val cardsWithJokers = listOf(
            listOf(Ten, Joker, Joker, Joker, Five),
            listOf(King, Nine, Joker, Joker, Ten),
            listOf(Joker, Joker, Joker, Joker, Ace)
        )
        val expectedReplacedCards = listOf(
            listOf(Ten, Ten, Ten, Ten, Five),
            listOf(King, Nine, King, King, Ten),
            listOf(Ace, Ace, Ace, Ace, Ace)
        )
        for (i in expectedReplacedCards.indices) {
            assertEquals(expectedReplacedCards[i], cardsWithJokers[i].replaceJokers())
        }
    }

    @Test
    fun `if cards are all jokers they are not replaced as it would still be five of a kind`() {
        val allJokers = Joker.repeat(5)
        assertEquals(allJokers, allJokers.replaceJokers())
    }

    @Test
    fun `part one gives total winnings for test data`() {
        assertEquals(6440, partOne(testData))
    }

    @Test
    fun `part two gives total winnings including jokers for test data`() {
        assertEquals(5905, partTwo(testData))
    }

}