package com.example.judo.drink.controller

import com.example.judo.common.dto.BaseResponse
import com.example.judo.common.dto.CustomUser
import com.example.judo.drink.dto.DrinkDetailDto
import com.example.judo.drink.dto.DrinkDto
import com.example.judo.drink.entity.DrinkRecommendation
import com.example.judo.drink.service.DrinkRecommendationService
import com.example.judo.drink.service.DrinkService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/drink")
class DrinkController(
    private val drinkService : DrinkService,
    private val drinkRecommendationService: DrinkRecommendationService,
    ) {
    /**
     * 주류 전체 리스트 보기
     */
    @GetMapping("/all")
    fun getAllDrinks(pageable: Pageable): BaseResponse<Page<DrinkDto>> {
        val drinks = drinkService.getAllDrinks(pageable)
        return BaseResponse(data = drinks)
    }

    /**
     * 주류 세부 정보 보기
     */
    @GetMapping("/{drinkId}")
    fun getDrinkById(@PathVariable drinkId: Long): BaseResponse<DrinkDetailDto> {
        val drink = drinkService.getDrinkById(drinkId)
        return BaseResponse(data = drink)
    }

    /**
     * 주류 리뷰 작성하기
     */
    @PostMapping("/{drinkId}/reviews")
    fun recommendDrink(
        @PathVariable drinkId: Long,
    ): BaseResponse<DrinkRecommendation> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val result = drinkRecommendationService.recommendDrink(drinkId, userId)
        return BaseResponse(message = result)
    }

    /**
     * 리뷰 보기
     */
    @GetMapping("/{drinkId}/reviews")
    fun getReviewsByDrinkId(@PathVariable drinkId: Long): BaseResponse<List<DrinkRecommendation>> {
        val reviews = drinkRecommendationService.getReviewsByDrinkId(drinkId)
        return BaseResponse(data = reviews)
    }
}
