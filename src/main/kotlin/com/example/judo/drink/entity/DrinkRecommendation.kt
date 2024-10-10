package com.example.judo.drink.entity

import com.example.judo.member.entity.Member
import jakarta.persistence.*

@Entity
class DrinkRecommendation (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "drink_id")
        val drink: Drink, // 어떤 주류에 대한 추천인지 연결

        @ManyToOne
        @JoinColumn(name = "member_id")
        val member: Member, // 어떤 사용자가 추천했는지 연결

        val reason: String? = null, // 추천 이유 또는 설명
        val rating: Int = 0 // 5점 만점의 별점 (1 ~ 5)
)
