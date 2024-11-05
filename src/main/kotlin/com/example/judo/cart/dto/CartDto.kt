package com.example.judo.cart.dto

data class CartDto(
    val id: Long? = null,
    val drinkId: Long,
    val name: String,
    val price: Double,
)
