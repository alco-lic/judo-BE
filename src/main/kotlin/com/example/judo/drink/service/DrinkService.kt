package com.example.judo.drink.service

import com.example.judo.common.exception.InvalidInputException
import com.example.judo.drink.dto.DrinkDetailDto
import com.example.judo.drink.dto.DrinkDto
import com.example.judo.drink.repository.DrinkRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DrinkService(
    private val drinkRepository: DrinkRepository,
) {
    fun getAllDrinks(pageable: Pageable): Page<DrinkDto>? {
        val drinkPage = drinkRepository.findAll(pageable)
        return drinkPage.map{it.toDto()}
    }

    fun getDrinkById(drinkId: Long): DrinkDetailDto? {
        val drink = drinkRepository.findByIdOrNull(drinkId)
            ?: throw InvalidInputException("제품이 존재하지 않습니다.")

        return drink.toDetailDto()
    }
}