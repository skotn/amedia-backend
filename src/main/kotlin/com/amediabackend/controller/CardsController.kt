package com.amediabackend.controller

import com.amediabackend.model.Card
import com.amediabackend.model.Hand
import com.amediabackend.service.CardsService
import org.apache.coyote.BadRequestException
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])//should be frontend url
@RestController
class CardsController(private val cardsService: CardsService) {

    @PostMapping("/hands")
    fun generateHand(): Hand {
        return cardsService.createHand()
    }


    @GetMapping("/hands")
    fun getPreviousHands(): List<Card> {
        return cardsService.previousCards()
    }

    @PostMapping("/compare")
    fun compareHands(@RequestBody hands: List<List<Card>>): Hand {
        validateHands(hands)
        return cardsService.compareHands(hands)
    }

    private fun validateHands(hands: List<List<Card>>) {
        if (hands.isEmpty()) throw BadRequestException("Can not be empty list")
        hands.forEach { if (it.size != 5) throw BadRequestException("Hand has wrong amount of cards") }
    }

}
