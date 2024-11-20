package com.example.judo.recommendation.controller

import com.example.judo.common.dto.BaseResponse
import com.example.judo.common.dto.CustomUser
import com.example.judo.common.exception.InvalidInputException
import com.example.judo.drink.entity.Drink
import com.example.judo.recommendation.service.DrinkFeatureService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/drink-feature")
internal class DrinkFeatureController(
    private val drinkFeatureService: DrinkFeatureService
) {
    // KNN 기반 추천 API
    @GetMapping("/knn")
    fun getKnnRecommendations(
        @RequestParam userTasteProfile: String,
        @RequestParam(defaultValue = "5") k: Int
    ): BaseResponse<List<Drink>> {
        if (k <= 0) throw InvalidInputException("k는 1 이상의 값이어야 합니다.")
        if (userTasteProfile.isBlank()) throw InvalidInputException("userTasteProfile이 비어 있습니다.")

        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
            ?: throw InvalidInputException("회원 정보가 존재하지 않습니다.")

        val userVector = drinkFeatureService.vectorizeUserTasteProfile(userTasteProfile)
        val recommendations = drinkFeatureService.getKnnRecommendations(userVector, k)

        return BaseResponse(data = recommendations)
    }

    // knn 시각화
    @GetMapping("/knn/process")
    fun getKnnProcess(
        @RequestParam userTasteProfile: String,
        @RequestParam(defaultValue = "5") k: Int
    ): BaseResponse<Map<String, Any>> {
        val userVector = drinkFeatureService.vectorizeUserTasteProfile(userTasteProfile)
        val processData = drinkFeatureService.getKnnProcessData(userVector, k)
        return BaseResponse(data = processData)
    }

}