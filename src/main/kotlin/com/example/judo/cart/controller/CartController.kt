package com.example.judo.cart.controller

import com.example.judo.cart.dto.CartDto
import com.example.judo.cart.service.CartService
import com.example.judo.common.dto.BaseResponse
import com.example.judo.common.dto.CustomUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartService: CartService,
) {
    /**
     * 상품 찜하기
     */
    @PostMapping("/new")
    fun addCart(@RequestBody cartDto: CartDto): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val favorite = cartService.toggleCart(userId, cartDto)
        return BaseResponse(message = favorite)
    }

    /**
     * 찜한 상품 보기
     */
    @GetMapping("/all")
    fun getCart(): List<CartDto> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        return cartService.getCart(userId)
    }
}