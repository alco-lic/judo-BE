package com.example.judo.drink.repository

import com.example.judo.drink.entity.Drink
import com.example.judo.drink.entity.DrinkType
import org.springframework.data.jpa.repository.JpaRepository

interface DrinkRepository : JpaRepository<Drink, Long> {
    fun findByTypeOrTasteProfile(type: DrinkType?, tasteProfile: String?): Collection<Drink>
}
