package com.amediabackend

import com.amediabackend.dao.CardRepository
import com.amediabackend.model.Card
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AmediaBackendApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var cardsRepository: CardRepository

    @Autowired
    private lateinit var mapper: ObjectMapper


    @Test
    fun contextLoads() {
    }

    @Test
    fun `get previous cards returns cards in db`() {
        //set up data
        val card = Card(number=3, type="k")
        cardsRepository.save(card)


        //Run test
        val apiResult = mockMvc.perform(
            get("/hands"))
            .andExpect(status().isOk)
            .andReturn()

        val responseJson = apiResult.response.contentAsString
        val cardsList: List<Card> = mapper.readValue(responseJson)

        //Assertions
        assertEquals(1, cardsList.size)
        assertEquals(card.number, cardsList[0].number)
        assertEquals(card.type, cardsList[0].type)
    }

}
