package com.example.judo.drink.dto

import com.example.judo.drink.entity.DrinkType
import com.fasterxml.jackson.annotation.JsonProperty

data class DrinkDto (
    @JsonProperty("id")
    val id: Long?,

    @JsonProperty("name")
    val name: String?,

    @JsonProperty("imageUrl")
    val imageUrl: String? = null, // 주류 이미지 URL

    @JsonProperty("type")
    val type: DrinkType? = null,

    @JsonProperty("abv")
    val abv: Double? = null,
)

data class DrinkDetailDto (
    @JsonProperty("id")
    val id: Long?,

    @JsonProperty("name")
    val name: String? = null, // 주류 이름

    @JsonProperty("type")
    val type: DrinkType? = null, // 주류 종류 (ex: whiskey, vodka, beer 등)

    @JsonProperty("description")
    val description: String? = null, // 주류 설명

    @JsonProperty("abv")
    val abv: Double = 0.0, // 알코올 도수

    @JsonProperty("originCountry")
    val originCountry: String? = null, // 원산지

    @JsonProperty("price")
    val price: Double = 0.0, // 가격

    @JsonProperty("tasteProfile")
    val tasteProfile: String? = null, // 맛 프로파일 (ex: 달콤한, 쌉쌀한 등)

    @JsonProperty("imageUrl")
    val imageUrl: String? = null, // 주류 이미지 URL

    @JsonProperty("shortReview")
    val shortReview: String? = null, // 한줄평

    @JsonProperty("detailedDescription")
    val detailedDescription: String? = null // 세부 설명
)
