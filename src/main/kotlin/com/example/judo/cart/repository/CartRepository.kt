package com.example.judo.cart.repository

import com.example.judo.cart.entity.Cart
import com.example.judo.drink.entity.Drink
import com.example.judo.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {
    fun findOneByMemberAndDrink(member: Member, drink: Drink): List<Cart>
    fun findAllByMember(member: Member): List<Cart>
}