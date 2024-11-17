package com.example.judo.recommendation.controller;

import com.example.judo.common.dto.CustomUser
import com.example.judo.common.exception.InvalidInputException
import com.example.judo.drink.entity.Drink
import com.example.judo.recommendation.service.RecommendationService;
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
class RecommendationController(
        private val recommendationService:RecommendationService
) {

    // 통합 추천 API
    @GetMapping("/all")
    fun getRecommendations(): ResponseEntity<List<Drink>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
            ?: throw InvalidInputException("회원 정보가 존재하지 않습니다.")
        val recommendations = recommendationService.getAllRecommendations(userId)
        return ResponseEntity.ok(recommendations)
    }

    // 찜한 상품 기반 추천 API
    @GetMapping("/wishlist")
    fun getRecommendationsFromWishlist(): ResponseEntity<List<Drink>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
            ?: throw InvalidInputException("회원 정보가 존재하지 않습니다.")
        val recommendations = recommendationService.getRecommendationsFromWishlist(userId)
        return ResponseEntity.ok(recommendations)
    }

    // 장바구니 기반 추천 API
    @GetMapping("/cart")
    fun getRecommendationsFromCart(): ResponseEntity<List<Drink>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
            ?: throw InvalidInputException("회원 정보가 존재하지 않습니다.")
        val recommendations = recommendationService.getRecommendationsFromCart(userId)
        return ResponseEntity.ok(recommendations)
    }

    // 구매 내역 기반 추천 API
    @GetMapping("/payment-history")
    fun getRecommendationsFromPaymentHistory(): ResponseEntity<List<Drink>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
            ?: throw InvalidInputException("회원 정보가 존재하지 않습니다.")
        val recommendations = recommendationService.getRecommendationsFromPaymentHistory(userId)
        return ResponseEntity.ok(recommendations)
    }
}