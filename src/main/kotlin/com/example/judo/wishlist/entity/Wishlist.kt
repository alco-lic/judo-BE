package com.example.judo.wishlist.entity

import com.example.judo.drink.entity.Drink
import com.example.judo.member.entity.Member
import com.example.judo.wishlist.dto.WishlistDto
import jakarta.persistence.*

@Entity
@Table(name = "wishlist")
data class Wishlist(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    val drink: Drink
){
    fun toDto(): WishlistDto =
        WishlistDto(id!!, drink.id)
}
