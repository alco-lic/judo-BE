package com.example.judo.drink.entity

import com.example.judo.drink.dto.DrinkDetailDto
import com.example.judo.drink.dto.DrinkDto
import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate

@Entity
class Drink @JsonCreator constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String? = null, // 주류 이름
    @Enumerated(EnumType.STRING)
    val type: DrinkType? = null, // 주류 종류 (ex: whiskey, vodka, beer 등)
    val description: String? = null, // 주류 설명
    val abv: Double = 0.0, // 알코올 도수
    val originCountry: String? = null, // 원산지
    val price: Double = 0.0, // 가격
    val tasteProfile: String? = null, // 맛 프로파일 (ex: 달콤한, 쌉쌀한 등)
    val imageUrl: String? = null, // 주류 이미지 URL

    val shortReview: String? = null, // 한줄평
    val detailedDescription: String? = null // 세부 설명
) {
    @OneToMany(mappedBy = "drink", cascade = [CascadeType.ALL], orphanRemoval = true)
    var recommendations: MutableList<DrinkRecommendation> = mutableListOf() // 추천 정보

    fun toDto(): DrinkDto =
        DrinkDto(id, name, imageUrl, type, abv)
    fun toDetailDto(): DrinkDetailDto =
        DrinkDetailDto(id, name, type, description, abv, originCountry,
            price, tasteProfile, imageUrl, shortReview, detailedDescription)

    @CreatedDate
    var createdDate: LocalDate? = null // 생성일 자동 관리

    @LastModifiedDate
    var modifiedDate: LocalDate? = null // 수정일 자동 관리
}

enum class DrinkType {
    BOURBON,
    IRISH_WHISKEY,
    VODKA,
    GIN,
    LIQUEUR,
    TEQUILA,
    TENNESSEE_WHISKEY,
    BLENDED_SCOTCH,
    CANADIAN_WHISKY,
    COGNAC,
    CHAMPAGNE,
    APERITIF,
    RUM,
    SINGLE_MALT_SCOTCH,
    SCOTCH
}
