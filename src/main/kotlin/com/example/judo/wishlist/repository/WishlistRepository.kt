package com.example.judo.wishlist.repository

import com.example.judo.drink.entity.Drink
import com.example.judo.member.entity.Member
import com.example.judo.wishlist.entity.Wishlist
import org.springframework.data.jpa.repository.JpaRepository

interface WishlistRepository : JpaRepository<Wishlist, Long> {
    fun findOneByMemberAndDrink(member: Member, drink: Drink): List<Wishlist>
    fun findAllByMember(member: Member): List<Wishlist>
}