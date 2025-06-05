package com.amediabackend.service

import com.amediabackend.dao.CardRepository
import com.amediabackend.model.Card
import com.amediabackend.model.Hand
import org.springframework.stereotype.Service

@Service
class CardsService(val cardRepository: CardRepository) {
    private val type = listOf("h", "s", "r", "k")
    private val actualNumber = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)

    fun previousHands(): List<Card> = cardRepository.findAll()

    fun createHand(): Hand {
        val shuffledDeck = generateDeck().shuffled()
        val hand = shuffledDeck.take(5)
        cardRepository.saveAll(hand)
        return Hand(hand, analyseHand(hand))
    }

    private fun analyseHand(cards: List<Card> ): String {
        val valueCounts = cards.groupingBy { it.number }.eachCount().values.sortedDescending()
        val isFlush = cards.all{ it.type == cards[0].type }
        val sorted = cards.sortedBy{ it.number }.map { it.number }
        val isStraight = sorted.zipWithNext().all { it.second == it.first + 1 } ||
                sorted == listOf(2, 3, 4, 5, 14)

        return when {
            isFlush && isStraight -> "Straight flush"
            valueCounts == listOf(4, 1) -> "Four of a kind"
            valueCounts == listOf(3, 2) -> "Full house"
            isFlush -> "Flush"
            isStraight -> "Straight"
            valueCounts == listOf(3, 1, 1) -> "Three of a kind"
            valueCounts == listOf(2, 2, 1) -> "Two pair"
            valueCounts == listOf(2, 1, 1, 1) -> "One pair"
            else -> "High card"
        }
    }

    private fun generateDeck(): List<Card> = type.flatMap { t -> actualNumber.map {  Card(number= it, type = t) } }.toList()

}