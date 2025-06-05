package com.amediabackend.controller

import com.amediabackend.model.Card
import com.amediabackend.model.Hand
import com.amediabackend.service.CardsService
import org.springframework.web.bind.annotation.*

@RestController
class CardsController(private val cardsService: CardsService) {

    @PostMapping("/hands")
    fun generateHand(): Hand {
        return cardsService.createHand()
    }


    @GetMapping("/hands")
    fun getPreviousHands(): List<Card> {
        return cardsService.previousHands()
    }

}
