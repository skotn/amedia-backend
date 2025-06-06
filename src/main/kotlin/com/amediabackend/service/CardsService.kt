package com.amediabackend.service

import com.amediabackend.dao.CardRepository
import com.amediabackend.model.Card
import com.amediabackend.model.Hand
import com.amediabackend.model.PokerHandEnum
import org.springframework.stereotype.Service

@Service
class CardsService(val cardRepository: CardRepository) {
    private val type = listOf("h", "s", "r", "k")
    private val actualNumber = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)

    fun previousCards(): List<Card> = cardRepository.findAll()

    fun createHand(): Hand {
        val shuffledDeck = generateDeck().shuffled()
        val hand = shuffledDeck.take(5)
        cardRepository.saveAll(hand)
        return Hand(hand, analyseHand(hand).analysis)
    }

    private fun analyseHand(cards: List<Card> ): PokerHandEnum {
        val valueCounts = cards.groupingBy { it.number }.eachCount().values.sortedDescending()
        val isFlush = cards.all{ it.type == cards[0].type }
        val sorted = cards.sortedBy{ it.number }.map { it.number }
        val isStraight = sorted.zipWithNext().all { it.second == it.first + 1 } ||
                sorted == listOf(2, 3, 4, 5, 14)

        return when {
            isFlush && isStraight -> PokerHandEnum.STRAIGHT_FLUSH
            valueCounts == listOf(4, 1) -> PokerHandEnum.FOUR_OF_A_KIND
            valueCounts == listOf(3, 2) -> PokerHandEnum.FULL_HOUSE
            isFlush -> PokerHandEnum.FLUSH
            isStraight -> PokerHandEnum.STRAIGHT
            valueCounts == listOf(3, 1, 1) -> PokerHandEnum.THREE_OF_A_KIND
            valueCounts == listOf(2, 2, 1) -> PokerHandEnum.TWO_PAIR
            valueCounts == listOf(2, 1, 1, 1) -> PokerHandEnum.ONE_PAIR
            else -> PokerHandEnum.HIGH_CARD
        }
    }


    fun compareHands(hands: List<List<Card>>): Hand {
        val (bestCards, bestRank) = hands
            .map { it to analyseHand(it) }
            .maxBy { (_, rank) -> rank.value }

        return Hand(bestCards, bestRank.analysis)
    }

    private fun generateDeck(): List<Card> = type.flatMap { t -> actualNumber.map {  Card(number= it, type = t) } }.toList()

}