package com.example.judo.drink.service

import com.example.judo.drink.entity.DrinkRecommendation
import org.springframework.stereotype.Service

@Service
class DrinkRecommendationService {
    fun recommendDrink(drinkId: Long, userId: Long?): String {
        TODO("Not yet implemented")
    }

    fun getReviewsByDrinkId(drinkId: Long): List<DrinkRecommendation>? {
        TODO("Not yet implemented")
    }
}