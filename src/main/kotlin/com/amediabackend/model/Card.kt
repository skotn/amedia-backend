package com.amediabackend.model

import jakarta.persistence.*

private val numbersMap =
    mapOf(14 to "1", 2 to "2", 3 to "3", 4 to "4", 5 to "5", 6 to
            "6", 7 to "7", 8 to "8", 9 to "9", 10 to "t", 11 to "j", 12 to "d", 13 to "k")

@Entity
@Table(name = "cards")
data class Card(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val number: Int,

    val type: String

) {

    override fun toString(): String = number.getCardNumberValue() + type

    private fun Int.getCardNumberValue(): String = numbersMap[this].orEmpty()
}