package com.example.judo.wishlist.controller

import com.example.judo.common.dto.BaseResponse
import com.example.judo.common.dto.CustomUser
import com.example.judo.wishlist.dto.WishlistDto
import com.example.judo.wishlist.dto.WishlistResponseDto
import com.example.judo.wishlist.service.WishlistService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/wishlist")
class WishlistController(
    private val wishlistService: WishlistService
) {
    /**
     * 상품 찜하기
     */
    @PostMapping("/new")
    fun addWishlist(@RequestBody wishlistDto: WishlistDto): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val favorite = wishlistService.toggleWishlist(userId, wishlistDto)
        return BaseResponse(message = favorite)
    }

    /**
     * 찜한 상품 보기
     */
    @GetMapping("/all")
    fun getWishlist(): List<WishlistResponseDto> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return wishlistService.getWishlist(userId)
    }
}