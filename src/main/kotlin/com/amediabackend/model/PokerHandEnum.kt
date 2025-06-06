package com.amediabackend.model

enum class PokerHandEnum(val value: Int, val analysis: String) {
    STRAIGHT_FLUSH(8, "Straight flush"),
    FOUR_OF_A_KIND(7, "Four of a kind"),
    FULL_HOUSE(6, "Full house"),
    FLUSH(5, "Flush"),
    STRAIGHT(4, "Straight"),
    THREE_OF_A_KIND(3, "Three of a kind"),
    TWO_PAIR(2, "Two pair"),
    ONE_PAIR(1, "One pair"),
    HIGH_CARD(0, "High card");

    override fun toString(): String = analysis
}