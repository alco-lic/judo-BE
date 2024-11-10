package com.example.judo.wishlist.dto

import com.example.judo.drink.dto.DrinkDto

data class WishlistDto(
    val id: Long,
    val drink: DrinkDto
)
