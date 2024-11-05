package com.example.judo.cart.entity

import com.example.judo.cart.dto.CartDto
import com.example.judo.drink.entity.Drink
import com.example.judo.member.entity.Member
import jakarta.persistence.*

@Entity
@Table(name = "cart")
data class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    val drink: Drink,

    val quantity: Int,
){
    fun toDto(): CartDto =
        CartDto(id!!, drink.id, drink.name, drink.price)
}
